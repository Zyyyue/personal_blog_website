package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.AddFailsureException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.ArticleCategoryMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCategoryDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleCategories;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {


    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    /**
     * 获取所有文章分类
     * @return
     */
    @Override
    public Result getAllArticleCategories() {
        List<ArticleCategories>list= articleCategoryMapper.getAllArticleCategories();
        //这里判空一下
        if(list==null||list.isEmpty()){
            return Result.error("文章分类表中没有文章分类，获取失败");
        }else{
            return Result.success(list);
        }
    }

    /**
     * 添加文章分类
     * @param articleCategoryDTO
     * @return
     */
    @Override
    public Result addArticleCategories(ArticleCategoryDTO articleCategoryDTO) throws AddFailsureException {

        try{
            //判空了一下
            if(articleCategoryDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }
            articleCategoryMapper.addArticleCategories(articleCategoryDTO);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddFailsureException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }


}
