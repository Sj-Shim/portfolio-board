package com.kh.board.dto;

import com.kh.board.domain.Category;
import com.kh.board.domain.Channel;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link Post} entity
 */
public record PostWithCommentDto(Long id
        , ChannelDto channel
        , UserDto user
        , CategoryDto category
        , Set<CommentDto> commentDtos
        , String title
        , String content
        , Integer rating
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static PostWithCommentDto of(Long id
            , ChannelDto channel
            , UserDto user
            , CategoryDto category
            , Set<CommentDto> commentDtos
            , String title
            , String content
            , Integer rating
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate){
        return new PostWithCommentDto(id, channel, user, category, commentDtos,title, content, rating, createdDate, modifiedDate);
    }
    public static PostWithCommentDto of(Long id
            , ChannelDto channel
            , UserDto user
            , CategoryDto category
            , Set<CommentDto> commentDtos
            , String title
            , String content
            , Integer rating){
        return new PostWithCommentDto(id, channel, user, category, commentDtos,title, content, rating, null, null);
    }
    public static PostWithCommentDto of(Long id
            , ChannelDto channel
            , UserDto user
            , Set<CommentDto> commentDtos
            , String title
            , String content)
            {
        return new PostWithCommentDto(id, channel, user, null, commentDtos, title, content, null, null, null);
    }

    public static PostWithCommentDto from(Post post) {
        return new PostWithCommentDto(post.getId(), ChannelDto.from(post.getChannel()), UserDto.from(post.getUser()), CategoryDto.from(post.getCategory()), post.getComments().stream().map(CommentDto::from).collect(Collectors.toCollection(LinkedHashSet::new)), post.getTitle(), post.getContent(), post.getRating(), post.getCreatedDate(), post.getModifiedDate());
    }

}