package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.pojo.dto.RssLinkDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/rssLink")
public class RssLinkController {

    @Resource
    private RssLinkService rssLinkService;

    /**
     * 获取所有 RSS 链接
     */
    @GetMapping()
    public Result getAllRssLinks() throws GetOptsException {
        return rssLinkService.getAllRssLinks();
    }

    /**
     * 添加 RSS 链接
     */
    @PostMapping()
    public Result addRssLink(@RequestBody RssLinkDTO dto) throws AddOperationException {
        return rssLinkService.addRssLink(dto);
    }

    /**
     * 更新 RSS 链接
     */
    @PutMapping()
    public Result updateRssLink(@RequestBody RssLinkDTO dto) throws UpdateOperationsException {
        return rssLinkService.updateRssLink(dto);
    }

    /**
     * 批量删除 RSS 链接
     */
    @DeleteMapping()
    public Result batchDeleteRssLinks(@RequestParam List<Long> ids) throws BatchDeleteRssSubscriptionException {
        return rssLinkService.batchDeleteRssLinks(ids);
    }
}
