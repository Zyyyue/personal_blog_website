package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface ArticleLikeService {


    //点赞文章
    Result likeArticle(Long articleId,Long visitorId) throws AddOperationException;


    //取消点赞文章
    Result cancleArticleLike(Long articleId, Long visitorId) throws UpdateOperationsException;
}
