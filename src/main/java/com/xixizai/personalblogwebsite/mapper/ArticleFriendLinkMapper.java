package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.vo.FriendLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleFriendLinkMapper {

    //获取可见友链
    @Select("select * from friend_links where is_visible=1")
    List<FriendLinkVO> getFrindLinkList();


}
