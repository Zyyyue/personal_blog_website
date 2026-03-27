package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.mapper.SkillMapper;
import com.xixizai.personalblogwebsite.pojo.dto.SkillDTO;
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


    /**
     * 添加技能
     * @param skillDTO
     * @return
     * @throws AddOperationException
     */
    @Override
    public Result addSkill(SkillDTO skillDTO) throws AddOperationException {
        try{

            if(skillDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            skillMapper.addSkill(skillDTO);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }

    /**
     * 更新技能
     * @param skillDTO
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateSkill(SkillDTO skillDTO) throws UpdateOperationsException {
        try{

            if(skillDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(skillDTO.getId()==null){
                throw new PassedParameterException(MessageConstant.ID_NOT_FOUND);
            }


            if(skillMapper.findById(skillDTO.getId())==null){
                return Result.error("此id的数据不存在");
            }

            skillMapper.updateSkill(skillDTO);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

}
