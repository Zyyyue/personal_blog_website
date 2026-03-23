package com.xixizai.personalblogwebsite.controller.admin;

import com.xixizai.personalblogwebsite.exception.AddFailsureException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteArticleCategoriesException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateArticleCategoriesException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCategoryDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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


    /**
     * 更新文章分类
     * @param articleCategoryDTO
     * @return
     */
    @PutMapping()
    public Result updateArticleCategories(@RequestBody ArticleCategoryDTO articleCategoryDTO) throws PassedParameterException, UpdateArticleCategoriesException {
        return articleCategoryService.updateArticleCategories(articleCategoryDTO);
    }

    /**
     * 批量删除文章分类
     * @param ids
     * @return
     * @throws PassedParameterException
     * @throws BatchDeleteArticleCategoriesException
     */
    @DeleteMapping()
    public Result batchDeleteArticleCategories(@RequestParam List<Long>ids) throws PassedParameterException, BatchDeleteArticleCategoriesException {
        return articleCategoryService.batchDeleteArticleCategories(ids);
    }
}
