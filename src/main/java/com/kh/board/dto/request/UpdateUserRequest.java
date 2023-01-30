package com.kh.board.dto.request;

import java.util.Optional;

public record UpdateUserRequest(
        Optional<String> email,
        Optional<String> nickname
) {

    public static UpdateUserRequest of(Optional<String> email,
                                       Optional<String> nickname){
        return new UpdateUserRequest(email, nickname);
    }
}
