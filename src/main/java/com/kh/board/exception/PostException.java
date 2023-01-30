package com.kh.board.exception;

public class PostException extends BaseException{

    private BaseExceptionType exceptionType;
    public PostException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
