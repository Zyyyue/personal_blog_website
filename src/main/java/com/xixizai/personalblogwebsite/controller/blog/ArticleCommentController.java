package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    /**
     * 提交评论
     * @param articleCommentDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result submitComment(@RequestBody ArticleCommentDTO articleCommentDTO, HttpServletRequest request) throws AddOperationException {
        return articleCommentService.submitComment(articleCommentDTO,request);
    }
}
