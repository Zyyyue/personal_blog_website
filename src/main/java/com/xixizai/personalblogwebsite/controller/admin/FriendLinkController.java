package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.FriendLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/friendLink")
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkServic;

    /**
     * 获取所有友链
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getAllFriendLinks() throws GetOptsException {
        return friendLinkServic.getAllFriendLinks();
    }
}
