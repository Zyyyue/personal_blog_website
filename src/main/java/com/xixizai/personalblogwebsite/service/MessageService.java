package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.AdminReplyMessageException;
import com.xixizai.personalblogwebsite.exception.BatchApproveMessageException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteMessageException;
import com.xixizai.personalblogwebsite.pojo.dto.MessageDTO;
import com.xixizai.personalblogwebsite.pojo.dto.MessageReplyDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface MessageService {


    //批量审核通过留言
    Result batchApproveMessages(List<Long> ids) throws BatchApproveMessageException;

    //批量删除留言
    Result batchDeleteMessages(List<Long> ids) throws BatchDeleteMessageException;

    //管理员回复留言
    Result adminReplyMessage(MessageReplyDTO messageReplyDTO, HttpServletRequest request) throws AdminReplyMessageException;

    //提交留言
    Result submitMessage(MessageDTO messageDTO,HttpServletRequest request) throws AddOperationException;

    //获取留言列表
    Result getMessagesList(Long visitorId) throws GetOptsException;
}
