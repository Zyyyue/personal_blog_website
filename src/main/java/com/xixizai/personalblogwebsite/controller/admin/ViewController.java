package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteViewRecordsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ViewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/view")
public class ViewController {

    @Resource
    private ViewService viewService;

    /**
     * 批量删除浏览记录
     * @param ids
     * @return
     * @throws BatchDeleteViewRecordsException
     */
    @DeleteMapping()
    public Result batchDeleteViewsRecords(@RequestParam List<Long> ids) throws BatchDeleteViewRecordsException {
        return viewService.batchDeleteViewRecords(ids);
    }

    /**
     * 分页查询浏览记录
     * @param page
     * @param pageSize
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/page")
    public Result pageQueryView(@RequestParam Integer page,@RequestParam Integer pageSize) throws GetOptsException {
        return viewService.pageQueryView(page,pageSize);
    }


}
