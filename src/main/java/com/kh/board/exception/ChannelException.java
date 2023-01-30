package com.kh.board.exception;

public class ChannelException extends BaseException{

    private BaseExceptionType exceptionType;

    public ChannelException(BaseExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
