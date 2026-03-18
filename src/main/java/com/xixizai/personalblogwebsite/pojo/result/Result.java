package com.xixizai.personalblogwebsite.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private Integer code;//业务状态码1表示成功,0表示失败
    private String msg;//错误信息
    private T data;//数据

    public static <T> Result<T> success(){
        Result<T> result=new Result<T>();
        result.code=200;
        result.msg="success";
        return result;
    }

    public static <T> Result <T> success(T object){
        Result<T> result=new Result<T>();
        result.data=object;
        result.code=200;
        result.msg="success";
        return result;
    }

    public static <T> Result <T> error(String msg){
        Result<T>result=new Result<T>();
        result.msg=msg;
        result.code=0;
        return result;
    }

}
