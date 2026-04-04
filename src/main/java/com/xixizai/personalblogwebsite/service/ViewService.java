package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteViewRecordsException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Views;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface ViewService {

    //批量删除浏览记录
    Result batchDeleteViewRecords(List<Long> ids) throws BatchDeleteViewRecordsException;

    //添加浏览记录
    void addViewRecord(Views view, HttpServletRequest request) throws AddOperationException;

    //分页查询浏览量
    Result pageQueryView(Integer page, Integer pageSize, String ip, Integer type) throws GetOptsException;
}
