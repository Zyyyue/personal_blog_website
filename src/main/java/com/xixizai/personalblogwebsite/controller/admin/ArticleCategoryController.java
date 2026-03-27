package com.xixizai.personalblogwebsite.controller.admin;

import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("adminArticleCategoryController")
@RequestMapping("/admin/articleCategory")
public class ArticleCategoryController {

    @Resource
    private ArticleCategoryService articleCategoryService;

    /**
     * 获取所有分类
     * @return
     */
    @GetMapping()
    public Result getArticleCategories(){
        return articleCategoryService.getAllArticleCategories();
    }

}
