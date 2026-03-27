package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleTagRelationsMapper {
    /**
     * 批量删除文章标签关系表
     * @param ids
     */
    void batchDeleteArticleTagsAndRelations(@Param("ids") List<Long> ids);

    /**
     * 修改文章标签关系表
     * @param tagIds
     */
    void updateArticleTagsAndRelations(@Param("tagIds") List<Long> tagIds, @Param("id") Long id);

    /**
     * 创建文章标签关系表中的数据
     * @param tagIds
     * @param articleId
     */
    void createArticleTagsAndRelations(@Param("tagIds1") List<Long> tagIds, @Param("id1") Long articleId);

    /**
     * 根据slug查找，因为slug是用unique约束的
     * @param slug
     * @return
     */
    @Select("select * from articles where slug=#{slug}")
    ArticleDTO findBySlug(String slug);

}
