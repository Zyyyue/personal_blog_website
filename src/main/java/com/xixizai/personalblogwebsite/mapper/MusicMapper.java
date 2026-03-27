package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.MusicDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Music;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MusicMapper {

    //根据id查询音乐
    @Select("select * from music where id=#{id}")
    Music getById(Long id);

    //添加音乐
    @Insert("insert into music (title, artist, duration, cover_image, music_url, lyric_url, has_lyric, lyric_type, sort, is_visible, create_time, update_time) values (#{title},#{artist},#{duration},#{coverImage},#{musicUrl},#{lyricUrl},#{hasLyric},#{lyricType},#{sort},#{isVisible},NOW(),NOW())")
    void addMusic(MusicDTO musicDTO);

    @Update("update music set title=#{title},artist=#{artist},duration=#{duration},cover_image=#{coverImage},music_url=#{musicUrl},lyric_url=#{lyricUrl},has_lyric=#{hasLyric},lyric_type=#{lyricType},sort=#{sort},is_visible=#{isVisible},create_time=now(),update_time=now() where id=#{id}")
    void updateMusic(MusicDTO musicDTO);

    //批量删除音乐
    void batchDeleteMusics(List<Long> ids);
}
