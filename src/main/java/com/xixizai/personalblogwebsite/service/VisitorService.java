package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.BatchBlockVisitorsException;
import com.xixizai.personalblogwebsite.exception.BatchUnblockVisitorsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VisitorService {


    //批量封禁访客
    Result batchBlockVisitors(List<Long> ids) throws BatchBlockVisitorsException;

    //批量解封访客
    Result batchUnblockVisitors(List<Long> ids) throws BatchUnblockVisitorsException;
}
