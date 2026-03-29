package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.BatchDeleteOperationLogsException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteRssSubscriptionException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.OperationLogMapper;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.OperationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    /**
     * 批量删除操作日志
     * @param ids
     * @return
     * @throws BatchDeleteOperationLogsException
     */
    @Override
    public Result batchDeleteOperationLogs(List<Long> ids) throws BatchDeleteOperationLogsException {
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
                if(operationLogMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除操作日志
            if(!updatedIds.isEmpty()){
                operationLogMapper.batchDeleteOperationLogs(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 条操作日志"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteOperationLogsException(MessageConstant.BATCH_DELETE_OPERATIONLOGS_FAILSURE);
        }
    }
}
