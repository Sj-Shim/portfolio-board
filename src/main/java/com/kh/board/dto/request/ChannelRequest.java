package com.kh.board.dto.request;

import com.kh.board.domain.Channel;
import com.kh.board.dto.ChannelDto;

import javax.validation.constraints.Pattern;

public record ChannelRequest(
        String channelName,
        String description,
        String slug
) {
    public static ChannelRequest of(String channelName, String description, String slug) {
        return new ChannelRequest(channelName, description, slug);
    }

    public ChannelDto toDto() {
        return ChannelDto.of(this.channelName, this.description, this.slug);
    }

    public Channel toEntity(){
        return Channel.of(channelName, description, slug);
    }
}
