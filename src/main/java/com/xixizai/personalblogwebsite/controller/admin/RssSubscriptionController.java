package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssSubscriptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
