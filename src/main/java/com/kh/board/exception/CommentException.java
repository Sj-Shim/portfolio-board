package com.kh.board.exception;

public class CommentException extends BaseException{
    private BaseExceptionType exceptionType;
    public CommentException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
