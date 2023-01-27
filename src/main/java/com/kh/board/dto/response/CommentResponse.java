package com.kh.board.dto.response;

import com.kh.board.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdDate,
        List<CommentResponse> replies,
        String nickname
) {
    public static CommentResponse of(Long id,
                                     String content,
                                     LocalDateTime createdDate,
                                     List<CommentResponse> replies,
                                     String nickname) {
        return new CommentResponse(id, content, createdDate, replies, nickname);
    }

    public static CommentResponse from(CommentDto dto) {
        return new CommentResponse(
                dto.id(),
                dto.content(),
                dto.createdDate(),
                CommentResponse.from(dto.replies()),
                dto.user().nickname()
        );
    }

    public static List<CommentResponse> from(List<CommentDto> commentDtos) {
        return commentDtos.stream().map(CommentResponse::from).collect(Collectors.toList());
    }
}
