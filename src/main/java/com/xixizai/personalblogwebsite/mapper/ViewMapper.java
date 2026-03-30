package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Views;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.swing.text.View;
import java.util.List;

@Mapper
public interface ViewMapper {

    //根据id获取浏览记录
    @Select("select * from views where id=#{id}")
    Views findById(Long id);

    //批量删除浏览记录
    void batchDeleteViewRecords(List<Long> ids);

    //添加浏览记录
    @Insert("insert into views (visitor_id, page_path, referer, page_title, ip_address, user_agent, view_time)values (#{visitorId},#{pagePath},#{referer},#{pageTitle},#{ipAddress},#{userAgent},now())")
    void addViewRecord(Views views);
}
