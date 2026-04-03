package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.constant.StatusConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.ArticleCommentMapper;
import com.xixizai.personalblogwebsite.mapper.ArticleMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentEditDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleCommentReplyDTO;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.entity.ArticleComments;
import com.xixizai.personalblogwebsite.pojo.entity.Views;
import com.xixizai.personalblogwebsite.pojo.result.PageResult;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.ArticleCommentVO;
import com.xixizai.personalblogwebsite.service.ArticleCommentService;
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
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
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

            List<ArticleCommentVO> allComments = articleCommentMapper.getRootComments(id);

            // 组装树形结构
            List<ArticleCommentVO> treeComments = buildCommentTree(allComments);

            if(treeComments==null||treeComments.isEmpty()){
                return Result.error("查找失败,该文章暂时没有评论");
            }else{
                return Result.success(treeComments);
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
     * 访客编辑评论
     * @param articleCommentEditDTO
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result editComment(ArticleCommentEditDTO articleCommentEditDTO) throws UpdateOperationsException {
        try{

            if(articleCommentEditDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            Long id = articleCommentEditDTO.getId();
            ArticleComments comment = articleCommentMapper.findArticleCommentById(id);

            if(comment==null){
                throw new Exception("该评论不存在");
            }

            if(!comment.getVisitorId().equals(articleCommentEditDTO.getVisitorId())){
                throw new ValidationException("无权编辑此评论");
            }

            ArticleComments updateComment= new ArticleComments();
            updateComment=updateComment.builder()
                    .id(articleCommentEditDTO.getId())
                    .content(articleCommentEditDTO.getContent())
                    .build();
            articleCommentEditDTO.setIsMarkdown(comment.getIsMarkdown());
            if(articleCommentEditDTO.getIsMarkdown()!=null&&articleCommentEditDTO.getIsMarkdown()==1){
                String toHtml = MarkdownUtil.toHtml(updateComment.getContent());
                updateComment.setContentHtml(toHtml);
            }else{
                updateComment.setContentHtml(updateComment.getContent());
            }
            articleCommentMapper.updateContent(updateComment);
            return Result.success("访客编辑评论成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 访客删除评论
     * @param id
     * @param visitorId
     * @return
     * @throws Exception
     */
    @Override
    public Result deleteComment(Long id, Long visitorId) throws Exception {
        try{

            if(id==null||visitorId==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            ArticleComments comment = articleCommentMapper.findArticleCommentById(id);
            if (comment == null) {
                throw new ValidationException("评论不存在");
            }
            if (!comment.getVisitorId().equals(visitorId)) {
                throw new ValidationException("无权删除此评论");
            }

            // 如果是根评论，级联删除所有子评论
            if (comment.getRootId() == null || comment.getRootId() == 0) {
                Integer childCount = articleCommentMapper.countByRootId(id);
                if (childCount != null && childCount > 0) {
                    articleCommentMapper.deleteByRootId(id);
                    // 评论数减去子评论数
                    for (int i = 0; i < childCount; i++) {
                        articleCommentMapper.decrementCommentCount(comment.getArticleId());
                    }
                }
            }

            articleCommentMapper.deleteById(id);
            // 文章评论数-1
            articleCommentMapper.decrementCommentCount(comment.getArticleId());
            return Result.success("访客删除评论成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new Exception("删除访客评论失败");
        }
    }

    /**
     * 分页查询评论
     * @param page
     * @param pageSize
     * @param articleId
     * @param isApproved
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result pageQueryComments(Integer page, Integer pageSize, Long articleId, Integer isApproved) throws GetOptsException {
      try{
          //处理一下
          if(page==null||page<1){
            page=1;
          }

          if(pageSize==null||pageSize<1){
              pageSize=10;
          }

          if((isApproved==null)||(isApproved!=1&&isApproved!=0)){
              isApproved=1;
          }

          //查询该文章的所有评论（平铺列表）
          List<ArticleCommentVO> allComments = articleCommentMapper.getAllCommentsByArticleId(articleId, isApproved);

          //组装树形结构
          List<ArticleCommentVO> treeComments = buildCommentTree(allComments);

          //手动分页
          int total = treeComments.size();
          int fromIndex = (page - 1) * pageSize;
          int toIndex = Math.min(fromIndex + pageSize, total);
          List<ArticleCommentVO> pagedComments = (fromIndex < total) ? treeComments.subList(fromIndex, toIndex) : new ArrayList<>();

          //封装分页结果
          PageResult pageResult = PageResult.builder()
                  .total((long) total)
                  .records(pagedComments)
                  .build();

          return Result.success(pageResult);
      }catch (Exception exception){
          exception.printStackTrace();
          throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
      }

    }

    /**
     * 组装评论树形结构
     * @param allComments 平铺的评论列表
     * @return 树形结构的评论列表（只返回根评论，子评论已组装到 children 中）
     */
    private List<ArticleCommentVO> buildCommentTree(List<ArticleCommentVO> allComments) {
        if (allComments == null || allComments.isEmpty()) {
            return new ArrayList<>();
        }

        // 根评论列表
        List<ArticleCommentVO> rootComments = new ArrayList<>();
        // 用于快速查找的 Map
        Map<Long, ArticleCommentVO> commentMap = new HashMap<>();

        // 初始化所有评论，确保 children 不为 null
        for (ArticleCommentVO comment : allComments) {
            comment.setChildren(new ArrayList<>());
            commentMap.put(comment.getId(), comment);
        }

        // 组装树形结构
        for (ArticleCommentVO comment : allComments) {
            if (comment.getParentId() == null || comment.getParentId() == 0) {
                // 根评论
                rootComments.add(comment);
            } else {
                // 子评论，找到父评论并添加
                ArticleCommentVO parent = commentMap.get(comment.getParentId());
                if (parent != null) {
                    parent.getChildren().add(comment);
                } else {
                    // 父评论不存在（可能被过滤或已删除），作为根评论处理
                    rootComments.add(comment);
                }
            }
        }

        // 根评论按创建时间排序
        rootComments.sort((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()));
        // 子评论也按创建时间排序（已在上面按顺序添加）

        return rootComments;
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
