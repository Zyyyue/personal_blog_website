package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.ExperienceDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExperienceService {
    //获取经历列表
    Result getExperienceList(List<Integer> types) throws GetOptsException;

    //添加经历
    Result addExperience(ExperienceDTO experienceDTO) throws AddOperationException;
}
