package com.kh.board.service;

import com.kh.board.domain.*;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.ChannelRequest;
import com.kh.board.dto.request.ChannelUpdateDto;
import com.kh.board.exception.ChannelException;
import com.kh.board.exception.ChannelExceptionType;
import com.kh.board.exception.UserException;
import com.kh.board.exception.UserExceptionType;
import com.kh.board.repository.ChannelManagerRepository;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.SubscribeRepository;
import com.kh.board.repository.UserRepository;
import com.kh.board.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OrderBy;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChannelService {

    @Autowired private ChannelRepository channelRepository;
    @Autowired private ChannelManagerRepository channelManagerRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SubscribeRepository subscribeRepository;

    @OrderBy("subCount desc")
    public List<ChannelDto> getChannelList(){
        return channelRepository.findAllByOrderBySubCountDesc().stream().map(ChannelDto::from).collect(Collectors.toList());
    }
    public ChannelDto getChannelBySlug(String slug) {
        return ChannelDto.from(channelRepository.findBySlugEquals(slug).orElseThrow(()->new EntityNotFoundException("일치하는 채널이 없습니다.getChannelBySlug : "+slug)));
    }

    public void createChannel(ChannelRequest channelRequest) {
        if(channelRepository.existsById(channelRequest.slug()) || channelRepository.existsByChannelName(channelRequest.channelName())){
            return;
        }
        User user = userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        if(user.getPoint() < 3000) return;

        Channel channel = channelRequest.toEntity();
        ChannelManager channelManager = ChannelManager.of(channel, user, ManagerLevel.PRIME);
        channel.addManager(channelManager);


        user.setPoint(user.getPoint() - 3000);
        channelRepository.save(channel);
        channelManagerRepository.save(channelManager);
    }

    public List<ChannelDto> getSearchedChannel(String keyword) {
        return channelRepository.findByChannelNameContainingIgnoreCaseOrSlugContainingIgnoreCase(keyword).stream().map(ChannelDto::from).collect(Collectors.toList());
    }

    public ChannelDto getChannel(String slug) {
        Channel channel = channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND));
        ChannelDto channelDto = ChannelDto.from(channel);
        return channelDto;
    }

    public void updateChannel(String slug, ChannelUpdateDto channelUpdateDto) {
        try{

            Channel channel = channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND));
            Set<ChannelManager> channelManagers = channel.getChannelManagers();
            for (ChannelManager manager : channelManagers){
                if(manager.getUser().getUserId().equals(SecurityUtil.getLoginUsername()) && manager.getChannel().equals(channel)){
                    channelUpdateDto.description().ifPresent(channel::updateDescription);
                }
            }

        }catch (ChannelException e) {
            log.warn(ChannelExceptionType.NOT_AUTHORITY_UPDATE_CHANNEL.getErrorMessage());
        }
    }

    public Integer getSubscribeCount(String slug) {
        return subscribeRepository.findByChannel_Slug(slug).size();
    }

    public void deleteChannel(String slug){
        try {
            Channel channel = channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND));
            Set<ChannelManager> channelManagers = channel.getChannelManagers();
            for (ChannelManager manager : channelManagers){
                if(manager.getUser().getUserId().equals(SecurityUtil.getLoginUsername()) && manager.getChannel().equals(channel) && manager.getManagerLevel().getName().equals("관리자")){
                    subscribeRepository.deleteAllInBatch(subscribeRepository.findByChannel_Slug(slug));
                    channelManagerRepository.deleteAllInBatch(channelManagerRepository.findByChannel_Slug(slug));
                    channelRepository.delete(channel);
                }
            }

        }catch (Exception e) {
            log.warn("채널을 삭제하지 못했습니다.");
        }
    }
}
