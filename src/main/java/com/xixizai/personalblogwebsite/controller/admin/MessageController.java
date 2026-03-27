package com.xixizai.personalblogwebsite.controller.admin;

import com.xixizai.personalblogwebsite.exception.BatchApproveMessageException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteMessageException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MessageService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @DeleteMapping()
    public Result batchDeleteMessages(@RequestParam List<Long>ids) throws BatchDeleteMessageException {
        return messageService.batchDeleteMessages(ids);
    }

}
