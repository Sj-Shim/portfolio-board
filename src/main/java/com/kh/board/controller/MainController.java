package com.kh.board.controller;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.ChannelRequest;
import com.kh.board.dto.request.UpdateUserRequest;
import com.kh.board.dto.request.UserSignUpDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
            UserDto userInfo = userService.getUserInfo(user.getUsername());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
        }

        List<ChannelDto> chanList = channelService.getChannelList();
        Collections.sort(chanList, Collections.reverseOrder());

        m.addAttribute("chanList", chanList);

        return "index";
    }

    @GetMapping("/search")
    public String channelSearch(@AuthenticationPrincipal BoardPrincipal user,
                                @RequestParam String keyword,

                                Model m) {

        if(user!=null){
            UserDto userInfo = userService.getUserInfo(user.getUsername());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
        }

        List<ChannelDto> chanList = channelService.getSearchedChannel(keyword);
        Collections.sort(chanList, Collections.reverseOrder());

        m.addAttribute("chanList", chanList);
        return "index";
    }

    @GetMapping("/signup")
    public String signUp() {

        return "registerForm";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String userId, @RequestParam String password,
                               @RequestParam String email, @RequestParam String nickname) throws Exception {
        UserSignUpDto userSignUpDto = UserSignUpDto.of(userId, password, email, nickname);
        userService.signUp(userSignUpDto);

        return "redirect:/";
    }

    @GetMapping("/createChannel")
    public String createChannelForm(@AuthenticationPrincipal BoardPrincipal user, Model m) {
        if(user!=null){
            UserDto userInfo = userService.getUser(user.nickname());
            List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
            m.addAttribute("userInfo", userInfo);
            m.addAttribute("subList", subInfo);
        }
        if(user.getUsername() == null || user.getUsername() == "") {
            return "redirect:/login";
        }

        return "channelForm";

    }

    @PostMapping("/createChannel")
    public String createChannelForm(
            @RequestParam String channelName
            , @RequestParam String description
            , @RequestParam String slug
            , @AuthenticationPrincipal BoardPrincipal user){
        if(user == null) {
            return "redirect:/login";
        }

        ChannelRequest channelRequest = ChannelRequest.of(channelName, description, slug);
        channelService.createChannel(channelRequest);

        return "redirect:/"+slug;
    }

    @GetMapping("/u/info")
    public String getUserInfoPage(
            @AuthenticationPrincipal BoardPrincipal user,
            Model m) throws Exception {
        if(user == null) return "redirect:/login";
        UserDto userDto = userService.getMyInfo();
        List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
        m.addAttribute("userInfo", userDto);
        m.addAttribute("subList", subInfo);

        return "userSetting";
    }

    @PostMapping("/u/info")
    public String changeNickname(
            @RequestParam String nickname,
            @AuthenticationPrincipal BoardPrincipal user) throws Exception {
        if(nickname!=null && !nickname.trim().isEmpty()) {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest(Optional.ofNullable(nickname));
            userService.updateUser(updateUserRequest, user.getUsername());
        }

        return "redirect:/u/info";
    }

    @GetMapping("/u/cancel-subscribe/{slug}")
    public String cancelSubscribe(
            @PathVariable String slug){

        subscribeService.deleteSubscribe(slug);

        return "redirect:/u/info";
    }

    @GetMapping("/u/account")
    public String checkPasswordPage(
            @AuthenticationPrincipal BoardPrincipal user, Model m) throws Exception {
        if(user == null) return "redirect:/login";
        UserDto userDto = userService.getMyInfo();
        List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
        m.addAttribute("userInfo", userDto);
        m.addAttribute("subList", subInfo);
        return "pwcheck";
    }
    @PostMapping("/u/account")
    public String checkPassword(
            @RequestParam String password,
            @AuthenticationPrincipal BoardPrincipal user,
            HttpServletResponse response,
            HttpServletRequest request
            ) throws Exception {


        if(!userService.checkPassword(password)){
            request.setAttribute("msg", "비밀번호가 틀렸습니다.");
            request.setAttribute("url", "/u/account");
            return "alert";
        }
        Cookie cookie = new Cookie("pwCheck", "confirmed");
        cookie.setMaxAge(30*60*60);
        response.addCookie(cookie);

        return "redirect:/u/password_change";
    }
    @GetMapping("/u/password_change")
    public String pwChangePage(
            HttpServletRequest request,
            @AuthenticationPrincipal BoardPrincipal user,
            Model m) throws Exception {
        if(user == null) return "redirect:/login";
        UserDto userDto = userService.getMyInfo();
        List<SubscribeDto> subInfo = subscribeService.getUserSubscribes(user.getUsername());
        m.addAttribute("userInfo", userDto);
        m.addAttribute("subList", subInfo);

        Cookie[] list = request.getCookies();
        boolean pwChk = false;
        for(Cookie cookie : list) {
            if(cookie.getName().equals("pwCheck")){
                pwChk = cookie.getValue().equals("confirmed")?true:false;
            }
        }

        if(!pwChk) return "redirect:/u/account";

        return "pwchange";
    }

    @PostMapping("/u/password_change")
    public String passwordChange(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @AuthenticationPrincipal BoardPrincipal user,
            HttpServletRequest request) throws Exception {
        User userInfo = userService.getMyInfo().toEntity();
        if(!userService.checkPassword(currentPassword)){
            request.setAttribute("msg", "비밀번호가 틀렸습니다.");
            request.setAttribute("url", "/u/password_change");
            return "alert";
        }

        userService.updatePassword(currentPassword, newPassword, user.getUsername());


        return "redirect:/u/info";
    }
}
