package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.mapper.PersonalInforMapper;
import com.xixizai.personalblogwebsite.pojo.entity.PersonalInfo;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public interface PersonalInforService {


    Result getPersonalInfor() throws GetOptsException;
}
