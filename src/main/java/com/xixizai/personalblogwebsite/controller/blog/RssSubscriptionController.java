package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssSubscriptionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("blogRssSubscriptionController")
@RequestMapping("/blog/rssSubscription")
public class RssSubscriptionController {

    @Resource
    private RssSubscriptionService rssSubscriptionService;

    /**
     * 添加订阅
     * @param rssSubscriptionDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addRssSubscription(@RequestBody RssSubscriptionDTO rssSubscriptionDTO) throws AddOperationException {
        return rssSubscriptionService.addRssSubscription(rssSubscriptionDTO);
    }

    /**
     * 取消订阅
     * @param email
     * @return
     */
    @PutMapping()
    public Result unSubscribe(@RequestParam String email) throws UpdateOperationsException {
        return rssSubscriptionService.unSubscribe(email);
    }


    /**
     * 检查订阅状态
     * @param visitorId
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/check")
    public Result checkWeatherSubscribe(@RequestParam Long visitorId) throws GetOptsException {
        return rssSubscriptionService.checkWeatherSubscribe(visitorId);
    }


}
