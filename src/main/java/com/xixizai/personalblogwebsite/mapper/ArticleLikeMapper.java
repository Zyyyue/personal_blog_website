package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.ArticleLikes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleLikeMapper {

    //点赞文章
    @Insert("insert into article_likes (article_id, visitor_id, like_time) values (#{articleId},#{visitorId},now())")
    void likeArticle(Long articleId, Long visitorId);

    //取消点赞文章
    @Delete("delete from article_likes where visitor_id=#{visitorId} and article_id=#{articleId}")
    void cancleArticleLike(Long articleId, Long visitorId);

    @Select("select * from article_likes where  visitor_id=#{visitorId} and article_id=#{articleId}")
    ArticleLikes getArticleLike(Long articleId, Long visitorId);
}
