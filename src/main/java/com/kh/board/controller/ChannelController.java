package com.kh.board.controller;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.type.SearchType;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.SubscribeRequest;
import com.kh.board.dto.response.PostResponse;
//import com.kh.board.repository.CategoryRepository;
import com.kh.board.repository.SubscribeRepository;
import com.kh.board.security.BoardPrincipal;
import com.kh.board.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{slug}")
public class ChannelController {

    private final PostService postService;
    private final ChannelService channelService;
//    @Autowired private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final SubscribeService subscribeService;
    private final PaginationService paginationService;
    private final SubscribeRepository subscribeRepository;

    @GetMapping
    public String getChannelMain(@AuthenticationPrincipal BoardPrincipal user
            , Model m
            , HttpServletRequest request
            , @RequestParam(required = false) SearchType target
            , @RequestParam(required = false) String keyword
            , @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        String slug = request.getRequestURL().toString().split("/")[3];
        Page<PostResponse> posts = postService.searchPosts(slug, target, keyword, pageable).map(PostResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),posts.getTotalPages());

        m.addAttribute("posts", posts);
        m.addAttribute("pageNumbers", barNumbers);
        m.addAttribute("target", target.values());

        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<Subscribe> subInfo = subscribeService.getFullInfoSubs(user.getUsername());
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
        }
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        m.addAttribute("chanInfo", channelDto);


        return "channelMain";
    }

    @GetMapping("/subscribe")
    public String addSubscribe(@AuthenticationPrincipal BoardPrincipal user, HttpServletRequest request){

        String slug = request.getRequestURL().toString().split("/")[3];
        if(user == null){
            return "redirect:/login";
        }
        ChannelDto channelDto = channelService.getChannelBySlug(slug);
        if(subscribeService.checkSubscribe(channelDto.channelName(), user.username())){
            subscribeService.deleteSubscribe(user.username(), channelDto.channelName());
            return "redirect:"+request.getHeader("Referer");
        }
        subscribeService.addSubscribe(SubscribeRequest.of(channelDto.channelName(), user.username()));

        return "redirect:"+request.getHeader("Referer");
    }

    @GetMapping("/write")
    public String loadPostForm(@AuthenticationPrincipal BoardPrincipal user
            , Model m
            , HttpServletRequest request) {
        if(user == null){
            return "redirect:/login";
        }
        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<Subscribe> subInfo = subscribeService.getFullInfoSubs(user.getUsername());
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
        }

        return "writeForm";
    }

    @PostMapping("/write")
    public String writePost() {

        return "redirect:/{slug}";
    }

    @GetMapping("{postId}")
    public String readPost() {


        return "post";
    }
}
