package com.xixizai.personalblogwebsite.controller.home;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.PersonalInforService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("home/personalInfo")
public class PersonalInfoController {

    @Resource
    private PersonalInforService personalInforService;

    /**
     * 获取个人信息
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getPersonalInfor() throws GetOptsException {
        return personalInforService.getPersonalInfor();
    }


}
