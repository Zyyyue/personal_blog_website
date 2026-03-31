package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.constant.StatusConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.ArticleCommentMapper;
import com.xixizai.personalblogwebsite.mapper.ArticleMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentReplyDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import com.xixizai.personalblogwebsite.pojo.entity.Views;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
import com.xixizai.personalblogwebsite.service.ArticleService;
import com.xixizai.personalblogwebsite.service.ViewService;
import com.xixizai.personalblogwebsite.utils.IpUtil;
import com.xixizai.personalblogwebsite.utils.MarkdownUtil;
import com.xixizai.personalblogwebsite.utils.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xixizai.personalblogwebsite.constant.RedisConstant.VIEW_LIMIT_PREFIX;
import static com.xixizai.personalblogwebsite.constant.RedisConstant.VIEW_LIMIT_SECONDS;
import static org.commonmark.internal.util.Escaping.escapeHtml;

@Service
@Slf4j
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserAgentUtil userAgentUtil;

    @Resource
    private ViewService viewService;

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 根据文章id查询评论
     * @param id
     * @return
     * @throws PassedParameterException
     * @throws ArticleNotFoundException
     * @throws GetOptsException
     */
    @Transactional
    @Override
    public Result getArticleCommentById(Long id) throws PassedParameterException, ArticleNotFoundException, GetOptsException {

        try{
            if(id==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            List<ArticleComments> articleCommentById = articleCommentMapper.getArticleCommentById(id);

            if(articleCommentById==null||articleCommentById.isEmpty()){
                return Result.error("查找失败,该文章暂时没有评论");
            }else{
                return Result.success(articleCommentById);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 批量审核通过评论
     * @param ids
     * @return
     * @throws PassedParameterException
     */
    @Override
    public Result batchApproveArticleComment(List<Long> ids) throws PassedParameterException, BatchApproveArticleCommentException {

        try{
            //判空
            if(ids==null||ids.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //去重一下id
            List<Long>distinctIds=new ArrayList<>();
            for (Long id : ids) {
                if(!distinctIds.contains(id)){
                    distinctIds.add(id);
                }
            }

            //再看一下数据库中是否有对应id

            //数据库中存在id的集合是updatedIds
            List<Long>updatedIds=new ArrayList<>();
            //数据库中不存在id的集合是nulledIds
            List<Long>nulledIds=new ArrayList<>();
            //再判断一下ids中的id是否都在数据库中存在,如果不存在的话就需要提示一下，然后删除已经存在的
            for (Long id : distinctIds) {
                if(articleCommentMapper.findArticleCommentById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量审核文章评论
            if(!updatedIds.isEmpty()){
                articleCommentMapper.batchApproveArticleComment(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量审核成功，共审核 " + updatedIds.size() + " 条文章评论/"));
            }
            return Result.success("批量审核成功，成功审核 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchApproveArticleCommentException(MessageConstant.BATCH_APPROVE_ARTICLE_COMMENT_EXCEPTION);
        }
    }

    /**
     * 批量删除文章评论
     * @param ids
     * @return
     * @throws BatchDeleteArticleCommentException
     */
    @Override
    public Result batchDeleteArticleComment(List<Long> ids) throws BatchDeleteArticleCommentException {
        try {

            //判空
            if(ids==null||ids.isEmpty()){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            //去重一下id
            List<Long>distinctIds=new ArrayList<>();
            for (Long id : ids) {
                if(!distinctIds.contains(id)){
                    distinctIds.add(id);
                }
            }

            //再看一下数据库中是否有对应id

            //数据库中存在id的集合是updatedIds
            List<Long>updatedIds=new ArrayList<>();
            //数据库中不存在id的集合是nulledIds
            List<Long>nulledIds=new ArrayList<>();
            //再判断一下ids中的id是否都在数据库中存在,如果不存在的话就需要提示一下，然后删除已经存在的
            for (Long id : distinctIds) {
                if(articleCommentMapper.findArticleCommentById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量审核文章评论
            if(!updatedIds.isEmpty()){
                articleCommentMapper.batchDeleteArticleComment(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量审核成功，共审核 " + updatedIds.size() + " 条文章评论/"));
            }
            return Result.success("批量审核成功，成功审核 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteArticleCommentException(MessageConstant.BATCH_DELETE_ARTICLE_COMMENT_FAILSURE);
        }

    }

    /**
     *管理员回复评论
     * @param articleCommentReplyDTO
     * @return
     * @throws AdminReplyCommentException
     */
    @Override
    public Result adminReplyComment(ArticleCommentReplyDTO articleCommentReplyDTO, HttpServletRequest request) throws AdminReplyCommentException {
        try {
            //判空一下
            if(articleCommentReplyDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(articleCommentReplyDTO.getArticleId()==null){
                throw new PassedParameterException("文章ID不能为空");
            }

            if(articleCommentReplyDTO.getParentId()==null){
                throw new PassedParameterException("父评论ID不能为空");
            }

            if(articleCommentReplyDTO.getContent()==null||articleCommentReplyDTO.getContent().trim()==null){
                throw new PassedParameterException("回复内容不能为空");
            }

            ArticleComments articleComments = BeanUtil.toBean(articleCommentReplyDTO, ArticleComments.class);

            //设置管理员回复相关字段
            articleComments.setIsApproved(StatusConstant.ENABLE);
            articleComments.setIsEdited(StatusConstant.DISABLE);
            articleComments.setIsAdminReply(StatusConstant.ENABLE);

            //处理markdown
            if(articleCommentReplyDTO.getIsMarkdown()!=null&&articleCommentReplyDTO.getIsMarkdown()==1){
                //如果是markdown
                String html=MarkdownUtil.toHtml(articleCommentReplyDTO.getContent());
                articleComments.setContentHtml(html);
            }else{
                articleComments.setContentHtml(articleCommentReplyDTO.getContent());
            }

            //获取客户端信息
            if(request!=null){
                String clientIp = IpUtil.getClientIp(request);

                //获取地理位置信息
                Map<String, String> geoInfo = IpUtil.getGeoInfo(clientIp);
                String province = geoInfo.getOrDefault("province", "");
                String city=geoInfo.getOrDefault("city","");

                //构建位置字符串
                String location="";
                if(!province.isEmpty()){
                    location=province;
                    if(!city.isEmpty()&&!city.equals(province)){
                        location+="-"+city;
                    }
                }

                if(!location.isEmpty()){
                    articleComments.setLocation(location);
                }

                log.info("管理员回复-IP:{},位置:{}",clientIp,location);
            }

            articleCommentMapper.adminReplyComment(articleComments);
            return Result.success("管理员回复成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AdminReplyCommentException(MessageConstant.REPLY_COMMENT_FAILSURE);
        }

    }

    /**
     * 提交评论
     * @param articleCommentDTO
     * @param request
     * @return
     * @throws AddOperationException
     */
    @Transactional
    @Override
    public Result submitComment(ArticleCommentDTO articleCommentDTO,HttpServletRequest request) throws AddOperationException {
        try{

            if(articleCommentDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            ArticleComments articleComments = BeanUtil.toBean(articleCommentDTO, ArticleComments.class);

            //获取用户使用的浏览器
            String userAgentString = request.getHeader("User-Agent");
            String browserName = userAgentUtil.getBrowserName(userAgentString);
            articleComments.setUserAgentBrowser(browserName);

            //获取用户使用的操作系统
            String osName = userAgentUtil.getOsName(browserName);
            articleComments.setUserAgentOs(osName);

            //获取用户的地理位置
            String clientIp = IpUtil.getClientIp(request);
            if(IpUtil.isLocalIp(clientIp)){
                String location="中国 成都";
                articleComments.setLocation(location);
            }else{
                Map<String, String> geoInfo = IpUtil.getGeoInfo(clientIp);
                String country=geoInfo.getOrDefault("country","未知");
                String city=geoInfo.getOrDefault("city","未知");
                if(country==null||country.isEmpty()){
                    articleComments.setLocation("未知");
                }
                String location=country+" "+city;
                articleComments.setLocation(location);
            }

            //处理markdown

            if (articleCommentDTO.getIsMarkdown() == 1) {
                //Markdown
                if (MarkdownUtil.isHtml(articleCommentDTO.getContent())) {
                    //内容是HTML过滤后存储
                    String sanitize = MarkdownUtil.sanitize(articleCommentDTO.getContent());
                    articleComments.setContentHtml(sanitize);
                } else {
                    //内容是Markdown转换为HTML
                    String contentHtml = MarkdownUtil.toHtml(articleCommentDTO.getContent());
                    articleComments.setContentHtml(contentHtml);
                }
            } else {
                //普通文本模式
                String content = articleCommentDTO.getContent();

                if (MarkdownUtil.isHtml(content)) {
                    //内容是 HTML过滤后存储
                    String sanitize = MarkdownUtil.sanitize(content);
                    articleComments.setContentHtml(sanitize);
                } else {
                    // 普通文本：转义 + 换行转 <br>
                    String escaped = escapeHtml(content);
                    String contentHtml = escaped.replace("\n", "<br>").replace("\r\n", "<br>");
                    articleComments.setContentHtml(contentHtml);
                }
            }

            String key = getKey(request, articleCommentDTO.getArticleId());
            if(!stringRedisTemplate.hasKey(key)){
                //如果没有key的话
                stringRedisTemplate.opsForValue().set(key,"1",VIEW_LIMIT_SECONDS, TimeUnit.SECONDS);
                //这本书的浏览量+1
                Views view =new Views();
                Long articleId=articleCommentDTO.getArticleId();
                ArticleDTO articleById = articleMapper.findArticleById(articleId);
                view.setPageTitle(articleById.getTitle());
                viewService.addViewRecord(view,request);

            }
            //提交评论
            articleCommentMapper.submitComment(articleComments);
            return Result.success("提交成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }

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
