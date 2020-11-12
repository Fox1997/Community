package com.example.comm.dto;

import com.example.comm.exception.CustomizeErrorCode;
import com.example.comm.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTo {
    private Integer code;
    private String message;

    public  static ResultDTo errorOf(Integer code,String message){
        ResultDTo resultDTo = new ResultDTo();
        resultDTo.setCode(code);
        resultDTo.setMessage(message);
        return  resultDTo;
    }

    public static ResultDTo errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTo okOf(){
        ResultDTo resultDTo =new ResultDTo();
        resultDTo.setCode(200);
        resultDTo.setMessage("成功");
        return  resultDTo;
    }

    public  static  ResultDTo errorOf(CustomizeException e){
        return errorOf(e.getCode(),e.getMessage());

    }
}
