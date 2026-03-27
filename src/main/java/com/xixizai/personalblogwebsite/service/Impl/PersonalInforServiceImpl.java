package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.PersonalInforMapper;
import com.xixizai.personalblogwebsite.pojo.entity.PersonalInfo;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.PersonalInforService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonalInforServiceImpl implements PersonalInforService {

    @Resource
    private PersonalInforMapper personalInforMapper;


    /**
     * 获取个人信息
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getPersonalInfor() throws GetOptsException {

        try{

            PersonalInfo personalInfor = personalInforMapper.getPersonalInfor();
            //这个情况一般不存在哈🤭
            if(personalInfor==null){
                return Result.error("没有找到管理员");
            }
            return Result.success(personalInfor);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }

}
