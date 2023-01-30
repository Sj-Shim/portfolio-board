package com.kh.board.controller;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.type.SearchType;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        String getSlug = slug;
        Page<PostResponse> posts = postService.searchPosts(getSlug, target, keyword, pageable);
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
        ChannelDto channelDto = channelService.getChannelBySlug(getSlug);
        m.addAttribute("chanInfo", channelDto);


        return "channelMain";
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public String readPost(@AuthenticationPrincipal BoardPrincipal user, @PathVariable(name = "slug") String slug, @PathVariable(name = "postId") Long postId, ModelMap map) {
        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            map.addAttribute("userInfo", userInfo);
            map.addAttribute("subList", subInfo);
        }

        PostWithCommentResponse post = PostWithCommentResponse.from(postService.findByPostId(postId));
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        map.addAttribute("chanInfo", channelDto);

        map.addAttribute("post", post);
        map.addAttribute("comments", post.comments());
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
            List<Subscribe> subInfo = subscribeService.getFullInfoSubs(user.getUsername());
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
        }

        return "writeForm";
    }
//
//    @PostMapping("/write")
//    public String writePost() {
//
//        return "redirect:/{slug}";
//    }
//
//    @GetMapping("{postId}")
//    public String readPost() {
//
//
//        return "post";
//    }


}
