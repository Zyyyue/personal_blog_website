package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController("blogArticleCommentController")
@RequestMapping("/blog/articleComment")
public class ArticleCommentController {

    @Resource
    private ArticleCommentService articleCommentService;

    /**
     * 获取评论列表
     * @param articleId
     * @return
     * @throws PassedParameterException
     * @throws ArticleNotFoundException
     * @throws GetOptsException
     */
    @GetMapping("/article/{articleId}")
    public Result getComments(@PathVariable Long articleId) throws PassedParameterException, ArticleNotFoundException, GetOptsException {
        return articleCommentService.getArticleCommentById(articleId);
    }

}
