package com.kh.board.dto;

import com.kh.board.domain.Channel;
import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.Subscribe;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
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
        , Set<ChannelManagerDto> channelManagerDtos
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable, Comparable<ChannelDto> {

    public static ChannelDto of(String channelName
            , String description
            , String slug
            , Set<SubscribeDto> subscribeDtos
            , Set<ChannelManagerDto> channelManagerDtos
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate) {
        return new ChannelDto(channelName, description, slug, subscribeDtos, channelManagerDtos, createdDate, modifiedDate);
    }

    public static ChannelDto of(String channelName
            , String description
            , String slug
            , Set<SubscribeDto> subscribeDtos
            , Set<ChannelManagerDto> channelManagerDtos) {
        return new ChannelDto(channelName, description, slug, subscribeDtos, channelManagerDtos,null, null);
    }

    public static ChannelDto of(String channelName
            , String description
            , String slug) {
        return new ChannelDto(channelName, description, slug, null, null,null, null);
    }

    public static ChannelDto from(Channel channel) {
        return new ChannelDto(channel.getChannelName(), channel.getDescription(), channel.getSlug(), ChannelDto.from(channel.getSubscribes()), channel.getChannelManagers().stream().map(ChannelManagerDto::from).collect(Collectors.toCollection(LinkedHashSet::new)), channel.getCreatedDate(), channel.getModifiedDate());
    }
    public static ChannelDto from2(Channel channel) {
        return new ChannelDto(channel.getChannelName(), channel.getDescription(), channel.getSlug(), ChannelDto.from(channel.getSubscribes()), channel.getChannelManagers().stream().map(e->ChannelManagerDto.of(e.getChannel().getChannelName(),UserDto.of(e.getUser().getUserId(),e.getUser().getPassword(),e.getUser().getEmail(),e.getUser().getNickname()),null)).collect(Collectors.toCollection(LinkedHashSet::new)), channel.getCreatedDate(), channel.getModifiedDate());
    }

    public Channel toEntity() {
        return Channel.of(this.channelName, this.description, this.slug, this.subscribeDtos.size());
    }

    public static Set<SubscribeDto> from(Set<Subscribe> subscribes) {
        return subscribes.stream().map(SubscribeDto::from).collect(Collectors.toCollection(LinkedHashSet::new));
    }


    @Override
    public int compareTo(ChannelDto o) {
        if(o.subscribeDtos.size() < subscribeDtos.size()){
            return 1;
        } else if (o.subscribeDtos.size() > subscribeDtos.size()) {
            return -1;
        }
        return 0;
    }
}