package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteRssSubscriptionException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RssSubscriptionService {

    //获取所有激活的订阅
    Result getAllRssSubscription() throws GetOptsException;

    //根据id查找
    Result findById(Long id) throws GetOptsException;

    //更新订阅
    Result updateRssSubscription(RssSubscriptions rssSubscriptions) throws UpdateOperationsException;

    //批量删除订阅
    Result batchDeleteRssSubscription(List<Long> ids) throws BatchDeleteRssSubscriptionException;

    //添加订阅
    Result addRssSubscription(RssSubscriptionDTO rssSubscriptionDTO) throws AddOperationException;

    //取消订阅
    Result unSubscribe(String email) throws UpdateOperationsException;

    //检查订阅状态
    Result checkWeatherSubscribe(Long visitorId) throws GetOptsException;

    //分页查询订阅列表
    Result pageQueryRssSubscription(Integer page, Integer pageSize) throws GetOptsException;
}
