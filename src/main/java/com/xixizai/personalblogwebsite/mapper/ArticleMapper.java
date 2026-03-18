package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleMapper {
    /**
     * 根据id获取文章详情
     * @param id
     * @return
     */
    @Select("select * from articles where id=#{id}")
    ArticleDTO findArticleById(Long id);

    @Insert("insert into articles () values (#{articleDTO})")
    void createNewArticle(ArticleDTO articleDTO);
}
