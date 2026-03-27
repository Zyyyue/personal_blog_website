package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.SocialMediaDTO;
import com.xixizai.personalblogwebsite.pojo.entity.SocialMedia;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SocialMediaMapper  {

    @Select("select * from social_media")
    List<SocialMedia> getAllSocialMedias();

    @Insert("insert into social_media (name,link,icon,sort,is_visible,create_time)values(#{name},#{link},#{icon},#{sort},#{isVisible},now())")
    void addSocialMedia(SocialMediaDTO socialMediaDTO);
}
