package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.BatchDeleteViewRecordsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ViewService {

    //批量删除浏览记录
    Result batchDeleteViewRecords(List<Long> ids) throws BatchDeleteViewRecordsException;
}
