package com.xixizai.personalblogwebsite.controller.home;

import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.VisitorRecordDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.VisitorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("homeVisitorController")
@RequestMapping("/home/visitor")
public class VisitorController {

    @Resource
    private VisitorService visitorService;

    /**
     * 记录访客信息
     * @param visitorRecordDTO
     * @param request
     * @return
     * @throws AddOperationException
     */
    @PostMapping("/record")
    public Result recordVisitor(@RequestBody VisitorRecordDTO visitorRecordDTO, HttpServletRequest request) throws AddOperationException {
        return visitorService.recordVisitorInfor(visitorRecordDTO,request);
    }

}
