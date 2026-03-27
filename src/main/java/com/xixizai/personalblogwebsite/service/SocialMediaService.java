package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteSocialMediasException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.SocialMediaDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocialMediaService {
    //获取所有社交媒体
    Result getAllSocialMedias() throws GetOptsException;

    //添加社交媒体
    Result addSocialMedia(SocialMediaDTO socialMediaDTO) throws AddOperationException;

    //更新社交媒体
    Result updateSocialMedia(SocialMediaDTO socialMediaDTO) throws PassedParameterException, UpdateOperationsException;

    //批量删除社交媒体
    Result batchDeleteSocialMedia(List<Long> ids) throws BatchDeleteSocialMediasException;
}
