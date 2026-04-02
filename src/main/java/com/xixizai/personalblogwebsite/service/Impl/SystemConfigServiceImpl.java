package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.SystemConfigMapper;
import com.xixizai.personalblogwebsite.pojo.dto.SystemConfigDTO;
import com.xixizai.personalblogwebsite.pojo.entity.SystemConfig;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SystemConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    /**
     * 获取所有配置
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getAllSystemConfigs() throws GetOptsException {
        try{

            List<SystemConfig>list =systemConfigMapper.getAllSystemConfig();
            if(list==null||list.isEmpty()){
                return Result.error("没有系统配置，获取失败");
            }

            return Result.success(list);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }

    /**
     * 根据配置键获取配置
     * @param configKey
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getSystemConfigByConfigKey(String configKey) throws GetOptsException {

        try{

            if(configKey==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            SystemConfig systemConfig=systemConfigMapper.getSystemConfigByConfigKey(configKey);

            if(systemConfig==null){
                return Result.error("暂无任何系统配置");
            }
            return Result.success(systemConfig);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }

    /**
     * 根据id获取配置
     * @param id
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getSystemConfigById(Long id) throws GetOptsException {
        try{

            if(id==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            SystemConfig systemConfig=systemConfigMapper.getSystemConfigById(id);
            if(systemConfig==null){
                return Result.error("暂无任何系统配置");
            }
            return Result.success(systemConfig);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 添加配置
     * @param systemConfigdto
     * @return
     * @throws AddOperationException
     */
    @Override
    public Result addSystemConfig(SystemConfigDTO systemConfigdto) throws AddOperationException {
        try{

            if(systemConfigdto==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            systemConfigMapper.addSystemConfig(systemConfigdto);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }

    }

    /**
     * 更新配置
     * @param systemConfigdto
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateSystemConfig(SystemConfigDTO systemConfigdto) throws UpdateOperationsException {
        try{

            if(systemConfigdto==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(systemConfigdto.getId()==null){
                throw new PassedParameterException(MessageConstant.ID_NOT_FOUND);
            }

            systemConfigMapper.updateSystemConfig(systemConfigdto);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 批量删除配置
     * @param ids
     * @return
     * @throws BatchDeleteSystemConfigsException
     */
    @Override
    public Result batchDeleteSystemConfigs(List<Long> ids) throws BatchDeleteSystemConfigsException {
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
                if(systemConfigMapper.getSystemConfigById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除系统配置
            if(!updatedIds.isEmpty()){
                systemConfigMapper.batchDeleteSystemConfigs(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 个系统配置"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 个，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteSystemConfigsException(MessageConstant.BATCH_DELETE_SYSTEM_CONFIGS_FAILSURE);
        }
    }

}
