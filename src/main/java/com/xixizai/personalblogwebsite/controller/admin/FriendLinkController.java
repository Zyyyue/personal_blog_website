package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.FriendLinkDTO;
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

    /**
     * 添加友链
     * @param friendLinkDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addFriendLink(@RequestBody FriendLinkDTO friendLinkDTO) throws AddOperationException {
        return friendLinkServic.addFriendLink(friendLinkDTO);
    }

    /**
     * 更改友链信息
     * @param friendLinkDTO
     * @return
     * @throws PassedParameterException
     * @throws UpdateOperationsException
     */
    @PutMapping()
    public Result updateFriendLink(@RequestBody FriendLinkDTO friendLinkDTO) throws PassedParameterException, UpdateOperationsException {
        return friendLinkServic.updateFriendLink(friendLinkDTO);
    }

}
