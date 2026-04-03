package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.xixizai.personalblogwebsite.pojo.result.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.ArticleMapper;
import com.xixizai.personalblogwebsite.mapper.ArticleTagRelationsMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Articles;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.*;
import com.xixizai.personalblogwebsite.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ArticleTagRelationsMapper articleTagRelationsMapper;
    /**
     * 根据id获取文章详情
     * @param id
     * @return
     */
    @Override
    public Result<ArticleDTO> getAdminArticle(Long id) throws ArticleNotFoundException {
        ArticleDTO articleDTO=articleMapper.findArticleById(id);
        //这里判空一下
        if(BeanUtil.isEmpty(articleDTO)){
            throw new ArticleNotFoundException(MessageConstant.ARTICLE_NOT_FOUND_EXCEPTION);
        }

        return Result.success(articleDTO);
    }

    /**
     * 创建文章
     * @param articleDTO
     * @return
     */
    @Transactional
    @Override
    public Result createNewArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, CreateNewArticleException {

        try{
            //这里判空一下
            if(articleDTO==null){
                throw new ArticleDTONotFoundException(MessageConstant.ARTICLEDTO_NOT_FOUND);
            }

            articleMapper.createNewArticle(articleDTO);

            //再在这里判断一下传进来的dto中是否有标签列表
            List<Long> tagIds = articleDTO.getTagIds();
            if(tagIds!=null){
                String slug = articleDTO.getSlug();
                ArticleDTO bySlug = articleTagRelationsMapper.findBySlug(slug);
                if(bySlug.getId()!=null){
                    Long id=bySlug.getId();
                    articleTagRelationsMapper.createArticleTagsAndRelations(tagIds,id);
                }
                log.info("获取到的id为:"+bySlug.getId());
            }

            return Result.success("创建文章成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new CreateNewArticleException(MessageConstant.CREATE_NEW_ARTICLE_FAILSURE);
        }

    }

    /**
     * 更新文章
     * @param articleDTO
     * @return
     */
    @Transactional
    @Override
    public Result updateArticle(ArticleDTO articleDTO) throws ArticleDTONotFoundException, UpdateArticlesException {
        try{
            //这里判空一下
            if(articleDTO==null||articleDTO.getId()==null){
                throw new ArticleDTONotFoundException(MessageConstant.ARTICLEDTO_NOT_FOUND);
            }
            //再单独判断一下文章是否存在
            ArticleDTO articleById = articleMapper.findArticleById(articleDTO.getId());
            if(articleById==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            //先更新文章基本信息
            articleMapper.updateArticle(articleDTO);

            //还得再判断一下dto里面的标签列表是不是为空
            List<Long> tagIds = articleDTO.getTagIds();
            if(tagIds!=null){
                //这里也得改一下标签文章关系表
                articleTagRelationsMapper.updateArticleTagsAndRelations(tagIds,articleDTO.getId());
            }

            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateArticlesException(MessageConstant.UPDATE_ARTICLES_FAILSURE);
        }

    }

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public Result batchDeleteArticles(List<Long> ids) throws IdNotValidException, BatchDeleteArticlesException {

        try{
            //判空一下ids
            if(ids==null|| ids.isEmpty()){
                throw new IdNotValidException(MessageConstant.ID_LIST_NOT_EXIST);
            }

            //去除一下重复id
            List<Long>distinctIds=new ArrayList<>();
            for (Long id : ids) {
                if(!distinctIds.contains(id)){
                    distinctIds.add(id);
                }
            }

            //数据库中存在id的集合是updatedIds
            List<Long>updatedIds=new ArrayList<>();
            //数据库中不存在id的集合是nulledIds
            List<Long>nulledIds=new ArrayList<>();
            //再判断一下ids中的id是否都在数据库中存在,如果不存在的话就需要提示一下，然后删除已经存在的
            for (Long id : distinctIds) {
                if(articleMapper.findArticleById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除文章
            if(!updatedIds.isEmpty()){
                articleMapper.batchDeleteArticles(updatedIds);
                articleTagRelationsMapper.batchDeleteArticleTagsAndRelations(updatedIds);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 篇文章"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 篇，"
                    + "不存在的ID：" + nulledIds);
        }catch (Exception exception){
            throw new BatchDeleteArticlesException(MessageConstant.BATCH_DELETE_ARTICLES_FAILSURE);

        }

    }

    /**
     * 发布文章
     * @param id
     * @param isPublished
     * @return
     */
    @Override
    public Result publishArticle(Long id, Integer isPublished) throws PublishArticleException {

        try{
            //判断一下id和isPublished
            if(id==null||id<=0){
                throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
            }

            if(isPublished==null||isPublished!=1){
                throw new IsPublishedNotValidException(MessageConstant.ISPUBLISHED_NOT_VALID_EXCEPTION);
            }

            articleMapper.publishArticle(id,isPublished);

            return Result.success();
        }catch (Exception exception){
            throw new PublishArticleException(MessageConstant.PUBLISH_ARTICLE_FAILSURE);
        }
    }

    /**
     * 取消发布文章
     * @param id
     * @param isPublished
     * @return
     */
    @Override
    public Result unpublishArticle(Long id, Integer isPublished) throws UnpublishArticleException {

        try{
            //判断一下id和isPublished
            if(id==null||id<=0){
                throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
            }

            if(isPublished==null||isPublished!=0){
                throw new IsPublishedNotValidException(MessageConstant.ISPUBLISHED_NOT_VALID_EXCEPTION);
            }

            articleMapper.unpublishArticle(id,isPublished);

            return Result.success();
        }catch (Exception exception){
            throw new UnpublishArticleException(MessageConstant.UNPUBLISH_ARTICLE_FAILSURE);
        }
    }

    /**
     * 置顶/取消置顶文章
     * @param id
     * @param isTop
     * @return
     */
    @Override
    public Result cancleOrNotTopArticle(Long id, Integer isTop) throws Exception {

        try{
            //判空并且判断是否合法
            if(id==null||isTop==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(isTop!=0&&isTop!=1){
                throw  new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_VALID);
            }

            articleMapper.cancleOrNotTopArticle(id,isTop);
            if(isTop==1){
                return Result.success("置顶操作成功");
            }else {
                return Result.success("取消置顶操作成功");
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new CancleOrNotTopArticleFailsureException(MessageConstant.CANCLE_OR_NOT_TOP_ARTICLE_FALISURE);
        }

    }
    //===========博客端==============

    /**
     * 根据slug查找articles
     * @param slug
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getArticleBySlug(String slug) throws GetOptsException {
        try {

            BlogArticleDetailVO blogArticleDetailVO=articleMapper.getArticleBySlug(slug);

            if(blogArticleDetailVO==null){
                return Result.error("文章未找到");
            }
            //获取文章标签名称列表
            List<String>tagName=new ArrayList<>();
            tagName=articleMapper.getTagNameListByArticleId(blogArticleDetailVO.getId());
            blogArticleDetailVO.setTagNames(tagName);

            //上一篇/下一篇导航
            BlogArticleVO prevArticle=new BlogArticleVO();
            BlogArticleVO nextArticle=new BlogArticleVO();
            prevArticle=articleMapper.getPrevArticle(blogArticleDetailVO.getId());
            nextArticle=articleMapper.getNextArticle(blogArticleDetailVO.getId());
            blogArticleDetailVO.setPrevArticle(prevArticle);
            blogArticleDetailVO.setNextArticle(nextArticle);

            //相关文章推荐
            List<BlogArticleVO>relatedArticles=new ArrayList<>();
            relatedArticles=articleMapper.getRelatedArticles(blogArticleDetailVO.getId(),blogArticleDetailVO.getCategoryId());
            blogArticleDetailVO.setRelatedArticles(relatedArticles);

            return Result.success(blogArticleDetailVO);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }

    /**
     * 获取文章归档
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getArtilceArchive() throws GetOptsException {

        try{

            List<ArticleArchiveVO>articleArchiveVOList=new ArrayList<>();
            //获取文章归档中的单篇文章
            List<ArticleArchiveItemVO>articleArchiveItemVOList=new ArrayList<>();
            articleArchiveItemVOList=articleMapper.getArticleArchiveItemVOList();

            //利用数据库中的publish的年和月生成队列(神来之笔的代码)
            Map<String,ArticleArchiveVO> archiveMap=new LinkedHashMap<>();
            for (ArticleArchiveItemVO itemVO : articleArchiveItemVOList) {
                if(itemVO.getPublishTime()==null){
                    continue;
                }

                int year = itemVO.getPublishTime().getYear();
                int month=itemVO.getPublishTime().getMonthValue();

                String key=year+"-"+month;
                ArticleArchiveVO archiveVO=new ArticleArchiveVO();
                archiveVO=archiveMap.computeIfAbsent(
                    key,k->
                    ArticleArchiveVO.builder()
                            .year(year)
                            .month(month)
                            .articles(new ArrayList<>())
                            .build()
                );
                archiveVO.getArticles().add(itemVO);
            }

            return Result.success(new ArrayList<>(archiveMap.values()));

        }catch (Exception exception){

            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);

        }


    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param title
     * @param categoryId
     * @param isPublished
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result pageQuery(Integer page, Integer pageSize, String title, Long categoryId, Integer isPublished) throws GetOptsException {
        try{

            //使用pageHelper开启分页
            PageHelper.startPage(page,pageSize);
            //执行查询
             Page<ArticleVO> page1= (Page<ArticleVO>) articleMapper.pageQuery(title,categoryId,isPublished);
            return Result.success(new PageResult(page1.getTotal(),page1.getResult()));

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);

        }

    }



    /**
     * 文章搜索
     * @param page
     * @param pageSize
     * @param keyword
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result searchArticles(Integer page, Integer pageSize, String keyword) throws GetOptsException {
        try{
            //使用 PageHelper 开启分页
            PageHelper.startPage(page, pageSize);
            log.info("开始搜索，page={}, pageSize={}, keyword={}", page, pageSize, keyword);
            
            //执行查询 - 使用 Page 接收结果
            Page<ArticleVO> pageResult = (Page<ArticleVO>) articleMapper.pageQuerySearch(keyword);
            log.info("查询完成，total={}, 结果数={}", pageResult.getTotal(), pageResult.getResult().size());
            
            return Result.success(new PageResult(pageResult.getTotal(), pageResult.getResult()));

        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 分页查询获取已经法发布文章
     * @param page
     * @param pageSize
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result pageQueryArticle(Integer page, Integer pageSize) throws GetOptsException {
        try{

            PageHelper.startPage(page,pageSize);
            Page<ArticleVO>list=articleMapper.pageQueryArticle();
            return Result.success(new PageResult(list.getTotal(),list.getResult()));
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }
}
