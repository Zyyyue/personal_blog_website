package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.FriendLinkMapper;
import com.xixizai.personalblogwebsite.pojo.entity.FriendLinks;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.FriendLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {


    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Override
    public Result getAllFriendLinks() throws GetOptsException {

        try{
            List<FriendLinks> list=friendLinkMapper.getAllFriendLinks();
            if(list==null||list.isEmpty()){
                throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
            }
            return Result.success(list);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }
}
