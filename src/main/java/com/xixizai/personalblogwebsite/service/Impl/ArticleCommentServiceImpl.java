package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.ArticleCommentMapper;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public Result getArticleCommentById(Long id) throws PassedParameterException, ArticleNotFoundException, GetOptsException {

        try{
            if(id==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            List<ArticleComments> articleCommentById = articleCommentMapper.getArticleCommentById(id);

            if(articleCommentById==null||articleCommentById.isEmpty()){
                return Result.error("查找失败");
            }else{
                return Result.success(articleCommentById);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }
}
