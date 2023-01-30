package com.kh.board.service;

import com.kh.board.domain.Channel;
import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.ManagerLevel;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelManagerDto;
import com.kh.board.exception.ChannelException;
import com.kh.board.exception.ChannelExceptionType;
import com.kh.board.exception.UserException;
import com.kh.board.exception.UserExceptionType;
import com.kh.board.repository.ChannelManagerRepository;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.UserRepository;
import com.kh.board.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChannelManagerService {

    @Autowired private ChannelManagerRepository channelManagerRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ChannelRepository channelRepository;

    public void addChannelManager(String userId, String slug) {
        try {
            Channel channel = channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND));

            List<ChannelManager> channelManager = channelManagerRepository.findByChannel_Slug(slug);
            for (ChannelManager cm : channelManager) {
                if (cm.getManagerLevel().equals(ManagerLevel.PRIME) && cm.getUser().getUserId().equals(SecurityUtil.getLoginUsername())) {
                    ChannelManager newManager = ChannelManager.of(channel, userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)), ManagerLevel.VICE);
                    channel.addManager(newManager);
                    channelManagerRepository.save(newManager);
                }
            }
        } catch (EntityNotFoundException e){
            log.warn("사용자를 찾을 수 없습니다.");
        }
    }

    public List<ChannelManagerDto> getChannelManagers(String slug) {
        return ChannelManagerDto.from(channelManagerRepository.findByChannel_Slug(slug));
    }

    public void dropChannelManager(String userId, String slug) {
        try {
            Channel channel = channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND));
            List<ChannelManager> channelManagers = channelManagerRepository.findByChannel_Slug(slug);
            for(ChannelManager cm : channelManagers) {
                if(cm.getManagerLevel().equals(ManagerLevel.PRIME) && cm.getUser().getUserId().equals(SecurityUtil.getLoginUsername())){
                    for (ChannelManager cm2 : channelManagers){
                        if (cm2.getUser().getUserId().equals(userId)){
                            channelManagerRepository.delete(cm2);
                            channel.removeManager(cm2);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("관리자 제명을 실패하였습니다.");
        }
    }
}
