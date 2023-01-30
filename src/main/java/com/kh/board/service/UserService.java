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
    private final PasswordEncoder passwordEncoder;

//    public void createUser(UserDto dto) {
//        User user = User.of(dto.userId(), dto.password(), dto.email(), dto.nickname());
//        user = userRepository.save(user);
//    }

    public void signUp(UserSignUpDto userSignUpDto) throws  Exception {
        User user = userSignUpDto.toEntity();
        user.encodePassword(passwordEncoder);
        if(userRepository.findByUserId(userSignUpDto.userId()).isPresent()){
            throw new UserException(UserExceptionType.ALREADY_EXIST_USERNAME);
        }
        userRepository.save(user);
    }


    public UserDto getUser(String nickname) {
        User user = userRepository.findByNicknameEquals(nickname).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));

        return UserDto.from(user);
    }
    public UserDto getUserInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        return UserDto.from(user);
    }

    public UserDto getMyInfo() throws Exception {
        User user = userRepository.findById(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        return UserDto.from(user);
    }

//    public void updateUser(String userId, UpdateUserRequest request){
//        try {
//            User user = userRepository.getReferenceById(userId);
//            if (request.password() != null) {
//                user.setPassword(request.password());
//            }
//            if (request.email() != null && !user.getEmail().equals(request.email())) {
//                if(!userRepository.existsByEmail(request.email())) {
//                    user.setEmail(request.email());
//                } else {
//                    log.warn("이미 등록된 이메일입니다.");
//                }
//            }
//            if (request.nickname() != null && !user.getNickname().equals(request.nickname())) {
//                if(!userRepository.existsByNickname(request.nickname())) {
//                    user.setNickname(request.nickname());
//                } else {
//                    log.warn("이미 사용중인 별명입니다.");
//                }
//            }
//        } catch (EntityNotFoundException e) {
//            log.warn("사용자를 찾을 수 없습니다.");
//        }
//    }
    public void updateUser(UpdateUserRequest updateUserRequest, String userId) throws Exception{
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        updateUserRequest.nickname().ifPresent(user::updateNickname);
        updateUserRequest.email().ifPresent(user::updateEmail);
    }


    public void updatePassword(String asIsPassword, String newPassword, String userId) throws Exception{
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        if(!user.matchPassword(passwordEncoder, asIsPassword)){
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }
        user.updatePassword(passwordEncoder, newPassword);
    }

//    public void deleteUser(String userId, String password) {
//        try {
//            User user = userRepository.getReferenceById(userId);
//            if (user.getPassword().equals(password)) {
//                userRepository.delete(user);
//            } else {
//                log.warn("비밀번호가 일치하지 않습니다.");
//            }
//        }catch (EntityNotFoundException e){
//            log.warn("사용자를 찾을 수 없습니다.");
//        }
//    }

    public void withdraw(String checkPassword, String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER));
        if(!user.matchPassword(passwordEncoder, checkPassword)){
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }

        userRepository.delete(user);
    }
}
