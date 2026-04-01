package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.mapper.ArticleFriendLinkMapper;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.FriendLinkVO;
import com.xixizai.personalblogwebsite.service.ArticleFriendLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleFriendLinkServiceImpl implements ArticleFriendLinkService {

    @Resource
    private ArticleFriendLinkMapper articleFriendLinkMapper;

    /**
     * 获取可见友链
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getFriendLink() throws GetOptsException {
        try{

            List<FriendLinkVO> list=articleFriendLinkMapper.getFrindLinkList();
            return Result.success(list);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }
}
