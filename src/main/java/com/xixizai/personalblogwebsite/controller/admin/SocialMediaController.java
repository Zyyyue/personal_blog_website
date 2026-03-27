package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.dto.SocialMediaDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SocialMediaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/socialMedia")
public class SocialMediaController {


    @Resource
    private SocialMediaService socialMediaService;

    /**
     * 获取所有社交媒体
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getSocialMedia() throws GetOptsException {
        return socialMediaService.getAllSocialMedias();
    }


    @PostMapping()
    public Result addSocialMedia(@RequestBody SocialMediaDTO socialMediaDTO){
        return socialMediaService.addSocialMedia(socialMediaDTO);
    }


}
