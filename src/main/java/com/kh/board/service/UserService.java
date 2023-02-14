package com.kh.board.service;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.UpdateUserRequest;
import com.kh.board.dto.request.UserSignUpDto;
import com.kh.board.exception.UserException;
import com.kh.board.exception.UserExceptionType;
import com.kh.board.repository.UserRepository;
import com.kh.board.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private final PasswordEncoder passwordEncoder;


    public void signUp(UserSignUpDto userSignUpDto) throws  Exception {
        User user = userSignUpDto.toEntity();
        user.encodePassword(passwordEncoder);
        if(userRepository.findById(userSignUpDto.userId()).isPresent()){
            throw new UserException(UserExceptionType.ALREADY_EXIST_USERNAME);
        }
        if(userRepository.existsByNickname(userSignUpDto.nickname())){
            throw new UserException(UserExceptionType.ALREADY_EXIST_NICKNAME);
        }
        if(userRepository.existsByEmail(userSignUpDto.email())){
            throw new UserException(UserExceptionType.ALREADY_EXIST_EMAIL);
        }
        user.setPoint(3000);

        userRepository.save(user);
    }


    public UserDto getUser(String nickname) {
        User user = userRepository.findByNicknameEquals(nickname).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));

        return UserDto.from(user);
    }
    public UserDto getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        return UserDto.from(user);
    }

    public UserDto getMyInfo() throws Exception {
        User user = userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        return UserDto.from(user);
    }


    public void updateUser(UpdateUserRequest updateUserRequest, String userId) throws Exception{
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        updateUserRequest.nickname().ifPresent(user::updateNickname);
    }


    public void updatePassword(String asIsPassword, String newPassword, String userId) throws Exception{
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        if(!user.matchPassword(passwordEncoder, asIsPassword)){
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }
        user.updatePassword(passwordEncoder, newPassword);
    }

    public boolean checkPassword(String password) {
        return passwordEncoder.matches(password, userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(()-> new UserException(UserExceptionType.NOT_FOUND_MEMBER)).getPassword());
    }

    public void withdraw(String checkPassword, String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        if(!user.matchPassword(passwordEncoder, checkPassword)){
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }

        userRepository.delete(user);
    }
}
