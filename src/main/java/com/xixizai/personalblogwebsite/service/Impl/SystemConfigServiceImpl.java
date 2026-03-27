package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.mapper.SystemConfigMapper;
import com.xixizai.personalblogwebsite.pojo.dto.SystemConfigDTO;
import com.xixizai.personalblogwebsite.pojo.entity.SystemConfig;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.SystemConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * @param systemConfig
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

}
