package com.kh.board.controller;

import com.kh.board.domain.Post;
import com.kh.board.domain.Subscribe;
import com.kh.board.domain.type.FormStatus;
import com.kh.board.domain.type.SearchType;
import com.kh.board.dto.*;
import com.kh.board.dto.request.CommentRequest;
import com.kh.board.dto.request.PostRequest;
import com.kh.board.dto.request.PostUpdateDto;
import com.kh.board.dto.request.SubscribeRequest;
import com.kh.board.dto.response.PostResponse;
//import com.kh.board.repository.CategoryRepository;
import com.kh.board.dto.response.PostWithCommentResponse;
import com.kh.board.repository.SubscribeRepository;
import com.kh.board.security.BoardPrincipal;
import com.kh.board.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/{slug:(?!css).*}"} )
public class ChannelController {

    private final PostService postService;
    private final CommentService commentService;
    private final ChannelService channelService;
    private final UserService userService;
    private final SubscribeService subscribeService;
    private final PaginationService paginationService;

    @GetMapping
    public String getChannelMain(@AuthenticationPrincipal BoardPrincipal user
            , Model m
            , @PathVariable(name = "slug") String slug
            , @RequestParam(required = false) SearchType target
            , @RequestParam(required = false) String keyword
            , @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){

        Page<PostResponse> posts = postService.searchPosts(slug, target, keyword, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),posts.getTotalPages());

        m.addAttribute("posts", posts);
        m.addAttribute("pageNumbers", barNumbers);
        m.addAttribute("target", target.values());

        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            boolean isSub = subscribeService.checkChanSub(slug);
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
            m.addAttribute("subCheck", isSub);
        }
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        m.addAttribute("chanInfo", channelDto);


        return "channelMain";
    }

    @GetMapping(value = "/post/{postId}")
    public String readPost(@AuthenticationPrincipal BoardPrincipal user,
                           @PathVariable(name = "slug") String slug,
                           @PathVariable(name = "postId") Long postId,
                           @RequestParam(required = false) SearchType target,
                           @RequestParam(required = false) String keyword
            , @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, ModelMap map) {
        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            boolean isSub = subscribeService.checkChanSub(slug);
            map.addAttribute("userInfo", userInfo);
            map.addAttribute("subList", subInfo);
            map.addAttribute("subCheck", isSub);
        }
        postService.increaseHit(postId);
        PostWithCommentResponse post = PostWithCommentResponse.from(postService.findByPostId(postId));
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        Page<PostResponse> posts = postService.searchPosts(slug, target, keyword, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),posts.getTotalPages());
        List<CommentDto> comments = commentService.gerCommentListByPost(postId);


        map.addAttribute("posts", posts);
        map.addAttribute("pageNumbers", barNumbers);
        map.addAttribute("target", target.values());
        map.addAttribute("comments", comments);

        map.addAttribute("chanInfo", channelDto);

        map.addAttribute("post", post);
        map.addAttribute("totalCount", postService.getPostCount());

        return "readPost";
    }

    @GetMapping("/subscribe")
    public String addSubscribe(@AuthenticationPrincipal BoardPrincipal user, HttpServletRequest request, @PathVariable(name = "slug") String slug){

        System.out.println("==========slug : " + slug);
        if(user == null){
            return "redirect:/login";
        }
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        System.out.println("==========slug : " + slug + channelDto.toString());
        if(subscribeService.checkSubscribe(slug)){
            subscribeService.deleteSubscribe(slug);

            return "redirect:"+request.getHeader("Referer");
        }
        subscribeService.addSubscribe(slug);

        return "redirect:"+request.getHeader("Referer");
    }

    @GetMapping("/write")
    public String loadPostForm(@AuthenticationPrincipal BoardPrincipal user
            , Model m
            , @PathVariable String slug
            , HttpServletRequest request) {

        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            boolean isSub = subscribeService.checkChanSub(slug);
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
            m.addAttribute("subCheck", isSub);
        }
        FormStatus status = FormStatus.CREATE;
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        m.addAttribute("chanInfo", channelDto);
        m.addAttribute("postStatus", status);


        return "writeForm";
    }
    @PostMapping("/write")
    public String writePost(
            @PathVariable String slug, @AuthenticationPrincipal BoardPrincipal user,
            @RequestParam String title,
            @RequestParam String content) {
        PostRequest request = PostRequest.of(title, content);
        System.out.println("slug == " + slug);

        postService.savePost(request, slug);


        return "redirect:/{slug}";
    }

    @GetMapping("/post/{postId}/modify")
    public String loadModifyForm(
            @AuthenticationPrincipal BoardPrincipal user
            , @PathVariable String slug
            , @PathVariable Long postId
            , Model m) {

        if (user==null) return "redirect:/login";
        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            boolean isSub = subscribeService.checkChanSub(slug);
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
            m.addAttribute("subCheck", isSub);
        }
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        m.addAttribute("chanInfo", channelDto);

        PostDto post = postService.findById(postId);
        FormStatus status = FormStatus.UPDATE;

        m.addAttribute("post", post);
        m.addAttribute("postStatus", status);


        return "writeForm";
    }

    @PostMapping("/post/{postId}/modify")
    public String modifyPost(
            @PathVariable String slug,
            @PathVariable Long postId,
            @ModelAttribute PostUpdateDto postUpdateDto) {

        postService.updatePost(postId, postUpdateDto);


        return "redirect:/"+slug+"/post/"+postId;
    }

    @PostMapping("/post/{postId}/delete")
    public String deletePost(
            @PathVariable String slug,
            @PathVariable Long postId,
            @AuthenticationPrincipal BoardPrincipal user){

        postService.deletePost(postId);

        return "redirect:/"+slug;
    }

    @PostMapping("/post/{postId}/comment")
    public String saveComment(@RequestParam String content,
                              @RequestParam Long postId,
                              @AuthenticationPrincipal BoardPrincipal user){
        if(user.getUsername() == null || user.getUsername() == "") {
            return "redirect:/login";
        }
        CommentRequest commentRequest = CommentRequest.of(content);
        commentService.save(postId, commentRequest);

        return "redirect:/{slug}/post/{postId}";
    }

    @PostMapping("/post/{postId}/comment/{parentId}")
    public String saveReply(
            @PathVariable Long postId,
            @RequestParam String content,
            @RequestParam Long parentId,
            @AuthenticationPrincipal BoardPrincipal user){
        if(user.getUsername() == null || user.getUsername() == "") {
            return "redirect:/login";
        }
        CommentRequest commentRequest = CommentRequest.of(content);
        commentService.saveReply(postId, parentId, commentRequest);
        return "redirect:/{slug}/post/{postId}";
    }




}
