package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {
    //根据id获取文章详情
    Result<ArticleDTO> getAdminArticle(Long id) throws ArticleNotFoundException;
}
