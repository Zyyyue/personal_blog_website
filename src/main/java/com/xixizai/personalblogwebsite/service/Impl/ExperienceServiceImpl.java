package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.ExperienceMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ExperienceDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Experiences;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.ExperienceVO;
import com.xixizai.personalblogwebsite.service.ExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {


    @Resource
    private ExperienceMapper experienceMapper;

    /**
     * 获取经历列表
     * @param types
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getExperienceList(List<Integer> types) throws GetOptsException {

        try{

            if(types==null||types.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //去重一下
            List<Integer>distinctTypes=new ArrayList<>();
            for (Integer type : types) {
                if (!distinctTypes.contains(type)) {
                    distinctTypes.add(type);
                }
            }

            List<Integer>correctTypes=new ArrayList<>();
            List<Integer>wrongTypes=new ArrayList<>();

            for (Integer type : distinctTypes) {
                if (type == 0 || type == 1 || type == 2) {
                    correctTypes.add(type);
                } else {
                    wrongTypes.add(type);
                }
            }

            //  验证并查询
            if (correctTypes.isEmpty()) {
                return Result.error("没有有效的类型参数，有效类型：0(教育)、1(工作)、2(项目)");
            }

            List<Experiences> list = experienceMapper.getExperienceList(correctTypes);

            // 返回结果
            return Result.success(list);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }


    }

    /**
     * 添加经历
     * @param experienceDTO
     * @return
     * @throws AddOperationException
     */
    @Override
    public Result addExperience(ExperienceDTO experienceDTO) throws AddOperationException {
        try{

            if(experienceDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            Integer type = experienceDTO.getType();
            //0-教育，1-工作，2-项目)
            if(type==1||type==0||type==2){
                experienceMapper.addExperience(experienceDTO);
            }else{
                return Result.error("type不合法");
            }

            return Result.success("添加经历成功");

        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }

    /**
     * 更新经历
     * @param experienceDTO
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateExperience(ExperienceDTO experienceDTO) throws UpdateOperationsException {
        try{

            if(experienceDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(experienceDTO.getId()==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            experienceMapper.updateExperience(experienceDTO);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 批量删除经历
     * @param ids
     * @return
     * @throws BatchDeleteExperienceException
     */
    @Override
    public Result batchDeleteExperience(List<Long> ids) throws BatchDeleteExperienceException {
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
                if(experienceMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除经历
            if(!updatedIds.isEmpty()){
                experienceMapper.batchDeleteExperiences(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 条经历/"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteExperienceException(MessageConstant.BATCH_DELETE_EXPERIENCE_FAILSURE);
        }
    }

    /**
     * 简历端获取经历
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getExperiences() throws GetOptsException {
       try{

           List<ExperienceVO>list=experienceMapper.getExperiences();
           if(list==null||list.isEmpty()){
               return Result.error("没有数据");
           }
           return Result.success(list);
       }catch (Exception exception){
       exception.printStackTrace();
       throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);

       }

    }


}

