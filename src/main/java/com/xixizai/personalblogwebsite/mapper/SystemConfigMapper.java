package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.SystemConfigDTO;
import com.xixizai.personalblogwebsite.pojo.entity.SystemConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    //添加配置
    @Insert("insert into system_config (config_key, config_value, config_type, description, create_time, update_time) values (#{configKey},#{configValue},#{configType},#{description},now(),now())")
    void addSystemConfig(SystemConfigDTO systemConfigdto);

    //更新配置
    @Update("update system_config set config_key=#{configKey},config_value=#{configValue},config_type=#{configType},description=#{description},update_time=now() where id=#{id}")
    void updateSystemConfig(SystemConfigDTO systemConfigdto);

    //批量删除配置
    void batchDeleteSystemConfigs(List<Long> ids);
}
