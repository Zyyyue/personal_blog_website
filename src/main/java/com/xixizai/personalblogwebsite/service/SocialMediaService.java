package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.SocialMediaDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface SocialMediaService {
    //获取所有社交媒体
    Result getAllSocialMedias() throws GetOptsException;

    //添加社交媒体
    Result addSocialMedia(SocialMediaDTO socialMediaDTO) throws AddOperationException;
}
