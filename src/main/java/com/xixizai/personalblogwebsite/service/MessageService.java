package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.BatchApproveMessageException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {


    //批量审核通过留言
    Result batchApproveMessages(List<Long> ids) throws BatchApproveMessageException;
}
