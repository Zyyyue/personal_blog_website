package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.RssLinkDTO;
import com.xixizai.personalblogwebsite.pojo.entity.RssLinks;
import com.xixizai.personalblogwebsite.pojo.vo.RssLinkVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RssLinkMapper {

    //获取所有 RSS 链接
    @Select("select * from rss_links order by sort asc, id asc")
    List<RssLinkVO> getAllRssLinks();

    //根据 id 查找
    @Select("select * from rss_links where id=#{id}")
    RssLinks findById(Long id);

    //添加
    @Insert("insert into rss_links (name,url,is_active,sort,create_time,update_time) values (#{name},#{url},#{isActive},#{sort},now(),now())")
    void addRssLink(RssLinkDTO dto);

    //更新
    @Update("update rss_links set name=#{name},url=#{url},is_active=#{isActive},sort=#{sort},update_time=now() where id=#{id}")
    void updateRssLink(RssLinkDTO dto);

    //批量删除
    void batchDeleteRssLinks(List<Long> ids);
}
