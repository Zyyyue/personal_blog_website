package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.ExperienceMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ExperienceDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Experiences;
import com.xixizai.personalblogwebsite.pojo.result.Result;
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


}

