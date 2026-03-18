package com.xixizai.personalblogwebsite.exception;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(){

    }

    public UnauthorizedException(String msg){
        super(msg);
    }
}
