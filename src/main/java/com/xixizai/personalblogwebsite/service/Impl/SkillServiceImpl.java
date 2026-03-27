package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.SkillMapper;
import com.xixizai.personalblogwebsite.pojo.entity.Skills;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SkillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Resource
    private SkillMapper skillMapper;

    /**
     * 获取所有技能
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getAllSkills() throws GetOptsException {
        try{
            List<Skills> list=skillMapper.getAllSkills();
            if(list==null){
                return Result.error("没有数据，获取失败");
            }
            return Result.success(list);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }

}
