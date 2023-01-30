package com.kh.board.dto;

import com.kh.board.domain.Channel;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;
//import com.kh.board.repository.CategoryRepository;
import com.kh.board.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Post} entity
 */
public record PostDto(
        Long id
        , String channelName
        , UserDto user
        , String title
        , String content
        , Integer rating
        , Integer hit
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static PostDto of(
             String channelName
            , UserDto user
            , String title
            , String content
            , Integer rating
            , Integer hit
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate){
        return new PostDto(null, channelName, user, title, content, rating, hit, createdDate, modifiedDate);
    }

    public static PostDto of(
             String channelName
            , UserDto user
            , String title
            , String content
            , Integer rating
            , Integer hit){
        return new PostDto(null, channelName, user, title, content, rating, hit,null, null);
    }
    public static PostDto of(
             String channelName
            , UserDto user
            , String title
            , String content)
            {
        return new PostDto(null, channelName, user, title, content, null, null, null, null);
    }

    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getChannel().getChannelName(), UserDto.from(post.getUser()),  post.getTitle(), post.getContent(), post.getRating(), post.getHit(), post.getCreatedDate(), post.getModifiedDate());
    }

    public Post toEntity(User user, Channel channel) {
        return Post.of(user, channel, this.title, this.content);
    }

}