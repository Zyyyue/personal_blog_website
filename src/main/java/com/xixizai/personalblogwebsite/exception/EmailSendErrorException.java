package com.xixizai.personalblogwebsite.exception;

//邮件发送失败的异常
public class EmailSendErrorException extends Exception{

    public EmailSendErrorException(){

    }

    public EmailSendErrorException(String msg){
        super(msg);
    }

}
