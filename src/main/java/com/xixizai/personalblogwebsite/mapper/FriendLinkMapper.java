package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.FriendLinkDTO;
import com.xixizai.personalblogwebsite.pojo.entity.FriendLinks;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FriendLinkMapper {

    //获取所有友链
    @Select("select * from friend_links")
    List<FriendLinks> getAllFriendLinks();

    //添加友链
    @Insert("insert into friend_links (name,url,description,is_visible,create_time,update_time)values (#{name},#{url},#{description},#{isVisible},now(),now())")
    void addFriendLink(FriendLinkDTO friendLinkDTO);
}
