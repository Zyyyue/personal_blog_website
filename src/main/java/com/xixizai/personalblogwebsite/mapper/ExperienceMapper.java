package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ExperienceDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Experiences;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExperienceMapper {
    //获取经历列表
    List<Experiences> getExperienceList(List<Integer> correctTypes);

    //添加经历
    @Insert("insert into experiences (type,title,subtitle,logo_url,content,start_date,end_date,is_visible,create_time)values(#{type},#{title},#{subtitle},#{logoUrl},#{content},#{startDate},#{endDate},#{isVisible},now())")
    void addExperience(ExperienceDTO experienceDTO);
}
