package com.xixizai.personalblogwebsite.mapper;

import com.github.pagehelper.Page;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Articles;
import com.xixizai.personalblogwebsite.pojo.vo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    //获取文章具体信息通过slug
    BlogArticleDetailVO getArticleBySlug(String slug);

    //获取文章标签名称通过id
    List<String> getTagNameListByArticleId(Long id);

    //获取下一页导航通过id
    BlogArticleVO getPrevArticle(Long id);

    //获取上一页导航通过id
    BlogArticleVO getNextArticle(Long id);

    //获取相关文章
    List<BlogArticleVO> getRelatedArticles(@Param("articleId") Long articleId, @Param("categoryId") Long categoryId);

    //获取文章归档中的已经发布的文章
    List<ArticleArchiveItemVO> getArticleArchiveItemVOList();

    //分页查询文章
    List<ArticleVO> pageQuery(String title, Long categoryId, Integer isPublished);

    //分页查询文章
    List<ArticleVO> pageQuerySearch(String keyword);

    //分页查询发布文章
    Page<ArticleVO> pageQueryArticle();
}
