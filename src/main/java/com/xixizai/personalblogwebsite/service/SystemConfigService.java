package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface SystemConfigService {
    //获取所有配置
    Result getAllSystemConfigs() throws GetOptsException;

    //根据配置键获取配置
    Result getSystemConfigByConfigKey(String configKey) throws GetOptsException;

    //根据id获取配置
    Result getSystemConfigById(Long id) throws GetOptsException;
}
