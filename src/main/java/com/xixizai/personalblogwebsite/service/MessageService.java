package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.BatchApproveMessageException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteMessageException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {


    //批量审核通过留言
    Result batchApproveMessages(List<Long> ids) throws BatchApproveMessageException;

    //批量删除留言
    Result batchDeleteMessages(List<Long> ids) throws BatchDeleteMessageException;
}
