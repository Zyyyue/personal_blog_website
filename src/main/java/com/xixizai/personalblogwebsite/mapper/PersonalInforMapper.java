package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.PersonalInfoDTO;
import com.xixizai.personalblogwebsite.pojo.entity.PersonalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PersonalInforMapper {

    //获取个人信息
    @Select("select * from personal_info ")
    PersonalInfo getPersonalInfor();

    //更新个人信息
    @Update("update  personal_info set nickname=#{nickname},avatar=#{avatar},description=#{description},github=#{github},email=#{email},update_time=now() where id=#{id}")
    void updatePersonalInfor(PersonalInfoDTO personalInfoDTO);
}
