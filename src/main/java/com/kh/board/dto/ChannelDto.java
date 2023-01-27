package com.kh.board.dto;

import com.kh.board.domain.Channel;
import com.kh.board.domain.Subscribe;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link Channel} entity
 */
public record ChannelDto(String channelName
        , String description
        , String slug
        , Set<SubscribeDto> subscribeDtos
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {

    public static ChannelDto of(String channelName
            , String description
            , String slug
            , Set<SubscribeDto> subscribeDtos
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate) {
        return new ChannelDto(channelName, description, slug, subscribeDtos, createdDate, modifiedDate);
    }

    public static ChannelDto of(String channelName
            , String description
            , String slug
            , Set<SubscribeDto> subscribeDtos) {
        return new ChannelDto(channelName, description, slug, subscribeDtos, null, null);
    }

    public static ChannelDto of(String channelName
            , String description
            , String slug) {
        return new ChannelDto(channelName, description, slug, null, null, null);
    }

    public static ChannelDto from(Channel channel) {
        return new ChannelDto(channel.getChannelName(), channel.getDescription(), channel.getSlug(), ChannelDto.from(channel.getSubscribes()),channel.getCreatedDate(), channel.getModifiedDate());
    }

    public Channel toEntity() {
        return Channel.of(this.channelName, this.description, this.slug);
    }

    public static Set<SubscribeDto> from(Set<Subscribe> subscribes) {
        return subscribes.stream().map(SubscribeDto::from).collect(Collectors.toSet());
    }
}