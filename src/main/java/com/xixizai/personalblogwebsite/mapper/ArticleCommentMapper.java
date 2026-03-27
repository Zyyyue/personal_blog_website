package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentReplyDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import org.apache.ibatis.annotations.Insert;
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
    void batchApproveArticleComment(List<Long> ids);

    //批量删除评论
    void batchDeleteArticleComment(List<Long> ids);

    //管理员回复评论
    @Insert("insert into article_comments (article_id,parent_id,root_id,parent_nickname,content,is_markdown,is_approved,is_edited,is_admin_reply,content_html,location,create_time)values(#{articleId},#{parentId},#{rootId},#{parentNickname},#{content},#{isMarkdown},#{isApproved},#{isEdited},#{isAdminReply},#{contentHtml},#{location},now())")
    void adminReplyComment(ArticleComments articleComment);
}
