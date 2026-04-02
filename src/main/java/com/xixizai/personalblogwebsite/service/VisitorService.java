package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchBlockVisitorsException;
import com.xixizai.personalblogwebsite.exception.BatchUnblockVisitorsException;
import com.xixizai.personalblogwebsite.pojo.dto.VisitorRecordDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Visitors;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface VisitorService {


    //批量封禁访客
    Result batchBlockVisitors(List<Long> ids) throws BatchBlockVisitorsException;

    //批量解封访客
    Result batchUnblockVisitors(List<Long> ids) throws BatchUnblockVisitorsException;

    //添加访客
    void addVisitors(Visitors visitors, HttpServletRequest request) throws AddOperationException;

    //根据请求获取访客id
    Long getVisitorIdByRequest(HttpServletRequest request) throws GetOptsException;

    //记录访客信息
    Result recordVisitorInfor(VisitorRecordDTO visitorRecordDTO,HttpServletRequest request) throws AddOperationException;
}
