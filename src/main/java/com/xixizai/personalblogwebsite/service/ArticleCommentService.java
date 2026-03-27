package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.BatchApproveArticleCommentException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteArticleCommentException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleCommentService {

    //根据文章id查询评论
    Result getArticleCommentById(Long id) throws PassedParameterException, ArticleNotFoundException, GetOptsException;

    //批量审核通过评论
    Result batchApproveArticleComment(List<Long> ids) throws PassedParameterException, BatchApproveArticleCommentException;

    //批量删除评论
    Result batchDeleteArticleComment(List<Long> ids) throws BatchDeleteArticleCommentException;
}
