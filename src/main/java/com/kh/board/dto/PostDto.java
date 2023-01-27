package com.kh.board.dto;

import com.kh.board.domain.Category;
import com.kh.board.domain.Channel;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;
import com.kh.board.repository.CategoryRepository;
import com.kh.board.repository.ChannelRepository;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Post} entity
 */
public record PostDto(Long id
        , String channelName
        , UserDto user
        , Integer categoryId
        , String title
        , String content
        , Integer rating
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static PostDto of(
             String channelName
            , UserDto user
            , Integer categoryId
            , String title
            , String content
            , Integer rating
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate){
        return new PostDto(null, channelName, user, categoryId, title, content, rating, createdDate, modifiedDate);
    }
    public static PostDto of(
            String channelName,
            UserDto user,
            Integer categoryId,
            String title,
            String content
    ){
        return new PostDto(null, channelName, user, categoryId, title, content, null, null, null);
    }
    public static PostDto of(
             String channelName
            , UserDto user
            , Integer categoryId
            , String title
            , String content
            , Integer rating){
        return new PostDto(null, channelName, user, categoryId, title, content, rating, null, null);
    }
    public static PostDto of(
             String channelName
            , UserDto user
            , String title
            , String content)
            {
        return new PostDto(null, channelName, user, null, title, content, null, null, null);
    }

    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getChannel().getChannelName(), UserDto.from(post.getUser()), post.getCategory().getId(), post.getTitle(), post.getContent(), post.getRating(), post.getCreatedDate(), post.getModifiedDate());
    }

    public Post toEntity(User user, Channel channel) {
        return Post.of(user, channel, this.title, this.content);
    }
    public Post toEntity(User user, Channel channel, Category category) {
        return Post.of(user, channel, category, this.title, this.content);
    }
    public Post toEntity(User user, ChannelRepository channelRepository) {
        return Post.of(user, channelRepository.getReferenceById(channelName), this.title, this.content);
    }
    public Post toEntity(User user, ChannelRepository channelRepository, CategoryRepository categoryRepository) {
        return Post.of(user, channelRepository.getReferenceById(channelName), categoryRepository.getReferenceById(categoryId), this.title, this.content);
    }
}