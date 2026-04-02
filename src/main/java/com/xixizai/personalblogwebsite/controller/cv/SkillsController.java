package com.xixizai.personalblogwebsite.controller.cv;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("cvSkillController")
@RequestMapping("/cv/skill")
public class SkillsController {

    @Resource
    private SkillService skillservice;

    /**
     * 获取技能列表
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getSkill() throws GetOptsException {
        return skillservice.getAllSkills();
    }

}
