package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.ArticleDTONotFoundException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.CreateNewArticleException;
import com.xixizai.personalblogwebsite.exception.UpdateArticlesException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {
    //根据id获取文章详情
    Result<ArticleDTO> getAdminArticle(Long id) throws ArticleNotFoundException;

    //创建文章
    Result createNewArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, CreateNewArticleException;

    //更新文章
    Result updateArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, UpdateArticlesException;
}
