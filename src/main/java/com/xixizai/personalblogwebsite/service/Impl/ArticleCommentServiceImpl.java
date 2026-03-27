package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.ArticleCommentMapper;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    /**
     * 根据文章id查询评论
     * @param id
     * @return
     * @throws PassedParameterException
     * @throws ArticleNotFoundException
     * @throws GetOptsException
     */
    @Override
    public Result getArticleCommentById(Long id) throws PassedParameterException, ArticleNotFoundException, GetOptsException {

        try{
            if(id==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            List<ArticleComments> articleCommentById = articleCommentMapper.getArticleCommentById(id);

            if(articleCommentById==null||articleCommentById.isEmpty()){
                return Result.error("查找失败");
            }else{
                return Result.success(articleCommentById);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    @Override
    public Result batchApproveArticleComment(List<Long> ids) throws PassedParameterException {
        //判空
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
            if(articleCommentMapper.findArticleCommentById(id)==null){
                nulledIds.add(id);
            }else{
                updatedIds.add(id);
            }
        }

        //批量审核文章评论
        if(!updatedIds.isEmpty()){
            articleCommentMapper.batchDeleteArticleComment(ids);
        }

        //返回结果
        if(updatedIds.isEmpty()){
            return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
        }
        if(nulledIds.isEmpty()){
            return Result.success(("批量审核成功，共审核 " + updatedIds.size() + " 条文章评论/"));
        }
        return Result.success("批量审核成功，成功审核 " + updatedIds.size() + " 条，"
                + "不存在的ID：" + nulledIds);
    }
}
