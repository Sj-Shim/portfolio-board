package com.kh.board.exception;

import org.springframework.http.HttpStatus;

public enum ChannelExceptionType implements BaseExceptionType{

    CHANNEL_NOT_FOUND(700, HttpStatus.NOT_FOUND, "찾으시는 채널이 없습니다"),
    NOT_AUTHORITY_UPDATE_CHANNEL(701, HttpStatus.FORBIDDEN, "채널을 업데이트할 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_CHANNEL(702, HttpStatus.FORBIDDEN, "채널을 삭제할 권한이 없습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    ChannelExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
