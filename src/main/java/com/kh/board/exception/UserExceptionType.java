package com.kh.board.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.kh.board.exception.BaseExceptionType;

@RequiredArgsConstructor
public enum UserExceptionType implements BaseExceptionType{

    ALREADY_EXIST_USERNAME(600, HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    WRONG_PASSWORD(601,HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    NOT_FOUND_MEMBER(602, HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    ALREADY_EXIST_NICKNAME(603, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    ALREADY_EXIST_EMAIL(604, HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),;

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    UserExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
