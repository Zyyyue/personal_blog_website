package com.xixizai.personalblogwebsite.controller.blog;

import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/blog/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/detail/{slug}")
    public Result getArticleBySlug(@PathVariable String slug){
        return articleService.getArticleBySlug(slug);
    }

}
