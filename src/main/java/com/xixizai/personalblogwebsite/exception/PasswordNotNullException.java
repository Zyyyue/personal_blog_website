package com.xixizai.personalblogwebsite.exception;

public class PasswordNotNullException extends Exception{
    public PasswordNotNullException(){

    }

    public PasswordNotNullException(String msg){
        super(msg);
    }
}
