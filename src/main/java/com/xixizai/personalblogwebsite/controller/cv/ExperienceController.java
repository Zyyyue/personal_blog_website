package com.xixizai.personalblogwebsite.controller.cv;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ExperienceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("cvExperienceController")
@RequestMapping("/cv/experience")
public class ExperienceController {

    @Resource
    private ExperienceService experienceService;

    /**
     * 获取经历
     * @return
     */
    @GetMapping()
    public Result getExperiences() throws GetOptsException {
        return experienceService.getExperiences();
    }

}
