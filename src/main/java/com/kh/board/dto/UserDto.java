package com.kh.board.dto;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link com.kh.board.domain.User} entity
 */
public record UserDto(String userId
        , String password
        , String email
        , String nickname
        , Integer point
        , LocalDateTime createdDate
        , LocalDateTime modifiedDate) implements Serializable {

    public static UserDto of(
            String userId
            , String password
            , String email
            , String nickname
            , Integer point){
        return new UserDto(userId, password, email, nickname, point, null, null);
    }
    public static UserDto of(
            String userId
            , String password
            , String email
            , String nickname
            , Integer point
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate
    ){
        return new UserDto(userId, password, email, nickname, point, createdDate, modifiedDate);
    }
    public static UserDto of(
            String userId
            , String password
            , String email
            , String nickname
    ){
        return new UserDto(userId, password, email, nickname, null,null, null);
    }

    public static UserDto from(User user) {
        return new UserDto(user.getUserId(), user.getPassword(), user.getEmail(), user.getNickname(), user.getPoint(), user.getCreatedDate(), user.getModifiedDate());
    }

    public User toEntity() {
        return User.of(this.userId, this.password, this.email, this.nickname);
    }


    public static List<SubscribeDto> subListfrom(List<Subscribe> subscribes) {
        return subscribes.stream().map(SubscribeDto::from).collect(Collectors.toList());
    }
}