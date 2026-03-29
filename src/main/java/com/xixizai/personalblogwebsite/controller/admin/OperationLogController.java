package com.xixizai.personalblogwebsite.controller.admin;

import com.xixizai.personalblogwebsite.exception.BatchDeleteOperationLogsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.OperationLogService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
