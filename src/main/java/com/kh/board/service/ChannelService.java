package com.kh.board.service;

import com.kh.board.domain.Channel;
import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.ChannelRequest;
import com.kh.board.repository.ChannelManagerRepository;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.SubscribeRepository;
import com.kh.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OrderBy;
import java.util.List;
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


    public List<Channel> getFullInfoChan() {
        List<ChannelDto> el = getChannelList();
        return el.stream().map(e -> new ChannelDto(e.channelName(),e.description(),e.slug(),e.subscribeDtos(),e.channelManagerDtos(), e.createdDate(),e.modifiedDate()).toEntity()).collect(Collectors.toList());
    }
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
