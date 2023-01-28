package com.kh.board.service;

import com.kh.board.domain.Channel;
import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.request.SubscribeRequest;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.SubscribeRepository;
import com.kh.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SubscribeService {
    @Autowired private SubscribeRepository subscribeRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ChannelRepository channelRepository;

    public void addSubscribe(SubscribeRequest request) {
        Subscribe subscribe = Subscribe.of(channelRepository.getReferenceById(request.channelName()), userRepository.getReferenceById(request.userId()));
        subscribeRepository.save(subscribe);
    }

    public boolean checkSubscribe(String slug, String userId) {
        return subscribeRepository.existsByChannel_ChannelNameAndUser_UserId(slug, userId);
    }

    public List<SubscribeDto> getUserSubscribes(String userId){

        return subscribeRepository.findByUser_UserId(userId).stream().map(SubscribeDto::from).collect(Collectors.toList());
    }
    public List<Subscribe> getFullInfoSubs(String userId) {
        List<SubscribeDto> el = getUserSubscribes(userId);
        return el.stream().map(e -> new SubscribeDto(e.id(),e.slug(),e.userId()).toEntity(channelRepository, userRepository)).collect(Collectors.toList());
    }

    public void deleteSubscribe(String userId, String channelName){
        Subscribe subscribe = subscribeRepository.findByUser_UserIdAndChannel_ChannelName(userId, channelName).orElse(null);
        if (subscribe!=null){
            subscribeRepository.delete(subscribe);
        }
    }
}
