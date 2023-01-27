package com.kh.board.dto;

import com.kh.board.domain.Subscribe;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.UserRepository;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Subscribe} entity
 */
public record SubscribeDto(Long id
        , String channelName
        , String userId
        , LocalDateTime subStartDate) implements Serializable {
    public static SubscribeDto of(Long id
            , String channelName
            , String userId
            , LocalDateTime subStartDate) {
        return new SubscribeDto(id, channelName, userId, subStartDate);
    }
    public static SubscribeDto of(Long id
            , String channelName
            , String userId) {
        return new SubscribeDto(id, channelName, userId, null);
    }

    public static SubscribeDto from(Subscribe subscribe) {
        return new SubscribeDto(subscribe.getId(), subscribe.getChannel().getChannelName(),subscribe.getUser().getUserId(), subscribe.getSubStartDate());
    }


    public Subscribe toEntity(ChannelRepository channelRepository, UserRepository userRepository) {
        return Subscribe.of(channelRepository.getReferenceById(channelName), userRepository.getReferenceById(userId));
    }
}