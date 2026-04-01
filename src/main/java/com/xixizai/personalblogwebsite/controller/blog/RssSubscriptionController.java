package com.xixizai.personalblogwebsite.controller.blog;

import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssSubscriptionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
