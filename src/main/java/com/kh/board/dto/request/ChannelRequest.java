package com.kh.board.dto.request;

import com.kh.board.dto.ChannelDto;

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
}
