package com.kh.board.dto;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link Comment} entity
 */
public record MultiLevelCommentDto(Long id
        , Long postId
        , UserDto user
        , String content
        , Long parentId
        , List<CommentDto> children
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static MultiLevelCommentDto of(Long id
            , Long postId
            , UserDto user
            , String content
            , Long parentId
            , List<CommentDto> children
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate) {
        return new MultiLevelCommentDto(id, postId, user, content, parentId, children,createdDate, modifiedDate);
    }

    public static MultiLevelCommentDto of(Long id
            , Long postId
            , UserDto user
            , String content
            , Long parentId
            , List<CommentDto> children) {
        return new MultiLevelCommentDto(id, postId, user, content, parentId, children, null, null);
    }
    public static MultiLevelCommentDto of(Long id
            , Long postId
            , UserDto user
            , String content) {
        return new MultiLevelCommentDto(id, postId, user, content, null,null,null, null);
    }
    public static MultiLevelCommentDto of(Long id
            , Long postId
            , UserDto user
            , String content
            , List<CommentDto> children) {
        return new MultiLevelCommentDto(id, postId, user, content, null, children,null, null);
    }
    public static MultiLevelCommentDto from(Comment comment) {
        return new MultiLevelCommentDto(comment.getId(), PostDto.from(comment.getPost()).id(), UserDto.from(comment.getUser()), comment.getContent(), CommentDto.from(comment.getParent()).id(), comment.getReplies().stream().map(CommentDto::from).collect(Collectors.toCollection(ArrayList::new)), comment.getCreatedDate(), comment.getModifiedDate());
    }
}