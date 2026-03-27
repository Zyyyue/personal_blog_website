package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.SkillDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface SkillService {
    //获取所有技能
    Result getAllSkills() throws GetOptsException;

    //添加技能
    Result addSkill(SkillDTO skillDTO) throws AddOperationException;
}
