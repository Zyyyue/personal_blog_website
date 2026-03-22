package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface ArticleCategoryService {

    //获取所有文章分类
    Result getAllArticleCategories();
}
