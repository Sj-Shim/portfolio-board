package com.kh.board.dto;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Comment} entity
 */
public record CommentDto(Long id
        , PostDto post
        , UserDto user
        , String content
        , CommentDto parentId
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static CommentDto of(Long id
            , PostDto post
            , UserDto user
            , String content
            , CommentDto parentId
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate) {
        return new CommentDto(id, post, user, content, parentId, createdDate, modifiedDate);
    }

    public static CommentDto of(Long id
            , PostDto post
            , UserDto user
            , String content
            , CommentDto parentId) {
        return new CommentDto(id, post, user, content, parentId, null, null);
    }
    public static CommentDto of(Long id
            , PostDto post
            , UserDto user
            , String content) {
        return new CommentDto(id, post, user, content, null, null, null);
    }
    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getId(), PostDto.from(comment.getPost()), UserDto.from(comment.getUser()), comment.getContent(), CommentDto.from(comment.getParentId()), comment.getCreatedDate(), comment.getModifiedDate());
    }

    public Comment toEntity(Post post, User user) {
        return Comment.of(post, user, this.content);
    }
}