package com.kh.board.controller;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.UserRepository;
import com.kh.board.security.BoardPrincipal;
import com.kh.board.service.ChannelManagerService;
import com.kh.board.service.ChannelService;
import com.kh.board.service.SubscribeService;
import com.kh.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final SubscribeService subscribeService;
    private final ChannelService channelService;
    private final ChannelManagerService channelManagerService;


    @GetMapping("/")
    public String index(@AuthenticationPrincipal BoardPrincipal user, Model m) {
        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<Subscribe> subInfo = subscribeService.getFullInfoSubs(user.getUsername());
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
        }

        List<ChannelDto> chanList = channelService.getChannelList();
        Collections.sort(chanList, Collections.reverseOrder());

        m.addAttribute("chanList", chanList);

        return "index";
    }

}
