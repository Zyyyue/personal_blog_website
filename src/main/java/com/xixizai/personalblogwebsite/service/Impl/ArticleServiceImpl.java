package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.ArticleMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Repeat;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 更新文章
     * @param articleDTO
     * @return
     */
    @Override
    public Result updateArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, UpdateArticlesException {
        try{
            //这里判空一下
            if(BeanUtil.isEmpty(articleDTO)){
                throw new ArticleDTONotFoundException(MessageConstant.ARTICLEDTO_NOT_FOUND);
            }
            //再单独判断一下id是否存在
            if(articleDTO.getId()==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }
            articleMapper.updateArticle(articleDTO);
            return Result.success("更新成功");
        }catch (Exception exception){
            throw new UpdateArticlesException(MessageConstant.UPDATE_ARTICLES_FAILSURE);
        }

    }

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public Result batchDeleteArticles(List<Long> ids) throws IdNotValidException, BatchDeleteArticlesException {

        try{
            //判空一下ids
            if(ids==null|| ids.isEmpty()){
                throw new IdNotValidException(MessageConstant.ID_LIST_NOT_EXIST);
            }

            //去除一下重复id
            List<Long>distinctIds=new ArrayList<>();
            for (Long id : ids) {
                if(!distinctIds.contains(id)){
                    distinctIds.add(id);
                }
            }

            //数据库中存在id的集合是updatedIds
            List<Long>updatedIds=new ArrayList<>();
            //数据库中不存在id的集合是nulledIds
            List<Long>nulledIds=new ArrayList<>();
            //再判断一下ids中的id是否都在数据库中存在,如果不存在的话就需要提示一下，然后删除已经存在的
            for (Long id : distinctIds) {
                if(articleMapper.findArticleById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除文章
            if(!updatedIds.isEmpty()){
                articleMapper.batchDeleteArticles(updatedIds);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 篇文章"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 篇，"
                    + "不存在的ID：" + nulledIds);
        }catch (Exception exception){
            throw new BatchDeleteArticlesException(MessageConstant.BATCH_DELETE_ARTICLES_FAILSURE);

        }

    }

    /**
     * 发布文章
     * @param id
     * @param isPublished
     * @return
     */
    @Override
    public Result publishArticle(Long id, Integer isPublished) throws PublishArticleException {

        try{
            //判断一下id和isPublished
            if(id==null||id<=0){
                throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
            }

            if(isPublished==null||isPublished!=1){
                throw new IsPublishedNotValidException(MessageConstant.ISPUBLISHED_NOT_VALID_EXCEPTION);
            }

            articleMapper.publishArticle(id,isPublished);

            return Result.success();
        }catch (Exception exception){
            throw new PublishArticleException(MessageConstant.PUBLISH_ARTICLE_FAILSURE);
        }
    }

    /**
     * 取消发布文章
     * @param id
     * @param isPublished
     * @return
     */
    @Override
    public Result unpublishArticle(Long id, Integer isPublished) throws UnpublishArticleException {

        try{
            //判断一下id和isPublished
            if(id==null||id<=0){
                throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
            }

            if(isPublished==null||isPublished!=0){
                throw new IsPublishedNotValidException(MessageConstant.ISPUBLISHED_NOT_VALID_EXCEPTION);
            }

            articleMapper.unpublishArticle(id,isPublished);

            return Result.success();
        }catch (Exception exception){
            throw new UnpublishArticleException(MessageConstant.UNPUBLISH_ARTICLE_FAILSURE);
        }
    }


}
