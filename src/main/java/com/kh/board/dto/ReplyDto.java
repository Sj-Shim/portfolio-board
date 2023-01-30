package com.kh.board.dto;

import com.kh.board.domain.Comment;

public record ReplyDto(
        Long postId,
        Long parentId,
        Long replyId,
        String content,
        UserDto userDto
) {
    public static ReplyDto of(
            Long postId,
            Long parentId,
            Long replyId,
            String content,
            UserDto userDto
    ) {
        return new ReplyDto(postId, parentId, replyId, content, userDto);
    }
    public static ReplyDto from(Comment reply) {
        return new ReplyDto(
        reply.getPost().getId(),
        reply.getParent().getId(),
        reply.getId(),
        reply.getContent(),
        UserDto.from(reply.getUser())
        );
    }

}
