package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.FriendLinks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FriendLinkMapper {

    //获取所有友链
    @Select("select * from friend_links")
    List<FriendLinks> getAllFriendLinks();
}
