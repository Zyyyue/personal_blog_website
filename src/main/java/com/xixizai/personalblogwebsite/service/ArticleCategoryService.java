package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.AddFailsureException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCategoryDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface ArticleCategoryService {

    //获取所有文章分类
    Result getAllArticleCategories();

    //添加文章分类
    Result addArticleCategories(ArticleCategoryDTO articleCategoryDTO) throws AddFailsureException;
}
