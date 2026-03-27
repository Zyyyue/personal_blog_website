package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssSubscriptionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/rssSubscription")
public class RssSubscriptionController {

    @Resource
    private RssSubscriptionService rssSubscriptionService;

    /**
     * 获取所有激活的订阅
     * @return
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getAllRssSubscriptions() throws GetOptsException {
        return rssSubscriptionService.getAllRssSubscription();
    }


    /**
     * 根据id查询订阅
     * @param id
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) throws GetOptsException {
        return rssSubscriptionService.findById(id);
    }


    /**
     * 更新订阅
     * @param rssSubscriptions
     * @return
     * @throws UpdateOperationsException
     */
    @PutMapping("")
    public Result updateRssSubscription(@RequestBody RssSubscriptions rssSubscriptions) throws UpdateOperationsException {
        return rssSubscriptionService.updateRssSubscription(rssSubscriptions);
    }

}
