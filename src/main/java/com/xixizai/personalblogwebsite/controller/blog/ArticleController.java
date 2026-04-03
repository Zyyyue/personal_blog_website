package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/blog/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 根据slug查找articles
     * @param slug
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/detail/{slug}")
    public Result getArticleBySlug(@PathVariable String slug) throws GetOptsException {
        return articleService.getArticleBySlug(slug);
    }

    /**
     * 获取文章归档
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/archive")
    public Result getArtilceArchive() throws GetOptsException {
        return articleService.getArtilceArchive();
    }

    /**
     * 分页查询已经发布的文章
     * @param page
     * @param pageSize
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/page")
    public Result pageQueryArticle(@RequestParam Integer page,@RequestParam Integer pageSize) throws GetOptsException {
        return articleService.pageQueryArticle(page,pageSize);
    }

}
