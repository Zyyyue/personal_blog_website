package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteOperationLogsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationLogService {
    //批量删除操作日志
    Result batchDeleteOperationLogs(List<Long> ids) throws BatchDeleteOperationLogsException;

    //分页查询操作日志
    Result pageQuery(Integer page, Integer pageSize) throws GetOptsException;
}
