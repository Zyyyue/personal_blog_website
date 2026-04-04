package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.BatchBlockVisitorsException;
import com.xixizai.personalblogwebsite.exception.BatchUnblockVisitorsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.VisitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/visitor")
public class VisitorController {

    @Resource
    private VisitorService visitorService;

    /**
     * 批量封禁访客
     * @param ids
     * @return
     * @throws BatchBlockVisitorsException
     */
    @PutMapping("/block")
    public Result batchBlockVisitors(@RequestParam List<Long> ids) throws BatchBlockVisitorsException {
        return visitorService.batchBlockVisitors(ids);
    }

    /**
     * 批量解封游客
     * @param ids
     * @return
     * @throws BatchUnblockVisitorsException
     */
    @PutMapping("/unblock")
    public Result batchUnblockVisitors(@RequestParam List<Long>ids) throws BatchUnblockVisitorsException {
        return visitorService.batchUnblockVisitors(ids);
    }

    /**
     * 分页查询访客
     * @param page
     * @param pageSize
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/page")
    public Result pageQueryVisitor(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSize) throws GetOptsException {
        return visitorService.pageQueryVisitor(page,pageSize);
    }


}
