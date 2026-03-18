package com.xixizai.personalblogwebsite.exception;

public class VerifyCodeErrorException extends Exception{
    public VerifyCodeErrorException(){

    }

    public VerifyCodeErrorException(String msg){
        super(msg);
    }

}
