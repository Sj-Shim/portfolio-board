package com.kh.board.dto;

import com.kh.board.domain.Category;
import com.kh.board.domain.Channel;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;
import com.kh.board.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link Post} entity
 */
public record PostWithCommentDto(Long id
        , String channelName
        , UserDto user
        , Integer categoryId
        , List<CommentDto> commentDtos
        , String title
        , String content
        , Integer rating
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static PostWithCommentDto of(Long id
            , String channelName
            , UserDto user
            , Integer categoryId
            , List<CommentDto> commentDtos
            , String title
            , String content
            , Integer rating
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate){
        return new PostWithCommentDto(id, channelName, user, categoryId, commentDtos,title, content, rating, createdDate, modifiedDate);
    }
    public static PostWithCommentDto of(Long id
            , String channelName
            , UserDto user
            , Integer categoryId
            , List<CommentDto> commentDtos
            , String title
            , String content
            , Integer rating){
        return new PostWithCommentDto(id, channelName, user, categoryId, commentDtos,title, content, rating, null, null);
    }
    public static PostWithCommentDto of(Long id
            , String channelName
            , UserDto user
            , List<CommentDto> commentDtos
            , String title
            , String content)
            {
        return new PostWithCommentDto(id, channelName, user, null, commentDtos, title, content, null, null, null);
    }

    public static PostWithCommentDto from(Post post, ChannelRepository channelRepository) {
        return new PostWithCommentDto(post.getId(), ChannelDto.from(post.getChannel()).channelName(), UserDto.from(post.getUser()), CategoryDto.from(post.getCategory()).toEntity(channelRepository).getId(), post.getComments().stream().map(CommentDto::from).collect(Collectors.toList()), post.getTitle(), post.getContent(), post.getRating(), post.getCreatedDate(), post.getModifiedDate());
    }

}