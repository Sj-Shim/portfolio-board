package com.kh.board.dto.response;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.PostDto;
//import com.kh.board.repository.CategoryRepository;
import com.kh.board.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PostResponse(
        Long id,
        ChannelDto channelDto,
        UserDto userDto,
        String title,
        String content,
        Integer rating,
        Integer hit,
        LocalDateTime createdDate,
        List<CommentResponse> commentResponseList
) implements Serializable {

    public static PostResponse of(
            Long id,
            ChannelDto channelDto,
            UserDto userDto,
            String title,
            String content,
            Integer rating,
            Integer hit,
            LocalDateTime createdDate,
            List<CommentResponse> commentResponseList
    ){
        return new PostResponse(id, channelDto, userDto, title, content, rating, hit, createdDate, commentResponseList);
    }

    public static PostResponse from(Post post) {

        return new PostResponse(
                post.getId(),
                ChannelDto.from(post.getChannel()),
                UserDto.from(post.getUser()),
                post.getTitle(),
                post.getContent(),
                post.getRating(),
                post.getHit(),
                post.getCreatedDate(),
                commentResponseList(post)
        );
    }

    public static List<CommentResponse> commentResponseList(Post post) {
        Map<Comment, List<Comment>> commentListMap = post.getCommentList().stream()
                .filter(comment -> comment.getParent() != null)
                .collect(Collectors.groupingBy(Comment::getParent));

        return commentListMap.keySet().stream().map(comment -> CommentResponse.from(comment, commentListMap.get(comment))).collect(Collectors.toList());
    }
}
