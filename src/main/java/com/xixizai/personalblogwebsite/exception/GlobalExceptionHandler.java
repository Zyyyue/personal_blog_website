package com.xixizai.personalblogwebsite.exception;

import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.apache.commons.codec.language.bm.RuleType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //捕捉全局异常
    @ExceptionHandler
    public Result handException(Exception e){
        //答应到控制台
        e.printStackTrace();
        return Result.error(e.getMessage().isEmpty()?"操作失败":e.getMessage());
    }
}
