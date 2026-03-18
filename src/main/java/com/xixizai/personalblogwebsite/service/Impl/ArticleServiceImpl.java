package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.mapper.ArticleMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Repeat;

import javax.annotation.Resource;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 根据id获取文章详情
     * @param id
     * @return
     */
    @Override
    public Result<ArticleDTO> getAdminArticle(Long id) throws ArticleNotFoundException {
        ArticleDTO articleDTO=articleMapper.findArticleById(id);
        //这里判空一下
        if(BeanUtil.isEmpty(articleDTO)){
            throw new ArticleNotFoundException(MessageConstant.ARTICLE_NOT_FOUND_EXCEPTION);
        }

        return Result.success(articleDTO);
    }
}
