package com.kh.board.dto.response;

import com.kh.board.domain.Comment;
import com.kh.board.dto.CommentDto;
import com.kh.board.dto.ReplyDto;
import com.kh.board.dto.UserDto;

public record ReplyResponse(
        Long postId,
        Long parentId,
        Long replyId,
        String content,
        UserDto userDto
) {
    public static ReplyResponse of(
            Long postId,
            Long parentId,
            Long replyId,
            String content,
            UserDto userDto
    ) {
        return new ReplyResponse(postId, parentId, replyId, content, userDto);
    }

    public static ReplyResponse from(Comment reply) {
        return new ReplyResponse(
                reply.getPost().getId(),
                reply.getParent().getId(),
                reply.getId(),
                reply.getContent(),
                UserDto.from(reply.getUser())
        );
    }
    public static ReplyResponse from(ReplyDto reply) {
        return new ReplyResponse(
        reply.postId(),
        reply.parentId(),
        reply.replyId(),
        reply.content(),
        reply.userDto()
        );
    }
}
