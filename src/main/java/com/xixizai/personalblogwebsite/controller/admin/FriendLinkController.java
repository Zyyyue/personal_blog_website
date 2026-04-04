package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteFriendLinksException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.FriendLinkDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.FriendLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("adminFriendLinkController")
@RequestMapping("/admin/friendLink")
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;

    /**
     * 获取所有友链
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getAllFriendLinks() throws GetOptsException {
        return friendLinkService.getAllFriendLinks();
    }

    /**
     * 添加友链
     * @param friendLinkDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addFriendLink(@RequestBody FriendLinkDTO friendLinkDTO) throws AddOperationException {
        return friendLinkService.addFriendLink(friendLinkDTO);
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
        return friendLinkService.updateFriendLink(friendLinkDTO);
    }

    /**
     * 批量删除友链
     * @param ids
     * @return
     * @throws BatchDeleteFriendLinksException
     */
    @DeleteMapping()
    public Result batchDeleteFriendLinks(@RequestParam(required = false) List<Long>ids) throws BatchDeleteFriendLinksException {
        return friendLinkService.batchDeleteFriendLinks(ids);
    }

}
