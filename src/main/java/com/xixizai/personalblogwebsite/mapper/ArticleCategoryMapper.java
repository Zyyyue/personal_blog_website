package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleCategoryDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleCategories;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleCategoryMapper {
    //获取所有文章分类
    @Select("select * from article_categories")
    List<ArticleCategories> getAllArticleCategories();

    //添加文章分类成功
    @Insert("insert into article_categories (name,slug,description,sort,create_time,update_time)values(#{name},#{slug},#{description},#{sort},now(),now())")
    void addArticleCategories(ArticleCategoryDTO articleCategoryDTO);
}
