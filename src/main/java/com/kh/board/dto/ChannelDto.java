package com.kh.board.dto;

import com.kh.board.domain.Channel;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Channel} entity
 */
public record ChannelDto(String channelName
        , String description
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {

    public static ChannelDto of(String channelName
            , String description
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate) {
        return new ChannelDto(channelName, description, createdDate, modifiedDate);
    }

    public static ChannelDto of(String channelName
            , String description) {
        return new ChannelDto(channelName, description, null, null);
    }

    public static ChannelDto from(Channel channel) {
        return new ChannelDto(channel.getChannelName(), channel.getDescription(), channel.getCreatedDate(), channel.getModifiedDate());
    }

    public Channel toEntity() {
        return Channel.of(this.channelName, this.description);
    }
}