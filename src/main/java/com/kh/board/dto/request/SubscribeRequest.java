package com.kh.board.dto.request;

import com.kh.board.domain.Subscribe;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;

public record SubscribeRequest(
        String slug,
        String userId
) {
    public static SubscribeRequest of(String slug, String userId) {
        return new SubscribeRequest(slug, userId);
    }

}
