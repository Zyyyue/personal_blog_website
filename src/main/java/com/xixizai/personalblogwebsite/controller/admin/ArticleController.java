package com.xixizai.personalblogwebsite.controller.admin;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.ArticleDTONotFoundException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.CreateNewArticleException;
import com.xixizai.personalblogwebsite.exception.IdNotValidException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 管理端文章接口
 */
@Slf4j
@RestController("adminArticleController")
@RequestMapping("/admin/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 根据id获取文章详情
     * @param id
     * @return
     * @throws IdNotValidException
     */
    @GetMapping("/{id}")
    public Result<ArticleDTO>getAdminArticle(@PathVariable Long id) throws IdNotValidException, ArticleNotFoundException {
        if(id<=0){
            throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
        }
        return articleService.getAdminArticle(id);
    }

    /**
     * 创建文章
     * @param articleDTO
     * @return
     */
    @PostMapping()
    public Result createNewArticle(@RequestBody ArticleDTO articleDTO) throws ArticleDTONotFoundException, CreateNewArticleException {
        return articleService.createNewArticle(articleDTO);
    }



}
