package com.kh.board.dto.response;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.dto.CommentDto;
import com.kh.board.dto.ReplyDto;
import com.kh.board.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CommentResponse(
        Long postId,
        Long commentId,
        String content,
        LocalDateTime createdDate,
        List<ReplyResponse> replies,
        UserDto userDto
) {
    public static CommentResponse of(
            Long postId,
            Long commentId,
            String content,
            LocalDateTime createdDate,
            List<ReplyResponse> replies,
            UserDto userDto){
        return new CommentResponse(postId, commentId, content, createdDate, replies, userDto);
    }

    public static CommentResponse from(Comment comment, List<Comment> replies) {
        return new CommentResponse(
                comment.getPost().getId(),
                comment.getId(),
                comment.getContent(),
                comment.getCreatedDate(),
                replies.stream().map(ReplyResponse::from).collect(Collectors.toList()),
                UserDto.from(comment.getUser())
        );
    }

    public static CommentResponse convertCommentToDto(Comment comment) {
        return new CommentResponse(
                comment.getId()
                , comment.getContent()
                , comment.
        )
    }

//    public static CommentResponse from(CommentDto commentDto, List<ReplyDto> replies) {
//        return new CommentResponse(
//                commentDto.postId(),
//                commentDto.id(),
//                commentDto.content(),
//                commentDto.createdDate(),
//                replies.stream().map(ReplyResponse::from).collect(Collectors.toList()),
//                commentDto.user()
//        );
//    }

}
