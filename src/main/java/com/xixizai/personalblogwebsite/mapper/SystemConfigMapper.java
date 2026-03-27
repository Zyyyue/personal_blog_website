package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SystemConfigMapper {

    //获取所有配置
    List<SystemConfig> getAllSystemConfig();

    //根据配置键获取配置
    @Select("select * from system_config where config_key=#{configKey}")
    SystemConfig getSystemConfigByConfigKey(String configKey);

    //根据id获取配置
    @Select("select * from system_config where id=#{id}")
    SystemConfig getSystemConfigById(Long id);
}
