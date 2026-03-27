package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.FriendLinkDTO;
import com.xixizai.personalblogwebsite.pojo.entity.FriendLinks;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FriendLinkMapper {

    //获取所有友链
    @Select("select * from friend_links")
    List<FriendLinks> getAllFriendLinks();

    //添加友链
    @Insert("insert into friend_links (name,url,description,is_visible,create_time,update_time)values (#{name},#{url},#{description},#{isVisible},now(),now())")
    void addFriendLink(FriendLinkDTO friendLinkDTO);

    //根据id查找友链信息
    @Select("select * from friend_links where id=#{id}")
    FriendLinks findById(Long id);

    //根据id修改友链信息
    @Update("update friend_links set   name=#{name},url=#{url},avatar_url=#{avatarUrl},description=#{description},sort=#{sort},is_visible=#{isVisible},update_time=now() where id=#{id}")
    void updateFriendLink(FriendLinkDTO friendLinkDTO);

    //批量删除友链
    void batchDeleteFriendLinks(List<Long> ids);
}
