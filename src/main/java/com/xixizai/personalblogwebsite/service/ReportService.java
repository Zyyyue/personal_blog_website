package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ReportService {
    //浏览量统计
    Result getViewStatistics(LocalDate begin, LocalDate end) throws GetOptsException;

    //访客统计
    Result getVisitorStatistics(LocalDate begin, LocalDate end) throws GetOptsException;

    //访客省份分布
    Result getProvinceDistribution() throws GetOptsException;

    //获取访客访问Top10
    Result getArticleViewTop10() throws GetOptsException;
}
