package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteRssSubscriptionException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.RssLinkDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;

import java.util.List;

public interface RssLinkService {

    //获取所有 RSS 链接
    Result getAllRssLinks() throws GetOptsException;

    //添加
    Result addRssLink(RssLinkDTO dto) throws AddOperationException;

    //更新
    Result updateRssLink(RssLinkDTO dto) throws UpdateOperationsException;

    //批量删除
    Result batchDeleteRssLinks(List<Long> ids) throws BatchDeleteRssSubscriptionException;
}
