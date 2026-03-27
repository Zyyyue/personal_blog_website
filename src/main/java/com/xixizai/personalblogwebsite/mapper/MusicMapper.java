package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MusicMapper {

    //根据id查询音乐
    @Select("select * from music where id=#{id}")
    Music getById(Long id);

}
