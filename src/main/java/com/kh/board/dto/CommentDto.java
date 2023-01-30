package com.kh.board.dto;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link com.kh.board.domain.Comment} entity
 */
public record CommentDto(Long id
        , Long postId
        , UserDto user
        , String content
        , List<ReplyDto> replies
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {
    public static CommentDto of(Long id
            , Long postId
            , UserDto user
            , String content
            , List<ReplyDto> replies
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate) {
        return new CommentDto(id, postId, user, content, replies, createdDate, modifiedDate);
    }
    public static CommentDto of(
            Long postId,
            UserDto userDto,
            String content
    ) {
        return new CommentDto(null, postId, userDto, content, null, null, null);
    }
    public static CommentDto of(
            Long postId,
            UserDto userDto,
            String content,
            List<ReplyDto> replies
    ) {
        return new CommentDto(null, postId, userDto, content, replies, null, null);
    }


    public static CommentDto of(Long id
            , Long postId
            , UserDto user
            , String content
            , List<ReplyDto> replies) {
        return new CommentDto(id, postId, user, content, replies, null, null);
    }
    public static CommentDto of(Long id
            , Long postId
            , UserDto user
            , String content) {
        return new CommentDto(id, postId, user, content, null, null, null);
    }
    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getId(), PostDto.from(comment.getPost()).id(), UserDto.from(comment.getUser()), comment.getContent(), comment.getReplies().stream().map(ReplyDto::from).collect(Collectors.toList()), comment.getCreatedDate(), comment.getModifiedDate());
    }

    public static List<CommentDto> from(List<Comment> comments) {
        return comments.stream().map(CommentDto::from).collect(Collectors.toList());
    }

    public static CommentDto from(Comment comment, List<Comment> repliesList) {
        return new CommentDto(
                comment.getId(),
                comment.getPost().getId(),
                UserDto.from(comment.getUser()),
                comment.getContent(),
                repliesList.stream().map(ReplyDto::from).collect(Collectors.toList()),
                comment.getCreatedDate(),
                comment.getModifiedDate()
        );
    }
    public Comment toEntity(Post post, User user) {
        return Comment.of(post, user, this.content);
    }
}