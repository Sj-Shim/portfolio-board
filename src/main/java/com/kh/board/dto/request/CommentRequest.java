package com.kh.board.dto.request;

import com.kh.board.domain.Comment;
import com.kh.board.dto.CommentDto;
import com.kh.board.dto.UserDto;

public record CommentRequest(
        String content
) {
    public static CommentRequest of(String content) {
        return new CommentRequest(content);
    }

    public Comment toEntity() {
        return Comment.builder().content(content).build();
    }
}
