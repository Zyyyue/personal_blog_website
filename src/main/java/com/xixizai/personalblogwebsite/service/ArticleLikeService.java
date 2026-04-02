package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface ArticleLikeService {


    //点赞文章
    Result likeArticle(Long articleId,Long visitorId) throws AddOperationException;


}
