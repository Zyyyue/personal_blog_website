package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.SystemConfigMapper;
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

}
