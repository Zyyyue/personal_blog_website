package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.RssSubscriptionMapper;
import com.xixizai.personalblogwebsite.pojo.dto.RssSubscriptionDTO;
import com.xixizai.personalblogwebsite.pojo.entity.RssSubscriptions;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.RssSubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssSubscriptionImpl implements RssSubscriptionService {

    @Resource
    private RssSubscriptionMapper rssSubscriptionMapper;

    /**
     * 获取所有激活的订阅
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getAllRssSubscription() throws GetOptsException {
        try{

            List<RssSubscriptions> allRssSubscription = rssSubscriptionMapper.getAllRssSubscription();
            return Result.success(allRssSubscription);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }

    }

    /**
     * 根据id查询
     * @param id
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result findById(Long id) throws GetOptsException {
        try{

            RssSubscriptions rssSubscriptions=rssSubscriptionMapper.findById(id);
            if(rssSubscriptions==null){
                return Result.error("获取失败");
            }
            return Result.success(rssSubscriptions);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 更新订阅
     * @param rssSubscriptions
     * @return
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateRssSubscription(RssSubscriptions rssSubscriptions) throws UpdateOperationsException {
        try{

            if(rssSubscriptions==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(rssSubscriptions.getId()==null){
                throw new IdNotFoundException(MessageConstant.ID_NOT_FOUND);
            }

            rssSubscriptionMapper.updateRssSubscription(rssSubscriptions);
            return Result.success("更新成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 批量删除文章
     * @param ids
     * @return
     * @throws BatchDeleteRssSubscriptionException
     */
    @Override
    public Result batchDeleteRssSubscription(List<Long> ids) throws BatchDeleteRssSubscriptionException {
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
                if(rssSubscriptionMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除订阅
            if(!updatedIds.isEmpty()){
                rssSubscriptionMapper.batchDeleteRssSubscriptions(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量审核成功，共删除 " + updatedIds.size() + " 条订阅"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteRssSubscriptionException(MessageConstant.BATCH_DELETE_RSSSUBSCRIPTIONS_FAILSURE);
        }
    }

    /**
     * 添加订阅
     * @param rssSubscriptionDTO
     * @return
     * @throws AddOperationException
     */
    @Transactional
    @Override
    public Result addRssSubscription(RssSubscriptionDTO rssSubscriptionDTO) throws AddOperationException {
        try{

            if(rssSubscriptionDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(rssSubscriptionMapper.findByVisitorId(rssSubscriptionDTO.getVisitorId())!=null){
                return Result.error("添加失败");
            }

            rssSubscriptionMapper.addRssSubscription(rssSubscriptionDTO);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }


}
