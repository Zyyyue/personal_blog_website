package com.xixizai.personalblogwebsite.controller.blog;

import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("blogCommonController")
@RequestMapping("/blog/common")
public class CommonController {

    @Resource
    private CommonService commonService;

    /**
     * 生成算术验证码
     * @return
     * @throws Exception
     */
    @GetMapping("/captcha/generate")
    public Result getCaptchaVerifyCode() throws Exception {
        return commonService.generateCode();
    }

}
