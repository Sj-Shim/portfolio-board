package com.kh.board.dto.request;

import com.kh.board.dto.CommentDto;
import com.kh.board.dto.UserDto;

public record CommentRequest(
        Long postId,
        Long parentId,
        String content
) {
    public static CommentRequest of(Long postId, Long parentId, String content) {
        return new CommentRequest(postId, parentId, content);
    }

    public static CommentRequest of(Long postId, String content) {
        return new CommentRequest(postId, null, content);
    }

    public CommentDto toDto(UserDto userDto) {
        return CommentDto.of(
                postId,
                userDto,
                content,
                parentId
        );
    }
}
