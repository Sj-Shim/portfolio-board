package com.kh.board.dto;

import com.kh.board.domain.Subscribe;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Subscribe} entity
 */
public record SubscribeDto(Long id
        , ChannelDto channel
        , UserDto user
        , LocalDateTime subStartDate) implements Serializable {
    public static SubscribeDto of(Long id
            , ChannelDto channel
            , UserDto user
            , LocalDateTime subStartDate) {
        return new SubscribeDto(id, channel, user, subStartDate);
    }
    public static SubscribeDto of(Long id
            , ChannelDto channel
            , UserDto user) {
        return new SubscribeDto(id, channel, user, null);
    }

    public static SubscribeDto from(Subscribe subscribe) {
        return new SubscribeDto(subscribe.getId(), ChannelDto.from(subscribe.getChannel()),UserDto.from(subscribe.getUser()), subscribe.getSubStartDate());
    }

    public Subscribe toEntity() {
        return Subscribe.of(this.channel.toEntity(), this.user.toEntity());
    }
}