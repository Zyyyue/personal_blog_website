package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteSkillsException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.SkillDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkillService {
    //获取所有技能
    Result getAllSkills() throws GetOptsException;

    //添加技能
    Result addSkill(SkillDTO skillDTO) throws AddOperationException;

    //更新技能
    Result updateSkill(SkillDTO skillDTO) throws UpdateOperationsException;

    //批量删除技能
    Result batchDeleteSkill(List<Long> ids) throws BatchDeleteSkillsException;
}
