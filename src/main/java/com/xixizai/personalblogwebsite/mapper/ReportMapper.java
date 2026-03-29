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

    //获取总浏览量
    @Select("select count(page_title) from views")
    Integer getTotalViewCount();

    //获取总访客数
    @Select("select count(id) from visitors")
    Integer getTotalVisitorCount();

    //获取今日浏览量
    @Select("select count(page_title) from views where DATE(view_time) = CURDATE()")
    Integer getTodayViewCount();

    //获取今日新增访客数
    @Select("select count(id) from visitors where DATE (create_time)=CURDATE()")
    Integer getTodayNewVisitorCount();

    //获取总文章数
    @Select("select count(id) from articles where is_published=1")
    Integer getTotalArticleCount();

    //获取总评论数
    @Select("select count(id) from article_comments")
    Integer getTotalCommentCount();

    //获取总留言数
    @Select("select count(id) from messages where is_approved=1")
    Integer getTotalMessageCount();

    //获取待审核评论数
    @Select("select count(id) from article_comments where is_approved=0")
    Integer getPendingCommentCount();

    //获取审核留言数
    @Select("select count(id) from messages where is_approved=0")
    Integer getPendingMessageCount();
}
