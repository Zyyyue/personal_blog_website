package com.xixizai.personalblogwebsite.controller.blog;

import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("blogArticleCategoryController")
@RequestMapping("/blog/articleCategory")
public class AticleCategoryController {

    @Resource
    private ArticleCategoryService articleCategoryService;

    /**
     * 获取所有文章分类
     * @return
     */
    @GetMapping()
    public Result getAticleCategort(){
        return articleCategoryService.getAllArticleCategories();
    }

}
