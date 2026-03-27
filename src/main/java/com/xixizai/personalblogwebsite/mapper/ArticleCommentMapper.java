package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleCommentMapper {

    //根据列表id获取评论
    @Select("select * from article_comments where id=#{articleId}")
    List<ArticleComments> getArticleCommentById(Long id);

    //根据id获取评论
    @Select("select * from article_comments where id=#{id}")
    ArticleComments findArticleCommentById(Long id);

    //批量审核评论
    void batchDeleteArticleComment(List<Long> ids);
}
