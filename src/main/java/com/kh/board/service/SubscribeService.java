package com.kh.board.service;

import com.kh.board.domain.Channel;
import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.request.SubscribeRequest;
import com.kh.board.exception.ChannelException;
import com.kh.board.exception.ChannelExceptionType;
import com.kh.board.exception.UserException;
import com.kh.board.exception.UserExceptionType;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.SubscribeRepository;
import com.kh.board.repository.UserRepository;
import com.kh.board.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    public void addSubscribe(String slug) {
       Channel channel = channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND));
       User user = userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
       Subscribe subscribe = Subscribe.of(channel, user);
       subscribeRepository.save(subscribe);
       user.addSubscribe(subscribe);
    }

    public boolean checkSubscribe(String slug) {
        return subscribeRepository.existsByChannel_SlugAndUser_UserId(slug, SecurityUtil.getLoginUsername());
    }

    public List<SubscribeDto> getUserSubscribes(String userId){

        return subscribeRepository.findByUser_UserId(userId).stream().map(SubscribeDto::from).collect(Collectors.toList());
    }
    public List<Subscribe> getFullInfoSubs(String userId) {
        List<SubscribeDto> el = getUserSubscribes(userId);
        return el.stream().map(e -> new SubscribeDto(e.id(),e.channelDto(),e.userDto()).toEntity(channelRepository, userRepository)).collect(Collectors.toList());
    }

    public boolean checkChanSub(String slug){
        return subscribeRepository.existsByChannel_SlugAndUser_UserId(slug, SecurityUtil.getLoginUsername());
    }
    public void deleteSubscribe(String slug){
        User user = userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(() -> new  UserException(UserExceptionType.NOT_FOUND_MEMBER));
        Channel channel = channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND));
        Subscribe subscribe = subscribeRepository.findByUser_UserIdAndChannel_Slug(SecurityUtil.getLoginUsername(), slug).orElseThrow(() -> new EntityNotFoundException("해당 구독 내역이 없음. id : slug = " + SecurityUtil.getLoginUsername() + " : " + slug));
        if (subscribe!=null){
            subscribeRepository.delete(subscribe);
            user.removeSubscribe(subscribe);
            channel.removeSubscribe(subscribe);
        }
    }
}
