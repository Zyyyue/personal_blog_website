package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.ArticleTagMapper;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleTags;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Resource
    private ArticleTagMapper articleTagMapper;

    /**
     * 获取所有标签
     * @return
     */
    @Override
    public Result getAllArticleTags() throws PassedParameterException, GetOptsException {
        try{
            List<ArticleTags> articleTagsList=articleTagMapper.getAllArticleTags();
            //这里判空一下
            if(articleTagsList==null||articleTagsList.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }
            return Result.success(articleTagsList);
        }catch (Exception exception){
            exception.printStackTrace();
            throw  new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }
}
