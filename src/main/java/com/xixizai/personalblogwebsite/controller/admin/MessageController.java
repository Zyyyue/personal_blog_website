package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AdminReplyMessageException;
import com.xixizai.personalblogwebsite.exception.BatchApproveMessageException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteMessageException;
import com.xixizai.personalblogwebsite.pojo.dto.MessageDTO;
import com.xixizai.personalblogwebsite.pojo.dto.MessageReplyDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MessageService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 批量审核留言
     * @param ids
     * @return
     * @throws BatchApproveMessageException
     */
    @PutMapping("/approve")
    public Result batchApproveMessages(@RequestParam List<Long> ids) throws BatchApproveMessageException {
        return messageService.batchApproveMessages(ids);
    }

    /**
     * 批量删除留言
     * @param ids
     * @return
     * @throws BatchDeleteMessageException
     */
    @DeleteMapping()
    public Result batchDeleteMessages(@RequestParam List<Long>ids) throws BatchDeleteMessageException {
        return messageService.batchDeleteMessages(ids);
    }

    /**
     * 管理员回复留言
     * @param messageReplyDTO
     * @param request
     * @return
     * @throws AdminReplyMessageException
     */
    @PostMapping("/reply")
    public Result adminReplyMessage(@RequestBody MessageReplyDTO messageReplyDTO, HttpServletRequest request) throws AdminReplyMessageException {
        return messageService.adminReplyMessage(messageReplyDTO,request);
    }

    /**
     * 分页查询留言
     * @param page
     * @param pageSize
     * @param isApproved
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/page")
    public Result pageQueryMessages(@RequestParam Integer page,@RequestParam Integer pageSize,@RequestParam Integer isApproved) throws GetOptsException {
        return messageService.pageQueryMessages(page,pageSize,isApproved);
    }

}
