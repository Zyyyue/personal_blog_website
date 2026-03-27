package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
