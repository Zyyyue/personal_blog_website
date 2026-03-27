package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemConfigMapper {

    //获取所有配置
    List<SystemConfig> getAllSystemConfig();
}
