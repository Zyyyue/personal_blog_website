package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Experiences;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExperienceMapper {
    //获取经历列表
    List<Experiences> getExperienceList(List<Integer> correctTypes);
}
