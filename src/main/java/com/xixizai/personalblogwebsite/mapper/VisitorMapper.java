package com.xixizai.personalblogwebsite.mapper;

import com.github.pagehelper.Page;
import com.xixizai.personalblogwebsite.pojo.entity.Visitors;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VisitorMapper {
    
    //根据id查找
    @Select("select * from visitors where id=#{id}")
    Visitors findById(Long id);

    //批量封禁游客
    void batchBlockVisitors(List<Long> ids);

    //批量解封游客
    void batchUnblockVisitors(List<Long> ids);

    //添加游客
    @Insert("insert into visitors (fingerprint, session_id, ip, user_agent, country, province, city, longitude, latitude, first_visit_time,  total_views, is_blocked,  create_time, update_time) values (#{fingerprint},#{sessionId},#{ip},#{userAgent},#{country},#{province},#{city},#{longitude},#{latitude},now(),1,0,now(),now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addVisitors(Visitors visitors);


    //根据指纹查找访客
    @Select("select * from visitors where fingerprint=#{fingerprint}")
    Visitors findByFingerprint(String fingerprint);

    //更新访客
    @Update("update visitors set update_time=now(),total_views=#{totalViews},last_visit_time=now() where fingerprint=#{fingerprint} ")
    void updateVisitor(Visitors existingVisitor);

    //分页查询访客
    @Select("select * from visitors order by create_time desc")
    Page<Visitors> pageQueryVisitor();
}
