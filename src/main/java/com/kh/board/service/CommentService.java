package com.kh.board.service;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;
import com.kh.board.dto.CommentDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.CommentRequest;
import com.kh.board.dto.request.CommentUpdateRequest;
import com.kh.board.exception.*;
import com.kh.board.repository.CommentRepository;
import com.kh.board.repository.CustomCommentRepository;
import com.kh.board.repository.PostRepository;
import com.kh.board.repository.UserRepository;
import com.kh.board.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    @Autowired private final PostRepository postRepository;
    @Autowired private final CommentRepository commentRepository;
    @Autowired private final CustomCommentRepository customCommentRepository;
    @Autowired private final UserRepository userRepository;


    public List<CommentDto> gerCommentListByPost(Long postId) {
        postRepository.findById(postId).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        return convertNestedStructure(customCommentRepository.findAllByPostId(postId));
    }

    public void save(Long postId, CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntity();
        comment.confirmPost(postRepository.findById(postId).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND)));
        comment.confirmUser(userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)));
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));

        commentRepository.save(comment);
    }


    public void saveReply(Long postId, Long parentId, CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntity();

        comment.confirmUser(userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)));

        comment.confirmPost(postRepository.findById(postId).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND)));

        comment.confirmParent(commentRepository.findById(parentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT)));

        commentRepository.save(comment);
    }


    public void update(Long id, CommentUpdateRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

        if(!comment.getUser().getUserId().equals(SecurityUtil.getLoginUsername())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_UPDATE_COMMENT);
        }

        commentRequest.content().ifPresent(comment::updateContent);
    }


    public void remove(Long id) throws CommentException {

        Comment comment = commentRepository.findCommentByIdWithParent(id).orElseThrow(()->new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));
        if(comment.getReplies().size() != 0){
            comment.remove();
        } else {
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    private Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if(parent != null && parent.getReplies().size() == 1 && parent.isRemoved())
            return getDeletableAncestorComment(parent);
        return comment;
    }

    public List<CommentDto> convertNestedStructure(List<Comment> comments){
        List<CommentDto> result = new ArrayList<>();
        Map<Long, CommentDto> map = new HashMap<>();
        comments.stream().forEach(c->{
            CommentDto response = CommentDto.convertCommentToDto(c);
            map.put(response.id(), response);
            if(c.getParent() != null) map.get(c.getParent().getId()).replies().add(response);
            else result.add(response);
        });
        return result;
    }


}
