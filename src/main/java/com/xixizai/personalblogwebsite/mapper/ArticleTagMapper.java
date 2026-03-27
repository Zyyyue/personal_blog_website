package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleTagDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleTags;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleTagMapper {
    //获取所有标签
    @Select("select * from article_tags;")
    List<ArticleTags> getAllArticleTags();

    //添加标签
    @Insert("insert into article_tags (id,name,slug,create_time)values(#{id},#{name},#{slug},now())")
    void addArticleTag(ArticleTagDTO articleTagDTO);

    //更新标签
    @Update("update article_tags set name=#{name},slug=#{slug},update_time=now() where id=#{id}")
    void updateArticleTag(ArticleTagDTO articleTagDTO);
}
