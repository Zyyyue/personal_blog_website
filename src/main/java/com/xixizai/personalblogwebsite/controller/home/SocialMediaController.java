package com.xixizai.personalblogwebsite.controller.home;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SocialMediaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("homeSocialMediaController")
@RequestMapping("/home/socialMedia")
public class SocialMediaController {

    @Resource
    private SocialMediaService socialMediaService;

    /**
     * 获取社交媒体
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getSocialMedia() throws GetOptsException {
        return socialMediaService.getAllSocialMedias();
    }

}
