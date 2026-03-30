package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.VisitorMapper;
import com.xixizai.personalblogwebsite.pojo.entity.Visitors;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    @Resource
    private VisitorMapper visitorMapper;

    /**
     * 批量封禁访客
     * @param ids
     * @return
     * @throws BatchBlockVisitorsException
     */
    @Override
    public Result batchBlockVisitors(List<Long> ids) throws BatchBlockVisitorsException {
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
                if(visitorMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除订阅
            if(!updatedIds.isEmpty()){
                visitorMapper.batchBlockVisitors(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量封禁成功，共封禁 " + updatedIds.size() + " 个游客"));
            }
            return Result.success("批量封禁成功，成功封禁 " + updatedIds.size() + " 个游客，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchBlockVisitorsException(MessageConstant.BATCH_BLOCK_VISITORS_FAILSURE);
        }
    }

    /**
     * 批量解封访客
     * @param ids
     * @return
     * @throws BatchUnblockVisitorsException
     */
    @Override
    public Result batchUnblockVisitors(List<Long> ids) throws BatchUnblockVisitorsException {
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
                if(visitorMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量解封访客
            if(!updatedIds.isEmpty()){
                visitorMapper.batchUnblockVisitors(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量封禁成功，共解封 " + updatedIds.size() + " 个游客"));
            }
            return Result.success("批量封禁成功，成功解封 " + updatedIds.size() + " 个游客，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchUnblockVisitorsException(MessageConstant.BATCH_UNBLOCK_VISITORS_FAILSURE);
        }
    }

    /**
     * 添加访客
     * @param visitors
     * @param request
     * @throws AddOperationException
     */
    @Override
    public void addVisitors(Visitors visitors, HttpServletRequest request) throws AddOperationException {
        try{

            if(visitors==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            visitorMapper.addVisitors(visitors);
            log.info("添加访客成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }



}
