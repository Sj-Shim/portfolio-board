package com.kh.board.dto.response;

import com.kh.board.dto.PostDto;
import com.kh.board.repository.CategoryRepository;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String channelName,
        String nickname,
        String categoryName,
        String title,
        String content,
        Integer rating,
        LocalDateTime createdDate
) implements Serializable {

    public static PostResponse of(
            Long id,
            String channelName,
            String nickname,
            String categoryName,
            String title,
            String content,
            Integer rating,
            LocalDateTime createdDate
    ){
        return new PostResponse(id, channelName, nickname, categoryName, title, content, rating, createdDate);
    }

    public static PostResponse from(PostDto dto, CategoryRepository categoryRepository) {

        return new PostResponse(
                dto.id(),
                dto.channelName(),
                dto.user().nickname(),
                categoryRepository.getReferenceById(dto.categoryId()).getCategoryName(),
                dto.title(),
                dto.content(),
                dto.rating(),
                dto.createdDate()
        );

    }
}
