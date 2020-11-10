package com.example.comm.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("你找的问题不在了，换一个试试");

    private String message;
    public String getMessage(){
        return  message;
    }
    CustomizeErrorCode(String message){
        this.message=message;
    }


}
