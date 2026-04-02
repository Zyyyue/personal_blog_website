package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理端文章接口
 */
@Slf4j
@RestController("adminArticleController")
@RequestMapping("/admin/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 根据id获取文章详情
     * @param id
     * @return
     * @throws IdNotValidException
     */
    @GetMapping("/{id}")
    public Result<ArticleDTO>getAdminArticle(@PathVariable Long id) throws IdNotValidException, ArticleNotFoundException {
        if(id<=0){
            throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
        }
        return articleService.getAdminArticle(id);
    }

    /**
     * 创建文章
     * @param articleDTO
     * @return
     */
    @PostMapping()
    public Result createNewArticle(@RequestBody ArticleDTO articleDTO) throws ArticleDTONotFoundException, CreateNewArticleException {
        return articleService.createNewArticle(articleDTO);
    }

    /**
     * 更新文章
     * @param articleDTO
     * @return
     */
    @PutMapping()
    public Result updateArticle(@RequestBody ArticleDTO articleDTO) throws ArticleDTONotFoundException, UpdateArticlesException {
        return articleService.updateArticle(articleDTO);
    }

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    @DeleteMapping()
    public Result batchDeleteArticles(@RequestParam List<Long>ids) throws BatchDeleteArticlesException, IdNotValidException {
        return articleService.batchDeleteArticles(ids);
    }

    /**
     * 发布文章
     * @param id
     * @param isPublished
     * @return
     */
    @PutMapping("/publish/{id}")
    public Result publishArticle(@PathVariable Long id,@RequestParam Integer isPublished) throws PublishArticleException {
        return articleService.publishArticle(id,isPublished);
    }

    /**
     * 取消发布文章
     * @param id
     * @param isPublished
     * @return
     */
    @PutMapping("/unpublish/{id}")
    public Result unpublishArticle(@PathVariable Long id,@RequestParam Integer isPublished) throws UnpublishArticleException {
        return articleService.unpublishArticle(id,isPublished);
    }


    /**
     * 置顶/取消置顶文章,1是置顶，0是取消置顶
     * @param id
     * @param isTop
     * @return
     */
    @PutMapping("/top/{id}")
    public Result cancleOrNotTopArticle(@PathVariable Long id,@RequestParam Integer isTop) throws Exception {
        return articleService.cancleOrNotTopArticle(id,isTop);
    }


    /**
     * 分页查询文章列表
     * @param page
     * @param pageSize
     * @param title
     * @param categoryId
     * @param isPublished
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/page")
    public Result PageQuery(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(required = false)String title,@RequestParam(required = false)Long categoryId,@RequestParam(required = false)Integer isPublished) throws GetOptsException {
        return articleService.pageQuery(page,pageSize,title,categoryId,isPublished);
    }

    /**
     * 根据关键词文章搜索
     * @param page
     * @param pageSize
     * @param keyword
     * @return
     * @throws GetOptsException
     */
    @GetMapping("/search")
    public Result searchArticles(@RequestParam(defaultValue = "1")Integer page,@RequestParam (defaultValue = "10")Integer pageSize,@RequestParam String keyword) throws GetOptsException {
        return articleService.searchArticles(page,pageSize,keyword);
    }

}
