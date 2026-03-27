package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    //根据id获取文章详情
    Result<ArticleDTO> getAdminArticle(Long id) throws ArticleNotFoundException;

    //创建文章
    Result createNewArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, CreateNewArticleException;

    //更新文章
    Result updateArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, UpdateArticlesException;

    //批量删除文章
    Result batchDeleteArticles(List<Long> ids) throws IdNotValidException, BatchDeleteArticlesException;

    //发布文章
    Result publishArticle(Long id, Integer isPublished) throws PublishArticleException;

    //取消发布
    Result unpublishArticle(Long id,Integer isPublished) throws UnpublishArticleException;
}
