package com.xixizai.personalblogwebsite.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeMapper {

    //点赞文章
    @Insert("insert into article_likes (article_id, visitor_id, like_time) values (#{articleId},#{visitorId},now())")
    void likeArticle(Long articleId, Long visitorId);

}
