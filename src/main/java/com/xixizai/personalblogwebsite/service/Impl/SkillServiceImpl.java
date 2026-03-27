package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.SkillMapper;
import com.xixizai.personalblogwebsite.pojo.dto.SkillDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Skills;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SkillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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


    /**
     * 批量删除技能
     * @param ids
     * @return
     * @throws BatchDeleteSkillsException
     */
    @Override
    public Result batchDeleteSkill(List<Long> ids) throws BatchDeleteSkillsException {
        try {

            //判空
            if(ids==null||ids.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //去重一下id
            List<Long>distinctIds=new ArrayList<>();
            for (Long id : ids) {
                if(!distinctIds.contains(id)){
                    distinctIds.add(id);
                }
            }

            //再看一下数据库中是否有对应id

            //数据库中存在id的集合是updatedIds
            List<Long>updatedIds=new ArrayList<>();
            //数据库中不存在id的集合是nulledIds
            List<Long>nulledIds=new ArrayList<>();
            //再判断一下ids中的id是否都在数据库中存在,如果不存在的话就需要提示一下，然后删除已经存在的
            for (Long id : distinctIds) {
                if(skillMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除技能
            if(!updatedIds.isEmpty()){
                skillMapper.batchDeleteSkills(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 个技能/"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 个，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteSkillsException(MessageConstant.BATCH_DELETE_SKILLS_FAILSURE);
        }
    }

}
