package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteSocialMediasException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.SocialMediaDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SocialMediaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("adminSocialMediaController")
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


    /**
     * 添加社交媒体
     * @param socialMediaDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addSocialMedia(@RequestBody SocialMediaDTO socialMediaDTO) throws AddOperationException {
        return socialMediaService.addSocialMedia(socialMediaDTO);
    }

    /**
     * 更新社交媒体
     * @param socialMediaDTO
     * @return
     * @throws PassedParameterException
     * @throws UpdateOperationsException
     */
    @PutMapping()
    public Result updateSocialMedia(@RequestBody SocialMediaDTO socialMediaDTO) throws PassedParameterException, UpdateOperationsException {
        return socialMediaService.updateSocialMedia(socialMediaDTO);
    }

    /**
     * 批量删除社交媒体
     * @param ids
     * @return
     * @throws BatchDeleteSocialMediasException
     */
    @DeleteMapping()
    public Result batchDeleteSocialMedia(@RequestParam List<Long> ids) throws BatchDeleteSocialMediasException {
        return socialMediaService.batchDeleteSocialMedia(ids);
    }


}
