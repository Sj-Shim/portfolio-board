package com.kh.board.dto.response;

import com.kh.board.dto.PostWithCommentDto;
//import com.kh.board.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostWithCommentResponse(
        Long id,
        String channelName,
        String nickname,
//        String categoryName,
        String title,
        String content,
        Integer rating,
        Integer hit,
        LocalDateTime createdDate,
        List<CommentResponse> comments
) implements Serializable {

    public static PostWithCommentResponse of (Long id,
                                              String channelName,
                                              String nickname,
//                                              String categoryName,
                                              String title,
                                              String content,
                                              Integer rating,
                                              Integer hit,
                                              LocalDateTime createdDate,
                                              List<CommentResponse> comments) {
        return new PostWithCommentResponse(id, channelName, nickname, /*categoryName,*/ title, content, rating, hit, createdDate, comments);
    }

    public static PostWithCommentResponse from (PostWithCommentDto dto/*, @Autowired CategoryRepository categoryRepository*/) {
        return new PostWithCommentResponse(
                dto.id(),
                dto.channelName(),
                dto.user().nickname(),
//                categoryRepository.findById(dto.categoryId()).orElse(null).getCategoryName(),
                dto.title(),
                dto.content(),
                dto.rating(),
                dto.hit(),
                dto.createdDate(),
                CommentResponse.from(dto.commentDtos())
        );
    }
}
