package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.SocialMedia;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SocialMediaMapper  {

    @Select("select * from social_media")
    List<SocialMedia> getAllSocialMedias();
}
