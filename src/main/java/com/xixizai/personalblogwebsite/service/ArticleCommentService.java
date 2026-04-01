package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentEditDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentReplyDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface ArticleCommentService {

    //根据文章id查询评论
    Result getArticleCommentById(Long id) throws PassedParameterException, ArticleNotFoundException, GetOptsException;

    //批量审核通过评论
    Result batchApproveArticleComment(List<Long> ids) throws PassedParameterException, BatchApproveArticleCommentException;

    //批量删除评论
    Result batchDeleteArticleComment(List<Long> ids) throws BatchDeleteArticleCommentException;

    //管理员回复评论
    Result adminReplyComment(ArticleCommentReplyDTO articleCommentReplyDTO, HttpServletRequest request) throws AdminReplyCommentException;

    //用户提交评论
    Result submitComment(ArticleCommentDTO articleCommentDTO,HttpServletRequest request) throws AddOperationException;

    //访客编辑评论
    Result editComment(ArticleCommentEditDTO articleCommentEditDTO) throws UpdateOperationsException;

    //访客删除评论
    Result deleteComment(Long id, Long visitorId) throws Exception;
}
