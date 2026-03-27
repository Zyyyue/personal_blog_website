package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
