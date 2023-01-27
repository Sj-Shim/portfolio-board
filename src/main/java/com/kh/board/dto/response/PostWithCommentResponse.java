package com.kh.board.dto.response;

import com.kh.board.dto.PostWithCommentDto;
import com.kh.board.repository.CategoryRepository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostWithCommentResponse(
        Long id,
        String channelName,
        String nickname,
        String categoryName,
        String title,
        String content,
        Integer rating,
        LocalDateTime createdDate,
        List<CommentResponse> comments
) implements Serializable {

    public static PostWithCommentResponse of (Long id,
                                              String channelName,
                                              String nickname,
                                              String categoryName,
                                              String title,
                                              String content,
                                              Integer rating,
                                              LocalDateTime createdDate,
                                              List<CommentResponse> comments) {
        return new PostWithCommentResponse(id, channelName, nickname, categoryName, title, content, rating, createdDate, comments);
    }

    public static PostWithCommentResponse from (PostWithCommentDto dto, CategoryRepository categoryRepository) {
        return new PostWithCommentResponse(
                dto.id(),
                dto.channelName(),
                dto.user().nickname(),
                categoryRepository.getReferenceById(dto.categoryId()).getCategoryName(),
                dto.title(),
                dto.content(),
                dto.rating(),
                dto.createdDate(),
                CommentResponse.from(dto.commentDtos())
        );
    }
}
