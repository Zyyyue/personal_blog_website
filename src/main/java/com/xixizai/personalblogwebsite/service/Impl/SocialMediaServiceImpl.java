package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.SocialMediaMapper;
import com.xixizai.personalblogwebsite.pojo.entity.SocialMedia;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SocialMediaService;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Message;
import java.util.List;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {


    @Resource
    private SocialMediaMapper socialMediaMapper;

    /**
     * 获取所有社交媒体
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getAllSocialMedias() throws GetOptsException {
        try{

            List<SocialMedia> list =socialMediaMapper.getAllSocialMedias();

            if(list==null||list.isEmpty()){
                return Result.error("数据库中没有任何社交媒体");
            }

            return Result.success(list);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }
}
