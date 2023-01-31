package com.kh.board.dto.request;

import com.kh.board.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record UserSignUpDto(
        @NotBlank(message = "아이디를 입력해주세요") @Size(min = 7, max = 20, message = "아이디는 7 ~20자 내외로 입력해주세요") String userId,
        @NotBlank(message = "비밀번호를 입력해주세요") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$" , message = "비밀번호는 8~30 자리로 1개 이상의 알파벳, 숫자, 특수문자를 포함해야 합니다.") String password,
        @NotBlank(message = "이메일을 입력해주세요") @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\\\.[a-z]+$") String email,
        @NotBlank(message = "닉네임을 입력해주세요") @Size(min = 2, message = "닉네임이 너무 짧습니다.") String nickname) {

    public static UserSignUpDto of(String userId, String password, String email, String nickname) {
        return new UserSignUpDto(userId, password, email, nickname);
    }
    public User toEntity() {
        return User.of(userId, password, email, nickname);
    }
}
