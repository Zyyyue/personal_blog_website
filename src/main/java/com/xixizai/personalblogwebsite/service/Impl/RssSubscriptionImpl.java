package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.IdNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.mapper.RssSubscriptionMapper;
import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssSubscriptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RssSubscriptionImpl implements RssSubscriptionService {

    @Resource
    private RssSubscriptionMapper rssSubscriptionMapper;

    /**
     * 获取所有激活的订阅
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getAllRssSubscription() throws GetOptsException {
        try{

            List<RssSubscriptions> allRssSubscription = rssSubscriptionMapper.getAllRssSubscription();
            return Result.success(allRssSubscription);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }

    /**
     * 根据id查询
     * @param id
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result findById(Long id) throws GetOptsException {
        try{

            RssSubscriptions rssSubscriptions=rssSubscriptionMapper.findById(id);
            if(rssSubscriptions==null){
                return Result.error("获取失败");
            }
            return Result.success(rssSubscriptions);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 更新订阅
     * @param rssSubscriptions
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateRssSubscription(RssSubscriptions rssSubscriptions) throws UpdateOperationsException {
        try{

            if(rssSubscriptions==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(rssSubscriptions.getId()==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            rssSubscriptionMapper.updateRssSubscription(rssSubscriptions);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }


}
