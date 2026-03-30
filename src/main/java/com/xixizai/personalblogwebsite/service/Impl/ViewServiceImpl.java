package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.AddOperationException;
import com.xixizai.personalblogwebsite.exception.BatchDeleteViewRecordsException;
import com.xixizai.personalblogwebsite.exception.BatchUnblockVisitorsException;
import com.xixizai.personalblogwebsite.exception.PassedParameterException;
import com.xixizai.personalblogwebsite.mapper.ViewMapper;
import com.xixizai.personalblogwebsite.pojo.dto.ArticleDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Views;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.ViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {


    @Resource
    private ViewMapper viewMapper;

    /**
     * 批量删除浏览记录
     * @param ids
     * @return
     * @throws BatchDeleteViewRecordsException
     */
    @Override
    public Result batchDeleteViewRecords(List<Long> ids) throws BatchDeleteViewRecordsException {
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
                if(viewMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量删除浏览记录
            if(!updatedIds.isEmpty()){
                viewMapper.batchDeleteViewRecords(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除浏览记录成功，共浏览记录 " + updatedIds.size() + " 个浏览纪录"));
            }
            return Result.success("批量删除浏览记录成功，成功删除 " + updatedIds.size() + " 个浏览纪录，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteViewRecordsException(MessageConstant.BATCH_DELETE_VIEW_RECORDS_FAILSURE);
        }
    }

    /**
     * 添加浏览记录
     * @param views
     * @throws AddOperationException
     */
    @Override
    public void addViewRecord(Views views) throws AddOperationException {
        try{

            //如果传进来的参数为空添加失败
            if(views==null){
                throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
            }

            viewMapper.addViewRecord(views);


        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }


}
