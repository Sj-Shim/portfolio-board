package com.kh.board.service;

import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.ManagerLevel;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelManagerDto;
import com.kh.board.repository.ChannelManagerRepository;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.UserRepository;
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

    public void addChannelManager(String managerId, String userId, String channelName) {
        try {
            List<ChannelManager> channelManager = channelManagerRepository.findByChannel_ChannelName(channelName);
            for (ChannelManager cm : channelManager) {
                if (cm.getManagerLevel().equals(ManagerLevel.PRIME) && cm.getUser().getUserId().equals(managerId)) {
                    ChannelManager newManager = ChannelManager.of(channelRepository.getReferenceById(channelName), userRepository.getReferenceById(userId), ManagerLevel.VICE);
                    channelManagerRepository.save(newManager);
                }
            }
        } catch (EntityNotFoundException e){
            log.warn("사용자를 찾을 수 없습니다.");
        }
    }

    public List<ChannelManagerDto> getChannelManagers(String channelName) {
        return ChannelManagerDto.from(channelManagerRepository.findByChannel_ChannelName(channelName));
    }

    public void dropChannelManager(String managerId, String userId, String channelName) {
        try {
            List<ChannelManager> channelManagers = channelManagerRepository.findByChannel_ChannelName(channelName);
            for(ChannelManager cm : channelManagers) {
                if(cm.getManagerLevel().equals(ManagerLevel.PRIME) && cm.getUser().getUserId().equals(managerId)){
                    for (ChannelManager cm2 : channelManagers){
                        if (cm2.getUser().getUserId().equals(userId)){
                            channelManagerRepository.delete(cm2);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("관리자 제명을 실패하였습니다.");
        }
    }
}
