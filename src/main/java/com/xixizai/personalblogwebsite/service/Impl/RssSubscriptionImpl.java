package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.RssSubscriptionMapper;
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


}
