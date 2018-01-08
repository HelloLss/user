package com.lss.user.exception;

/**
 * @author liushuaishuai
 * @date 2018/1/4 下午2:11
 */
public class UserException extends RuntimeException {

    private String message;
    private int code;


    public UserException(){

    }

    public UserException(String message){
        this.message = message;
    }


    public UserException(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
