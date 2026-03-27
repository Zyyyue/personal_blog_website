package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.mapper.PersonalInforMapper;
import com.xixizai.personalblogwebsite.pojo.dto.PersonalInfoDTO;
import com.xixizai.personalblogwebsite.pojo.entity.PersonalInfo;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public interface PersonalInforService {

    //获取个人信息
    Result getPersonalInfor() throws GetOptsException;

    //更新个人信息
    Result updatePersonalInfor(PersonalInfoDTO personalInfoDTO) throws PassedParameterException, UpdateOperationsException;
}
