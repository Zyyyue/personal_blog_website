package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.mapper.ArticleCategoryMapper;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleCategories;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {


    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    /**
     * 获取所有文章分类
     * @return
     */
    @Override
    public Result getAllArticleCategories() {
        List<ArticleCategories>list= articleCategoryMapper.getAllArticleCategories();
        //这里判空一下
        if(list==null||list.isEmpty()){
            return Result.error("文章分类表中没有文章分类，获取失败");
        }else{
            return Result.success(list);
        }
    }


}
