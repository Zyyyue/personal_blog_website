package com.xixizai.personalblogwebsite.controller.admin;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleTagDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleTagService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加标签
     * @param articleTagDTO
     * @return
     * @throws PassedParameterException
     * @throws AddOperationException
     */
    @PostMapping()
    public Result addArticleTag(@RequestBody ArticleTagDTO articleTagDTO) throws PassedParameterException, AddOperationException {
        return articleTagService.addArticleTag(articleTagDTO);
    }

    /**
     * 更新文章标签
     * @param articleTagDTO
     * @return
     * @throws PassedParameterException
     * @throws UpdateOperationsException
     */
    @PutMapping()
    public Result updateArticleTag(@RequestBody ArticleTagDTO articleTagDTO) throws PassedParameterException, UpdateOperationsException {
        return articleTagService.updateArticleTag(articleTagDTO);
    }


}
