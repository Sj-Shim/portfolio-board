package com.kh.board.dto.request;

import java.util.Optional;

public record UpdateUserRequest(
        Optional<String> nickname
) {

    public static UpdateUserRequest of(Optional<String> nickname){
        return new UpdateUserRequest(nickname);
    }
}
