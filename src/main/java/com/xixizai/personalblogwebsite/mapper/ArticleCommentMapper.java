package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleCommentMapper {

    //根据列表id获取评论
    @Select("select * from article_comments where article_id=#{id}")
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

    //提交评论
    @Insert("insert into article_comments (article_id, root_id, parent_id, parent_nickname, content,  visitor_id, nickname, email_or_qq,  is_markdown, is_secret,is_notice, create_time, update_time,location,user_agent_browser,user_agent_os,is_approved,content_html) values (#{articleId},#{rootId},#{parentId},#{parentNickname},#{content},#{visitorId},#{nickname},#{emailOrQq},#{isMarkdown},#{isSecret},#{isNotice},now(),now(),#{location},#{userAgentBrowser},#{userAgentOs},0,#{contentHtml})")
    void submitComment(ArticleComments articleComments);
}
