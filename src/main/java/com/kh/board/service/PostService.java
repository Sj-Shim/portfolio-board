package com.kh.board.service;


import com.kh.board.domain.Channel;
import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.User;
import com.kh.board.domain.type.SearchType;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.CommentDto;
import com.kh.board.dto.PostDto;
import com.kh.board.dto.PostWithCommentDto;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.CommentRepository;
import com.kh.board.repository.PostRepository;
import com.kh.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final ChannelRepository channelRepository;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<PostDto> findAll() {
        return postRepository.findAll().stream().map(PostDto::from).collect(Collectors.toList());
    }

    public Page<PostDto> searchChannelPosts(String slug, Pageable pageable) {
        return postRepository.findByChannel_ChannelName(slug, pageable).map(PostDto::from);
    }

//    public Page<PostDto> searchChannelPostsByCategory(String slug, String categoryName, Pageable pageable) {
//        return postRepository.findByChannel_ChannelNameAndCategory_CategoryName(slug, categoryName, pageable).map(PostDto::from);
//    }

    @Transactional(readOnly = true)
    public Page<PostDto> searchPosts(String slug, SearchType target, String keyword, Pageable pageable){

        if(keyword == null || keyword.isBlank()){
//            Page<PostDto> pageA = postRepository.findAll(pageable).map(PostDto::from);
//            Page<PostDto> pageB = postRepository.findByChannelEquals(channelRepository.findBySlugEquals(slug).orElseThrow(()->new EntityNotFoundException("searchPosts 슬러그로 채널 못찾음:" +slug)), pageable).map(PostDto::from);


//            Set<PostDto> setPost = new HashSet<>(pageA.getContent());
//            setPost.addAll(pageB.getContent());
//            Page<PostDto> combinedPage = new PageImpl<>(new ArrayList<>(setPost), pageable, setPost.size());
//            return postRepository.findAll(pageable).map(PostDto::from);
//            return combinedPage;
            return postRepository.findByChannelEquals(channelRepository.findBySlugEquals(slug).orElseThrow(()->new EntityNotFoundException("searchPosts 슬러그로 채널 못찾음:" +slug)), pageable).map(PostDto::from);
        }

        return switch (target){
            case ALL -> postRepository.findByTitleContainingIgnoreCaseOrContentIsContainingIgnoreCaseOrUser_NicknameContainingIgnoreCase(keyword, keyword, keyword, pageable).map(PostDto::from);
            case TITLE -> postRepository.findByTitleContainingIgnoreCase(keyword, pageable).map(PostDto::from);
            case TITLEORCONTENT -> postRepository.findByTitleContainingIgnoreCaseOrContentIsContainingIgnoreCase(keyword, keyword, pageable).map(PostDto::from);
            case CONTENT -> postRepository.findByContentContainingIgnoreCase(keyword, pageable).map(PostDto::from);
            case USER -> postRepository.findByUser_NicknameContainingIgnoreCase(keyword, pageable).map(PostDto::from);
        };
    }

    @Transactional(readOnly = true)
    public PostDto getPost(Long postId) {
        return postRepository.findById(postId).map(PostDto::from).orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다. 글번호 : " + postId));
    }

    public PostDto findById(Long id) {
        return PostDto.from(postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException()));
    }

    public List<CommentDto> findCommentsByPost_Id(Long postId) {
        return CommentDto.from(commentRepository.findByPost_Id(postId));
    }

    public CommentDto saveComment(CommentDto commentDto, Long postId) {
        Comment comment = new Comment();
        comment.setContent(commentDto.content());
        comment.setPost(postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException()));
        return CommentDto.from(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public PostWithCommentDto getPostWithComments(Long postId) {
        increaseHit(postId);
        return postRepository.findById(postId)
                .map(post -> PostWithCommentDto.from(post, channelRepository))
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다. 글번호 : " + postId));
    }
    public void increaseHit(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        post.setHit(post.getHit()+1);
        postRepository.save(post);
    }

    public void savePost(PostDto dto) {
        User user = userRepository.getReferenceById(dto.user().userId());
        Channel channel = channelRepository.getReferenceById(dto.channelName());
        postRepository.save(dto.toEntity(user, channel));
    }

    public void updatePost(Long postId, PostDto dto) {
        try{
            Post post = postRepository.getReferenceById(postId);
            User user = userRepository.getReferenceById(dto.user().userId());
            if(post.getUser().equals(user)){
                if(dto.title() != null) {
                    post.setTitle(dto.title());
                }
                if(dto.content() != null) {
                    post.setContent(dto.content());
                }
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글을 수정하지 못했습니다. - 게시글 없음 -");
        }
    }

    public void deletePost(long postId, String userId) {
        postRepository.deleteByIdAndUser_UserId(postId, userId);
    }

    public long getPostCount() {
        return postRepository.count();
    }

    public void recommendPost(Long postId){
        try {
            Post post = postRepository.getReferenceById(postId);
            post.setRating(post.getRating() + 1);
        } catch (EntityNotFoundException e) {
            log.warn("게시글을 찾을 수 없습니다.");
        }
    }
    public void downPostRate(Long postId) {
        try{
            Post post = postRepository.getReferenceById(postId);
            post.setRating(post.getRating() - 1);
        } catch (EntityNotFoundException e){
            log.warn("게시글을 찾을 수 없습니다.");
        }
    }
}
