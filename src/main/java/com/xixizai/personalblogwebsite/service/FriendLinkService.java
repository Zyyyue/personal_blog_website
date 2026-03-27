package com.xixizai.personalblogwebsite.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.FriendLinkDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface FriendLinkService {
    //获取所有友链
    Result getAllFriendLinks() throws GetOptsException;

    //添加友链
    Result addFriendLink(FriendLinkDTO friendLinkDTO) throws AddOperationException;
}
