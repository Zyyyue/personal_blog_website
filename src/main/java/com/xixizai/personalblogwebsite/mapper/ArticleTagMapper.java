package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.ArticleTags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleTagMapper {
    //获取所有标签
    @Select("select * from article_tags;")
    List<ArticleTags> getAllArticleTags();
}
