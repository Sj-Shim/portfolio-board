package com.kh.board.service;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import com.kh.board.dto.SubscribeDto;
import com.kh.board.dto.UserDto;
import com.kh.board.dto.request.UpdateUserRequest;
import com.kh.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired private UserRepository userRepository;

    public void createUser(UserDto dto) {
        User user = User.of(dto.userId(), dto.password(), dto.email(), dto.nickname());
        user = userRepository.save(user);
    }

    public UserDto getUser(String nickname) {
        User user = userRepository.findByNicknameEquals(nickname).orElse(null);
        UserDto userDto = UserDto.from(user);

        return userDto;
    }

    public void updateUser(String userId, UpdateUserRequest request){
        try {
            User user = userRepository.getReferenceById(userId);
            if (request.password() != null) {
                user.setPassword(request.password());
            }
            if (request.email() != null && !user.getEmail().equals(request.email())) {
                if(!userRepository.existsByEmail(request.email())) {
                    user.setEmail(request.email());
                } else {
                    log.warn("이미 등록된 이메일입니다.");
                }
            }
            if (request.nickname() != null && !user.getNickname().equals(request.nickname())) {
                if(!userRepository.existsByNickname(request.nickname())) {
                    user.setNickname(request.nickname());
                } else {
                    log.warn("이미 사용중인 별명입니다.");
                }
            }
        } catch (EntityNotFoundException e) {
            log.warn("사용자를 찾을 수 없습니다.");
        }
    }

    public void deleteUser(String userId, String password) {
        try {
            User user = userRepository.getReferenceById(userId);
            if (user.getPassword().equals(password)) {
                userRepository.delete(user);
            } else {
                log.warn("비밀번호가 일치하지 않습니다.");
            }
        }catch (EntityNotFoundException e){
            log.warn("사용자를 찾을 수 없습니다.");
        }
    }
}
