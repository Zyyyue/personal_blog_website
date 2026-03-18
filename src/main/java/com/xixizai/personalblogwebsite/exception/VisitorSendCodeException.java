package com.xixizai.personalblogwebsite.exception;

//游客无需验证码登录
public class VisitorSendCodeException extends Exception {

    public VisitorSendCodeException(){

    }

    public VisitorSendCodeException(String msg){
    super(msg);
    }
}
