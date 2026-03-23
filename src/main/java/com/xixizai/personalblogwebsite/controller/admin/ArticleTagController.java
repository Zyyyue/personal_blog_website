package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleTagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/article/tag")
public class ArticleTagController {
    @Resource
    private ArticleTagService articleTagService;

    /**
     * 获取所有标签
     * @return
     * @throws PassedParameterException
     * @throws GetOptsException
     */
    @GetMapping()
    public Result getAllArticleTags() throws PassedParameterException, GetOptsException {
        return articleTagService.getAllArticleTags();
    }
}
