package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteOperationLogsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.OperationLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/operationLog")
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    /**
     * 批量删除操作日志
     * @param ids
     * @return
     * @throws BatchDeleteOperationLogsException
     */
    @DeleteMapping()
    public Result batchDeleteOperationLogs(@RequestParam List<Long> ids) throws BatchDeleteOperationLogsException {
        return operationLogService.batchDeleteOperationLogs(ids);
    }


    /**
     * 分页查询操作日志
     * @param page
     * @param pageSize
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/page")
    public Result pageQueryOperation(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSize) throws GetOptsException {
        return operationLogService.pageQuery(page,pageSize);
    }


}
