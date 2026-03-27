package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleTagDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface ArticleTagService {
    //获取所有标签
    Result getAllArticleTags() throws PassedParameterException, GetOptsException;

    //添加标签
    Result addArticleTag(ArticleTagDTO articleTagDTO) throws PassedParameterException, AddOperationException;
}
