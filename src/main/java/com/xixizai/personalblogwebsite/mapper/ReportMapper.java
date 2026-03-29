package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleTitleViewCountDTO;
import com.xixizai.personalblogwebsite.pojo.dto.DailyViewCountDTO;
import com.xixizai.personalblogwebsite.pojo.dto.DailyVisitorCountDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ProvinceCountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReportMapper {

    //获取浏览记录
    @Select("select date(view_time)as date,count(*) as count from views where date (view_time) between #{begin} and #{end} group by date order by date")
    List<DailyViewCountDTO> getViewCountList(LocalDate begin,LocalDate end);

    //获取访客记录
    @Select("select date(create_time)as date,count(*) as count from visitors where date(create_time) between #{begin} and #{end} group by date order by date")
    List<DailyVisitorCountDTO> getVisitorCountList(LocalDate begin, LocalDate end);

    //获取省份分布统计
    List<ProvinceCountDTO> getProvinceDistribution();

    //获取文章访问top10
    List<ArticleTitleViewCountDTO> getArticleViewTop10();
}
