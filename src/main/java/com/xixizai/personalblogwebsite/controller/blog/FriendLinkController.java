package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.FriendLinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("blogFriendLinkController")
@RequestMapping("/blog/friendLink")
public class FriendLinkController {



    @Resource
    private FriendLinkService friendLinkService;
    /**
     * 获取可见友链
     * @return
     */
    @GetMapping()
    public Result getFriendLink() throws GetOptsException {
        return friendLinkService.getFriendLink();
    }

}
