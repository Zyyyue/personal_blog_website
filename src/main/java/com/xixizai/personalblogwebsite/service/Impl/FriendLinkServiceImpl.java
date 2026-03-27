package com.xixizai.personalblogwebsite.service.Impl;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.FriendLinkMapper;
import com.xixizai.personalblogwebsite.pojo.dto.FriendLinkDTO;
import com.xixizai.personalblogwebsite.pojo.entity.FriendLinks;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.service.FriendLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {


    @Resource
    private FriendLinkMapper friendLinkMapper;

    /**
     * 获取所有友链
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getAllFriendLinks() throws GetOptsException {

        try{
            List<FriendLinks> list=friendLinkMapper.getAllFriendLinks();
            if(list==null||list.isEmpty()){
                throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
            }
            return Result.success(list);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 添加友链
     * @param friendLinkDTO
     * @return
     * @throws AddOperationException
     */
    @Override
    public Result addFriendLink(FriendLinkDTO friendLinkDTO) throws AddOperationException {
        try{

            if(friendLinkDTO==null){
                return Result.error("添加失败");
            }

            friendLinkMapper.addFriendLink(friendLinkDTO);
            return Result.success("添加成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }

    /**
     * 更改友链信息
     * @param friendLinkDTO
     * @return
     * @throws PassedParameterException
     * @throws UpdateOperationsException
     */
    @Override
    public Result updateFriendLink(FriendLinkDTO friendLinkDTO) throws PassedParameterException, UpdateOperationsException {
        try{
            //判空
            if(friendLinkDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(friendLinkDTO.getId()==null){
                throw new PassedParameterException(MessageConstant.ID_NOT_FOUND);
            }

            //再在数据库中查找一下对应id中的数据是否在数据库中存在
            if(friendLinkMapper.findById(friendLinkDTO.getId())==null){
                return Result.error("没有对应id的数据");
            }

            friendLinkMapper.updateFriendLink(friendLinkDTO);
            return Result.success("更新操作成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new UpdateOperationsException(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 批量删除友链
     * @param ids
     * @return
     * @throws BatchDeleteFriendLinksException
     */
    @Override
    public Result batchDeleteFriendLinks(List<Long> ids) throws BatchDeleteFriendLinksException {
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
                if(friendLinkMapper.findById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量审核文章评论
            if(!updatedIds.isEmpty()){
                friendLinkMapper.batchDeleteFriendLinks(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量删除成功，共删除 " + updatedIds.size() + " 条友链/"));
            }
            return Result.success("批量删除成功，成功删除 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteFriendLinksException(MessageConstant.BATCH_DELETE_FRIEND_LINKS_FAILSURE);
        }
    }
}
