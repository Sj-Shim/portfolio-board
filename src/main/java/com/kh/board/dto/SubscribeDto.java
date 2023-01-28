package com.kh.board.dto;

import com.kh.board.domain.Subscribe;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.UserRepository;
import lombok.Setter;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Subscribe} entity
 */

public record SubscribeDto(Long id
        , String slug
        , String userId) implements Serializable {
    public static SubscribeDto of(Long id
            , String slug
            , String userId) {
        return new SubscribeDto(id, slug, userId);
    }


    public static SubscribeDto from(Subscribe subscribe) {
        return new SubscribeDto(subscribe.getId(), subscribe.getChannel().getSlug(),subscribe.getUser().getUserId());
    }


    public Subscribe toEntity(ChannelRepository channelRepository, UserRepository userRepository) {
        return Subscribe.of(channelRepository.findById(slug).orElseThrow(()-> new EntityNotFoundException("채널을 찾지 못했습니다.")), userRepository.getReferenceById(userId));
    }
}