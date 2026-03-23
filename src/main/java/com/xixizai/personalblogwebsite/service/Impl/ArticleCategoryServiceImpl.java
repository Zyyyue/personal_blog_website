package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.ArticleCategoryMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCategoryDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleCategories;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /**
     * 更新文章分类,这里传了id进去
     * @param articleCategoryDTO
     * @return
     */
    @Override
    public Result updateArticleCategories(ArticleCategoryDTO articleCategoryDTO) throws PassedParameterException, UpdateArticleCategoriesException {

        try{
            //判空一下
            if(articleCategoryDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //这里判断一下id是否为空是否合法
            if(articleCategoryDTO.getId()==null){
                throw new PassedParameterException(MessageConstant.ID_NOT_FOUND);
            }
            if(articleCategoryDTO.getId()<=0){
                throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
            }
            articleCategoryMapper.updateArticleCategories(articleCategoryDTO);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateArticleCategoriesException(MessageConstant.UPDATE_ARTICLES_CATEGORIES_FAILSURE);
        }
    }

    /**
     * 批量删除文章分类
     * @param ids
     * @return
     */
    @Override
    public Result batchDeleteArticleCategories(List<Long> ids) throws PassedParameterException, BatchDeleteArticleCategoriesException {

        try{
            //判空一下
            if(ids==null||ids.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //去重一下id
            List<Long>distinctIds=new ArrayList<>();
            for (Long id : ids) {
                if(!distinctIds.contains(id)){
                    distinctIds.add(id);
                }
            }

            //再看一下数据库中是否有对应id

            //数据库中存在id的集合是updatedIds
            List<Long>updatedIds=new ArrayList<>();
            //数据库中不存在id的集合是nulledIds
            List<Long>nulledIds=new ArrayList<>();
            //再判断一下ids中的id是否都在数据库中存在,如果不存在的话就需要提示一下，然后删除已经存在的
            for (Long id : distinctIds) {
                if(articleCategoryMapper.findArticleCategoryById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除文章分类
            if(!updatedIds.isEmpty()){
                articleCategoryMapper.batchDeleteArticleCategories(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 个文章分类"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 个，"
                    + "不存在的ID：" + nulledIds);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteArticleCategoriesException(MessageConstant.BATCH_DELETE_ARTICLE_CATEGORIES_FAILSURE);
        }
    }


}
