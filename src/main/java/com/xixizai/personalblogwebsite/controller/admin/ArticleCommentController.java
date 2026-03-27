package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.BatchApproveArticleCommentException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteArticleCommentException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/article/comment")
public class ArticleCommentController {

    @Resource
    private ArticleCommentService articleCommentService;

    /**
     * 根据文章id查询评论
     * @param id
     * @return
     * @throws PassedParameterException
     * @throws ArticleNotFoundException
     * @throws GetOptsException
     */
    @GetMapping("/{id}")
    public Result getArticleCommentById(@PathVariable Long id) throws PassedParameterException, ArticleNotFoundException, GetOptsException {
        return articleCommentService.getArticleCommentById(id);
    }

    /**
     * 批量审核通过评论
     * @param ids
     * @return
     * @throws PassedParameterException
     */
    @PutMapping("/approve")
    public Result batchApproveArticleComment(@RequestParam List<Long>ids) throws PassedParameterException, BatchApproveArticleCommentException {
        return articleCommentService.batchApproveArticleComment(ids);
    }

    /**
     * 批量删除文章评论
     * @param ids
     * @return
     * @throws BatchDeleteArticleCommentException
     */
    @DeleteMapping()
    public Result batchDeleteArticleComment(@RequestParam List<Long>ids) throws BatchDeleteArticleCommentException {
        return articleCommentService.batchDeleteArticleComment(ids);
    }
}
