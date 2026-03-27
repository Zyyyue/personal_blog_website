package com.xixizai.personalblogwebsite.controller.admin;

import com.xixizai.personalblogwebsite.exception.AddFailsureException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCategoryDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCategoryService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加文章分类
     * @param articleCategoryDTO
     * @return
     * @throws AddFailsureException
     */
    @PostMapping()
    public Result addArticleCategories(@RequestBody ArticleCategoryDTO articleCategoryDTO) throws AddFailsureException {
        return articleCategoryService.addArticleCategories(articleCategoryDTO);
    }
}
