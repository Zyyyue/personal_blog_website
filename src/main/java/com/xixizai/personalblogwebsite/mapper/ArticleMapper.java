package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    /**
     * 根据id获取文章详情
     * @param id
     * @return
     */
    @Select("select * from articles where id=#{id}")
    ArticleDTO findArticleById(Long id);

    /**
     * 创建新文章
     * @param articleDTO
     */
    @Insert("INSERT INTO articles (title, slug, summary, cover_image, content_markdown, content_html, category_id, is_published, is_top, create_time) " +
            "VALUES (#{title}, #{slug}, #{summary}, #{coverImage}, #{contentMarkdown}, #{contentHtml}, #{categoryId}, #{isPublished}, #{isTop}, NOW())")
    void createNewArticle(ArticleDTO articleDTO);

    /**
     * 更新文章
     * @param articleDTO
     */

    void updateArticle(ArticleDTO articleDTO);

    /**
     * 批量删除文章表中的文章
     * @param ids
     */
    void batchDeleteArticles( List<Long> ids);


    /**
     * 发布文章
     * @param id
     * @param isPublished
     */
    void publishArticle(Long id, Integer isPublished);

    /**
     * 取消发布文章
     * @param id
     * @param isPublished
     */
    void unpublishArticle(Long id, Integer isPublished);

    /**
     * 置顶或者取消置顶
     * @param id
     * @param isTop
     */
    void cancleOrNotTopArticle(Long id, Integer isTop);
}
