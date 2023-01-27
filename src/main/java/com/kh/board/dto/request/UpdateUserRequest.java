package com.kh.board.dto.request;

public record UpdateUserRequest(
        String password,
        String email,
        String nickname
) {

    public static UpdateUserRequest of(String password,
                                       String email,
                                       String nickname){
        return new UpdateUserRequest(password, email, nickname);
    }
}
