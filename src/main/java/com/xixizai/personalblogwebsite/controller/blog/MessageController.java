package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.MessageDTO;
import com.xixizai.personalblogwebsite.pojo.dto.MessageEditDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("blogMessageController")
@RequestMapping("/blog/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 提交留言
     * @param messageDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result submitMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request) throws AddOperationException {
        return messageService.submitMessage(messageDTO,request);
    }

    /**
     * 获取留言列表
     * @param visitorId
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getMessagesList(@RequestParam Long visitorId) throws GetOptsException {
        return messageService.getMessagesList(visitorId);
    }

    /**
     * 编辑留言
     * @param messageEditDTO
     * @return
     * @throws Exception
     */
    @PutMapping("/edit")
    public Result editMessage(@RequestBody MessageEditDTO messageEditDTO) throws Exception {
        return messageService.editMessage(messageEditDTO);
    }

    /**
     * 删除留言
     * @param id
     * @param visitorId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public Result deleteMessage(@PathVariable Long id,@RequestParam Long visitorId) throws Exception {
        return messageService.deleteMessage(id,visitorId);
    }

}
