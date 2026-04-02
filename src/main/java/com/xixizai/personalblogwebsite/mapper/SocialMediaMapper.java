package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.SocialMediaDTO;
import com.xixizai.personalblogwebsite.pojo.entity.SocialMedia;
import com.xixizai.personalblogwebsite.pojo.vo.SocialMediaVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SocialMediaMapper  {

    //获取社交媒体列表
    @Select("select * from social_media")
    List<SocialMediaVO> getAllSocialMedias();

    //添加社交媒体
    @Insert("insert into social_media (name,link,icon,sort,is_visible,create_time)values(#{name},#{link},#{icon},#{sort},#{isVisible},now())")
    void addSocialMedia(SocialMediaDTO socialMediaDTO);

    //根据id查找社交媒体
    @Select("select * from social_media where id=#{id}")
    SocialMedia findById(Long id);

    //根据id更新社交媒体
    @Update("update social_media set name=#{name},link=#{link} ,icon=#{icon} ,sort=#{sort},is_visible=#{isVisible},update_time=now() where id=#{id}")
    void updateSocialMedia(SocialMediaDTO socialMediaDTO);

    //批量删除社交媒体
    void batchDeleteSocialMedias(List<Long> ids);
}
