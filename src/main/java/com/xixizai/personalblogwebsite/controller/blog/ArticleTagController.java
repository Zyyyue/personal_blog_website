package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleTagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//博客端的文章标签tag
@RestController("blogArticleTagController")
@RequestMapping("/blog/article/tag")
public class ArticleTagController {

    @Resource
    private ArticleTagService articleTagService;

    /**
     * 获取所有文章标签
     * @return
     * @throws PassedParameterException
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getArticleTags() throws PassedParameterException, GetOptsException {
        return articleTagService.getAllArticleTags();
    }



}
