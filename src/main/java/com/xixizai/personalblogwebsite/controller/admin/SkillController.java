package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/skill")
public class SkillController {

    @Resource
    private SkillService skillService;

    /**
     * 获取所有技能
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getAllSkills() throws GetOptsException {
        return skillService.getAllSkills();
    }

}
