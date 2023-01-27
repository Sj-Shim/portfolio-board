package com.kh.board.service;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.CommentRequest;
import com.kh.board.dto.response.CommentResponse;
import com.kh.board.repository.CommentRepository;
import com.kh.board.repository.PostRepository;
import com.kh.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    @Autowired private final PostRepository postRepository;
    @Autowired private final CommentRepository commentRepository;
    @Autowired private final UserRepository userRepository;

    public void createComment(Long postId, CommentRequest request, String writer) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("게시글이 없습니다."));
        User user = userRepository.findByUserId(writer).orElseThrow(()->new ResourceNotFoundException("해당 사용자를 찾을 수 없습니다."));
        Comment comment = request.toDto(UserDto.from(user)).toEntity(post, user);
        commentRepository.save(comment);
    }

    public void createReply(Long commentId, CommentRequest request, String writer) {
        Comment parentComment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("해당 댓글이 없습니다."));
        User user = userRepository.findByUserId(writer).orElseThrow(()-> new ResourceNotFoundException("해당 사용자를 찾을 수 없습니다."));
        Comment reply = request.toDto(UserDto.from(user)).toEntity(postRepository.findById(parentComment.getPost().getId()).orElseThrow(()-> new ResourceNotFoundException("해당 게시글이 없습니다.")), user);
        reply.setParent(parentComment);
        commentRepository.save(reply);
    }

    public void updateComment(Long commentId, CommentRequest request, String writer) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("해당댓글을 찾을 수 없습니다."));
        if(!comment.getUser().getUserId().equals(writer))
            throw new AccessDeniedException("수정 권한이 없습니다.");
        comment.setContent(request.content());
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String writer) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("해당 댓글을 찾을 수 없습니다."));
        if(!comment.getUser().getUserId().equals(writer))
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        commentRepository.delete(comment);
    }



}
