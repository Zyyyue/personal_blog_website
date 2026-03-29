package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ReportService {
    //浏览量统计
    Result getViewStatistics(LocalDate begin, LocalDate end) throws GetOptsException;
}
