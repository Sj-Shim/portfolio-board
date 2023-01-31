package com.kh.board.dto.response;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.CommentDto;
import com.kh.board.dto.PostWithCommentDto;
//import com.kh.board.repository.CategoryRepository;
import com.kh.board.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PostWithCommentResponse(
        Long id,
        ChannelDto channelDto,
        UserDto userDto,
        String title,
        String content,
        Integer rating,
        Integer hit,
        LocalDateTime createdDate,
        List<CommentResponse> comments
) implements Serializable {

    public static PostWithCommentResponse of (Long id,
                                              ChannelDto channelDto,
                                              UserDto userDto,
                                              String title,
                                              String content,
                                              Integer rating,
                                              Integer hit,
                                              LocalDateTime createdDate,
                                              List<CommentResponse> comments) {
        return new PostWithCommentResponse(id, channelDto, userDto, title, content, rating, hit, createdDate, comments);
    }

    public static PostWithCommentResponse from (Post post) {
//        List<CommentResponse> commentResponses = post.getCommentList().stream().map(comment -> CommentResponse.from(comment, comment.getReplies().stream().filter(reply -> reply.getParent() != null).collect(Collectors.groupingBy(Comment::getParent)).keySet().stream().toList())).collect(Collectors.toList());
        List<CommentResponse> commentResponses = post.getCommentList().stream()
                .map(comment -> {
                    List<Comment> replies = comment.getReplies().stream()
                            .filter(reply -> reply.getParent() != null)
                            .collect(Collectors.toList());
                    return CommentResponse.from(comment, replies);
                })
                .collect(Collectors.toList());



        return new PostWithCommentResponse(
                post.getId(),
                ChannelDto.from(post.getChannel()),
                UserDto.from(post.getUser()),
                post.getTitle(),
                post.getContent(),
                post.getRating(),
                post.getHit(),
                post.getCreatedDate(),
                commentResponses

        );
    }

    public static List<CommentResponse> replies(Post post) {
        Map<Comment, List<Comment>> commentListMap = post.getCommentList().stream()
                .filter(comment -> comment.getParent() != null)
                .collect(Collectors.groupingBy(Comment::getParent));
        return commentListMap.keySet().stream()
                .map(comment -> CommentResponse.from(comment, commentListMap.get(comment))).collect(Collectors.toList());
    }
}
