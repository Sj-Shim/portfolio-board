package com.kh.board.service;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.CommentRequest;
import com.kh.board.dto.request.CommentUpdateRequest;
import com.kh.board.dto.response.CommentResponse;
import com.kh.board.exception.*;
import com.kh.board.repository.CommentRepository;
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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    @Autowired private final PostRepository postRepository;
    @Autowired private final CommentRepository commentRepository;
    @Autowired private final UserRepository userRepository;

//    public void createComment(Long postId, CommentRequest request, String writer) {
//        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("게시글이 없습니다."));
//        User user = userRepository.findByUserId(writer).orElseThrow(()->new ResourceNotFoundException("해당 사용자를 찾을 수 없습니다."));
//        Comment comment = request.toDto(UserDto.from(user)).toEntity(post, user);
//        commentRepository.save(comment);
//    }

    public void save(Long postId, CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntity();
        comment.confirmPost(postRepository.findById(postId).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND)));
        comment.confirmUser(userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)));
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));

        commentRepository.save(comment);
    }

//    public void createReply(Long commentId, CommentRequest request, String writer) {
//        Comment parentComment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("해당 댓글이 없습니다."));
//        User user = userRepository.findByUserId(writer).orElseThrow(()-> new ResourceNotFoundException("해당 사용자를 찾을 수 없습니다."));
//        Comment reply = request.toDto(UserDto.from(user)).toEntity(postRepository.findById(parentComment.getPost().getId()).orElseThrow(()-> new ResourceNotFoundException("해당 게시글이 없습니다.")), user);
//        reply.setParent(parentComment);
//        commentRepository.save(reply);
//    }

    public void saveReply(Long postId, Long parentId, CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntity();

        comment.confirmUser(userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)));

        comment.confirmPost(postRepository.findById(postId).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND)));

        comment.confirmParent(commentRepository.findById(parentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT)));

        commentRepository.save(comment);
    }

//    public void updateComment(Long commentId, CommentRequest request, String writer) {
//        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("해당댓글을 찾을 수 없습니다."));
//        if(!comment.getUser().getUserId().equals(writer))
//            throw new AccessDeniedException("수정 권한이 없습니다.");
//        comment.setContent(request.content());
//        commentRepository.save(comment);
//    }
    public void update(Long id, CommentUpdateRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

        if(!comment.getUser().getUserId().equals(SecurityUtil.getLoginUsername())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_UPDATE_COMMENT);
        }

        commentRequest.content().ifPresent(comment::updateContent);
    }

//    public void deleteComment(Long commentId, String writer) {
//        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("해당 댓글을 찾을 수 없습니다."));
//        if(!comment.getUser().getUserId().equals(writer))
//            throw new AccessDeniedException("삭제 권한이 없습니다.");
//        commentRepository.delete(comment);
//    }

    public void remove(Long id) throws CommentException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

        if(!comment.getUser().getUserId().equals(SecurityUtil.getLoginUsername())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_DELETE_COMMENT);
        }
        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);
    }

}
