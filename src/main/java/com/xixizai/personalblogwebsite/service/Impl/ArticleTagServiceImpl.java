package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.ArticleTagMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleTagDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleTags;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleTagService;
import org.redisson.transaction.operation.set.AddOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /**
     * 添加标签
     * @param articleTagDTO
     * @return
     */
    @Override
    public Result addArticleTag(ArticleTagDTO articleTagDTO) throws PassedParameterException, AddOperationException {
        try{
            //判断是否为空
            if(articleTagDTO==null||articleTagDTO.getId()==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }
            articleTagMapper.addArticleTag(articleTagDTO);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }

    /**
     * 更新标签
     * @param articleTagDTO
     * @return
     */
    @Override
    public Result updateArticleTag(ArticleTagDTO articleTagDTO) throws PassedParameterException, UpdateOperationsException {
        try{
            //判断是否为空
            if(articleTagDTO==null||articleTagDTO.getId()==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }
            articleTagMapper.updateArticleTag(articleTagDTO);
            return Result.success("更新标签操作成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 批量删除标签
     * @param ids
     * @return
     */
    @Override
    public Result batchDeleteArticleTag(List<Long> ids) throws PassedParameterException, BatchDeleteArticleTagException {

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
                if(articleTagMapper.findArticleTagById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除文章分类
            if(!updatedIds.isEmpty()){
                articleTagMapper.batchDeleteArticleTags(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 个文章标签"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 个，"
                    + "不存在的ID：" + nulledIds);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteArticleTagException(MessageConstant.BATCH_DELETE_ARTICLE_TAGS_FAILSURE);
        }
    }
}
