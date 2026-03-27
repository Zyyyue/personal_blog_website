package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface RssSubscriptionService {

    //获取所有激活的订阅
    Result getAllRssSubscription() throws GetOptsException;
}
