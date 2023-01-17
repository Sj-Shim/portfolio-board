package com.kh.board.dto;

import com.kh.board.domain.Category;
import com.kh.board.domain.Channel;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Post} entity
 */
public record PostDto(Long id
        , ChannelDto channel
        , UserDto user
        , CategoryDto category
        , String title
        , String content
        , Integer rating
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static PostDto of(Long id
            , ChannelDto channel
            , UserDto user
            , CategoryDto category
            , String title
            , String content
            , Integer rating
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate){
        return new PostDto(id, channel, user, category, title, content, rating, createdDate, modifiedDate);
    }
    public static PostDto of(Long id
            , ChannelDto channel
            , UserDto user
            , CategoryDto category
            , String title
            , String content
            , Integer rating){
        return new PostDto(id, channel, user, category, title, content, rating, null, null);
    }
    public static PostDto of(Long id
            , ChannelDto channel
            , UserDto user
            , String title
            , String content)
            {
        return new PostDto(id, channel, user, null, title, content, null, null, null);
    }

    public static PostDto from(Post post) {
        return new PostDto(post.getId(), ChannelDto.from(post.getChannel()), UserDto.from(post.getUser()), CategoryDto.from(post.getCategory()), post.getTitle(), post.getContent(), post.getRating(), post.getCreatedDate(), post.getModifiedDate());
    }

    public Post toEntity(User user, Channel channel) {
        return Post.of(user, channel, this.title, this.content);
    }
    public Post toEntity(User user, Channel channel, Category category) {
        return Post.of(user, channel, category, this.title, this.content);
    }
}