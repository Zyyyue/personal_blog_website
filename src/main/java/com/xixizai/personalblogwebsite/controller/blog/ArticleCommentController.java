package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentDTO;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import com.xixizai.personalblogwebsite.utils.IpUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.concurrent.TimeUnit;

import static com.xixizai.personalblogwebsite.constant.RedisConstant.VIEW_LIMIT_PREFIX;
import static com.xixizai.personalblogwebsite.constant.RedisConstant.VIEW_LIMIT_SECONDS;

@RestController("blogArticleCommentController")
@RequestMapping("/blog/articleComment")
public class ArticleCommentController {

    @Resource
    private ArticleCommentService articleCommentService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取评论列表
     * @param articleId
     * @return
     * @throws PassedParameterException
     * @throws ArticleNotFoundException
     * @throws GetOptsException
     */
    @GetMapping("/article/{articleId}")
    public Result getComments(@PathVariable Long articleId,HttpServletRequest request) throws PassedParameterException, ArticleNotFoundException, GetOptsException {
        String key = getKey(request, articleId);
        //如果这本书的key没了，可以重新设置key,时间1小时
        if(!stringRedisTemplate.hasKey(key)){
            stringRedisTemplate.opsForValue().set(key,"1",VIEW_LIMIT_SECONDS, TimeUnit.SECONDS);
        }
        return articleCommentService.getArticleCommentById(articleId);
    }

    /**
     * 提交评论
     * @param articleCommentDTO
     * @return
     * @throws AddOperationException
     */
    @PostMapping()
    public Result submitComment(@RequestBody ArticleCommentDTO articleCommentDTO, HttpServletRequest request) throws AddOperationException {
        return articleCommentService.submitComment(articleCommentDTO,request);
    }

    /**
     * 获取key
     * @param request
     * @return
     */
    private String getKey(HttpServletRequest request,Long id){
        String clientIp = IpUtil.getClientIp(request);
        if(IpUtil.isLocalIp(clientIp)){
            //如果是的话就找主机ip
            clientIp = IpUtil.getLocalHostIp();
        }
        String key=VIEW_LIMIT_PREFIX+":"+clientIp+":"+id;
        return key;
    }
}
