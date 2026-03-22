package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.ArticleCategories;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleCategoryMapper {
    //获取所有文章分类
    @Select("select * from article_categories")
    List<ArticleCategories> getAllArticleCategories();

}
