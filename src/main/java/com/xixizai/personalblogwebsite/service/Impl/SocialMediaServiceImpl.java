package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.SocialMediaMapper;
import com.xixizai.personalblogwebsite.pojo.dto.SocialMediaDTO;
import com.xixizai.personalblogwebsite.pojo.entity.SocialMedia;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SocialMediaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {


    @Resource
    private SocialMediaMapper socialMediaMapper;

    /**
     * 获取所有社交媒体
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getAllSocialMedias() throws GetOptsException {
        try{

            List<SocialMedia> list =socialMediaMapper.getAllSocialMedias();

            if(list==null||list.isEmpty()){
                return Result.error("数据库中没有任何社交媒体");
            }

            return Result.success(list);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 添加社交媒体
     * @param socialMediaDTO
     * @return
     * @throws AddOperationException
     */
    @Override
    public Result addSocialMedia(SocialMediaDTO socialMediaDTO) throws AddOperationException {
        try{

            //判断是否为空
            if(socialMediaDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            socialMediaMapper.addSocialMedia(socialMediaDTO);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }


    }


    /**
     * 更新社交媒体
     * @param socialMediaDTO
     * @return
     * @throws PassedParameterException
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateSocialMedia(SocialMediaDTO socialMediaDTO) throws PassedParameterException, UpdateOperationsException {
        try{

            if(socialMediaDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(socialMediaDTO.getId()==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            if(socialMediaMapper.findById(socialMediaDTO.getId())==null){
                return Result.error(MessageConstant.ID_NOT_FOUND);
            }

            socialMediaMapper.updateSocialMedia(socialMediaDTO);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }


    /**
     * 批量删除社交媒体
     * @param ids
     * @return
     * @throws BatchDeleteSocialMediasException
     */
    @Override
    public Result batchDeleteSocialMedia(List<Long> ids) throws BatchDeleteSocialMediasException {
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
                if(socialMediaMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除社交媒体
            if(!updatedIds.isEmpty()){
                socialMediaMapper.batchDeleteSocialMedias(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 个社交媒体"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 个，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteSocialMediasException(MessageConstant.BATCH_DELETE_SOCIAL_MEDIAS_FAILSURE);
        }
    }

}
