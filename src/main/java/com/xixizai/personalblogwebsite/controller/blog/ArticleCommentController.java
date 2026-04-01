package com.xixizai.personalblogwebsite.controller.blog;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.ArticleNotFoundException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.exception.UpdateOperationsException;
import com.xixizai.personalblogwebsite.mapper.ArticleMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentEditDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Views;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import com.xixizai.personalblogwebsite.service.ViewService;
import com.xixizai.personalblogwebsite.utils.IpUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ViewService viewService;

    /**
     * 获取评论列表
     * @param articleId
     * @return
     * @throws PassedParameterException
     * @throws ArticleNotFoundException
     * @throws GetOptsException
     */
    @GetMapping("/article/{articleId}")
    public Result getComments(@PathVariable Long articleId,HttpServletRequest request) throws PassedParameterException, ArticleNotFoundException, GetOptsException, AddOperationException {
        String key = getKey(request, articleId);
        //如果这本书的key没了，可以重新设置key,时间1小时
        if(!stringRedisTemplate.hasKey(key)){
            stringRedisTemplate.opsForValue().set(key,"1",VIEW_LIMIT_SECONDS, TimeUnit.SECONDS);
            //然后添加浏览量
            //这本书的浏览量+1
            Views view =new Views();
            ArticleDTO articleById = articleMapper.findArticleById(articleId);
            view.setPageTitle(articleById.getTitle());
            viewService.addViewRecord(view,request);
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

    /**
     * 访客编辑评论
     * @param articleCommentEditDTO
     * @return
     * @throws UpdateOperationsException
     */
    @PutMapping("/edit")
    public Result editComment(@RequestBody ArticleCommentEditDTO articleCommentEditDTO) throws UpdateOperationsException {
        return articleCommentService.editComment(articleCommentEditDTO);
    }

    /**
     * 删除评论
     * @param id
     * @param visitorId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable Long id,@RequestParam Long visitorId) throws Exception {
        return articleCommentService.deleteComment(id,visitorId);
    }

}
