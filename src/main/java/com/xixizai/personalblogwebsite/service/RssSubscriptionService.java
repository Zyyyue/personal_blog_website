package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface RssSubscriptionService {

    //获取所有激活的订阅
    Result getAllRssSubscription() throws GetOptsException;

    //根据id查找
    Result findById(Long id) throws GetOptsException;

    //更新订阅
    Result updateRssSubscription(RssSubscriptions rssSubscriptions) throws UpdateOperationsException;
}
