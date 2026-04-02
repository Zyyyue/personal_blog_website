package com.xixizai.personalblogwebsite.controller.common;

import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class Health {

    @GetMapping()
    public Result health(){
        return Result.success("Server is running");
    }

}
