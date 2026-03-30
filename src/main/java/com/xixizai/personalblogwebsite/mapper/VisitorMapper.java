package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Visitors;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.javassist.compiler.ast.Visitor;

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
    @Insert("insert into visitors (fingerprint, session_id, ip, user_agent, country, province, city, longitude, latitude, first_visit_time,  total_views, is_blocked,  create_time, update_time) values (#{fingerprint},#{sessionId},#{ip},#{userAgent},#{country},#{province},#{city},#{longtitude},#{latitude},now(),1,0,now(),now())")
    void addVisitors(Visitors visitors);

    //根据Request获取id
    @Select("select id from visitors where ip=#{clientIp} and session_id=#{sessionId}")
    Long getVisitorIdByRequest(String sessionId, String clientIp);
}
