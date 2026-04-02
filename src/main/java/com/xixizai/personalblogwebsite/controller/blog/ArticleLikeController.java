package com.xixizai.personalblogwebsite.controller.blog;

import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleLikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("blogArticleLikeController")
@RequestMapping("/blog/articleLike")
public class ArticleLikeController {

    @Resource
    private ArticleLikeService articleLikeService;

    /**
     * 点赞文章
     * @param articleId
     * @param visitorId
     * @return
     * @throws AddOperationException
     */
    @PostMapping("/{articleId}")
    public Result likeArticle(@PathVariable Long articleId, @RequestParam Long visitorId) throws AddOperationException {
        return articleLikeService.likeArticle(articleId,visitorId);
    }

}
