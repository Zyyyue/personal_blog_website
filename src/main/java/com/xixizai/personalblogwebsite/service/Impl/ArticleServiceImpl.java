package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.ArticleDTONotFoundException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.CreateNewArticleException;
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

    /**
     * 创建文章
     * @param articleDTO
     * @return
     */
    @Override
    public Result createNewArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, CreateNewArticleException {

        try{
            //这里判空一下
            if(BeanUtil.isEmpty(articleDTO)){
                throw new ArticleDTONotFoundException(MessageConstant.ARTICLEDTO_NOT_FOUND);
            }
            articleMapper.createNewArticle(articleDTO);
            return Result.success("创建文章成功");
        }catch (Exception exception){
            throw new CreateNewArticleException(MessageConstant.CREATE_NEW_ARTICLE_FAILSURE);
        }

    }
}
