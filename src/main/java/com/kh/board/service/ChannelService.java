package com.kh.board.service;

import com.kh.board.domain.Channel;
import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.ChannelRequest;
import com.kh.board.repository.ChannelManagerRepository;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.SubscribeRepository;
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
public class ChannelService {

    @Autowired private ChannelRepository channelRepository;
    @Autowired private ChannelManagerRepository channelManagerRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SubscribeRepository subscribeRepository;

    public void createChannel(ChannelRequest channelRequest) {
        Channel channel = Channel.of(channelRequest.channelName(), channelRequest.description(), channelRequest.slug());
        channel = channelRepository.save(channel);
    }

    public ChannelDto getChannel(String channelName) {
        Channel channel = channelRepository.findById(channelName).orElse(null);
        ChannelDto channelDto = ChannelDto.from(channel);
        return channelDto;
    }

    public void updateChannel(String channelName, String description, UserDto userDto) {
        try{
            Channel channel = channelRepository.getReferenceById(channelName);
            User user = userRepository.getReferenceById(userDto.userId());
            List<ChannelManager> channelManagers = channelManagerRepository.findByChannel_ChannelName(channelName);
            for (ChannelManager channelManager : channelManagers) {
                if(channelManager.getUser().getUserId().equals(user.getUserId())){
                    channel.setDescription(description);
                }
            }

        }catch (EntityNotFoundException e) {
            log.warn("채널을 찾을 수 없습니다.");
        }
    }

    public Integer getSubscribeCount(String channelName) {
        return subscribeRepository.findByChannel_ChannelName(channelName).size();
    }

    public void deleteChannel(String channelName){
        try {
            Channel channel = channelRepository.getReferenceById(channelName);
            channelRepository.delete(channel);
        }catch (Exception e) {
            log.warn("채널을 삭제하지 못했습니다.");
        }
    }
}
