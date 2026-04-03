package com.xixizai.personalblogwebsite.mapper;

import com.github.pagehelper.Page;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import com.xixizai.personalblogwebsite.pojo.vo.ArticleCommentVO;
import org.apache.ibatis.annotations.*;

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

    //访客编辑评论
    void updateContent(ArticleComments updateComment);

    //统计待审核评论数
    @Select("select count(*) from article_comments where root_id=#{id}")
    Integer countByRootId(Long id);

    //根据根评论ID删除所有子评论
    @Delete("delete from article_comments where root_id=#{id}")
    void deleteByRootId(Long id);

    //评论数-1(最小为0)
    @Update("update articles set comment_count = case when comment_count > 0 then comment_count - 1 else 0 end where id = #{articleId}")
    void decrementCommentCount(Long articleId);

    //删除单挑评论
    @Delete("delete from article_comments where id = #{id}")
    void deleteById(Long id);

    //查询根评论（带子评论）
    List<ArticleCommentVO> getRootComments(Long articleId);

    //根据根评论 ID 查询子评论
    List<ArticleCommentVO> getChildrenByRootId(Long rootId);

    //分页查询评论（支持文章 ID 和审核状态筛选，返回根评论带子评论）
    Page<ArticleCommentVO> pageQueryComments(Long articleId, Integer isApproved);
}
