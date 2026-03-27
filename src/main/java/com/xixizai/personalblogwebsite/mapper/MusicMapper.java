package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.MusicDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Music;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MusicMapper {

    //根据id查询音乐
    @Select("select * from music where id=#{id}")
    Music getById(Long id);

    //添加音乐
    @Insert("insert into music (title, artist, duration, cover_image, music_url, lyric_url, has_lyric, lyric_type, sort, is_visible, create_time, update_time) values (#{title},#{artist},#{duration},#{coverImage},#{musicUrl},#{lyricUrl},#{hasLyric},#{lyricType},#{sort},#{isVisible},NOW(),NOW())")
    void addMusic(MusicDTO musicDTO);
}
