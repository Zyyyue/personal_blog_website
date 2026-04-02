package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.IdNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.mapper.ArticleLikeMapper;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleLikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {

    @Resource
    private ArticleLikeMapper articleLikeMapper;

    /**
     * 点赞文章
     * @param articleId
     * @param visitorId
     * @return
     * @throws AddOperationException
     */
    @Override
    public Result likeArticle(Long articleId,Long visitorId) throws AddOperationException {
        try{

            if(articleId==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            if(articleId<=0){
                throw new PassedParameterException(MessageConstant.ID_NOT_VALID);
            }

            if(visitorId==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            if(visitorId<=0){
                throw new PassedParameterException(MessageConstant.ID_NOT_VALID);
            }

            articleLikeMapper.likeArticle(articleId,visitorId);
            return Result.success("点赞成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }

    }

    /**
     * 取消点赞
     * @param articleId
     * @param visitorId
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result cancleArticleLike(Long articleId, Long visitorId) throws UpdateOperationsException {
        try{

            if(articleId==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            if(articleId<=0){
                throw new PassedParameterException(MessageConstant.ID_NOT_VALID);
            }

            if(visitorId==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            if(visitorId<=0){
                throw new PassedParameterException(MessageConstant.ID_NOT_VALID);
            }
            articleLikeMapper.cancleArticleLike(articleId,visitorId);
            return Result.success("取消成功");
        }catch (Exception exception){

            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);

        }

    }
}
