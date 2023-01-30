package com.kh.board.dto;

import com.kh.board.domain.*;
import com.kh.board.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link Post} entity
 */
public record PostWithCommentDto(
        Long id
        , ChannelDto channelDto
        , UserDto userDto
        , List<CommentDto> commentDtoList
        , String title
        , String content
        , Integer rating
        , Integer hit
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static PostWithCommentDto of(Long id
            , ChannelDto channelDto
            , UserDto userDto
            , List<CommentDto> commentDtoList
            , String title
            , String content
            , Integer rating
            , Integer hit
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate){
        return new PostWithCommentDto(id, channelDto, userDto, commentDtoList, title, content, rating, hit, createdDate, modifiedDate);
    }
    public static PostWithCommentDto of(
            Long id
            , ChannelDto channelDto
            , UserDto userDto
            , List<CommentDto> commentDtoList
            , String title
            , String content
            , Integer rating
            , Integer hit){
        return new PostWithCommentDto(id, channelDto, userDto, commentDtoList, title, content, rating, hit,null, null);
    }
    public static PostWithCommentDto of(
            Long id
            , ChannelDto channelDto
            , UserDto userDto
            , List<CommentDto> commentDtoList
            , String title
            , String content)
            {
        return new PostWithCommentDto(id, channelDto, userDto, commentDtoList, title, content, null, null,null, null);
    }

    public static PostWithCommentDto from(Post post) {
        return new PostWithCommentDto(
                post.getId(),
                ChannelDto.from(post.getChannel()),
                UserDto.from(post.getUser()),
                commentDtoList(post),
                post.getTitle(),
                post.getContent(),
                post.getRating(),
                post.getHit(),
                post.getCreatedDate(),
                post.getModifiedDate());
    }


    public static List<CommentDto> commentDtoList(Post post) {
        Map<Comment, List<Comment>> commentListMap = post.getCommentList().stream()
                .filter(comment -> comment.getParent() != null)
                .collect(Collectors.groupingBy(Comment::getParent));
        return commentListMap.keySet().stream()
                .map(comment -> CommentDto.from(comment, commentListMap.get(comment))).collect(Collectors.toList());
    }


}