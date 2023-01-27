package com.kh.board.dto.request;

import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;

public record SubscribeRequest(
        String channelName,
        String userId
) {
    public static SubscribeRequest of(String channelName, String userId) {
        return new SubscribeRequest(channelName, userId);
    }

    public SubscribeDto toDto() {
        return SubscribeDto.of(null, channelName, userId,  null);
    }
}
