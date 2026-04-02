package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.VisitorMapper;
import com.xixizai.personalblogwebsite.pojo.dto.VisitorRecordDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Views;
import com.xixizai.personalblogwebsite.pojo.entity.Visitors;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.VisitorRecordVO;
import com.xixizai.personalblogwebsite.service.ViewService;
import com.xixizai.personalblogwebsite.service.VisitorService;
import com.xixizai.personalblogwebsite.utils.FingerprintGeneratorUtil;
import com.xixizai.personalblogwebsite.utils.IpUtil;
import com.xixizai.personalblogwebsite.utils.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    @Resource
    private VisitorMapper visitorMapper;

    @Resource
    private FingerprintGeneratorUtil fingerprintGeneratorUtil;

    @Resource
    @Lazy
    //打破bean相互循环
    private ViewService viewService;

    @Resource
    private IpUtil ipUtil;

    @Resource
    private UserAgentUtil agentUtil;

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
     * 管理端添加访客
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

            String fingerprint = fingerprintGeneratorUtil.generateSimple(request);

            // 根据指纹查询是否存在
            Visitors existingVisitor = visitorMapper.findByFingerprint(fingerprint);

            if (existingVisitor != null) {
                // 如果已存在，更新访问次数,更新时间
                existingVisitor.setFingerprint(fingerprint);
                existingVisitor.setTotalViews(existingVisitor.getTotalViews() + 1);
                visitorMapper.updateVisitor(existingVisitor);
                visitors.setId(existingVisitor.getId());
                return;
            }

            visitors.setFingerprint(fingerprint);
            visitorMapper.addVisitors(visitors);
            log.info("添加访客成功，id={}", visitors.getId());
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
        }
    }

    /**
     * 管理端根据request查询id
     * @param request
     * @return
     * @throws GetOptsException
     */
    @Override
    public Long getVisitorIdByRequest(HttpServletRequest request) throws GetOptsException {
        try{
            
            if(request==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            String fingerprint = fingerprintGeneratorUtil.generateSimple(request);
            Visitors visitor = visitorMapper.findByFingerprint(fingerprint);
            if(visitor!=null&&visitor.getId()!=null){
                return visitor.getId();
            }
            return null;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 博客端记录访客信息
     * @param visitorRecordDTO
     * @param request
     * @return
     * @throws AddOperationException
     */
    @Transactional
    @Override
    public Result recordVisitorInfor(VisitorRecordDTO visitorRecordDTO,HttpServletRequest request) throws AddOperationException {
       try{

           if(visitorRecordDTO==null){
               throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
           }

           String fingerprint = fingerprintGeneratorUtil.generateSimple(request);
           Visitors visitors=new Visitors();
           visitors.setFingerprint(fingerprint);

           addVisitors(visitors,request);
           Views views= Views.builder()
                   .pagePath(visitorRecordDTO.getPagePath())
                   .pageTitle(visitorRecordDTO.getPageTitle())
                   .referer(visitorRecordDTO.getReferer())
                   .build();
           log.info("创建的 Views 对象: pagePath={}", views.getPagePath());
           viewService.addViewRecord(views,request);
           VisitorRecordVO visitorRecordVO = new VisitorRecordVO();


           //生成Fingerprint
           String fingerprintNow = FingerprintGeneratorUtil.generateSimple(request);
           HttpSession session = request.getSession();
           //生成sessionId
           String sessionId=session.getId();
           //获取visitorId
           Visitors byFingerprint = visitorMapper.findByFingerprint(fingerprintNow);
           visitorRecordVO=visitorRecordVO.builder()
                   .sessionId(sessionId)
                   .visitorId(byFingerprint.getId())
                   .visitorFingerprint(fingerprintNow)
                   .build();

           //判断是否是新访客
           LocalDateTime createTime = byFingerprint.getCreateTime();
           LocalDateTime updateTime = byFingerprint.getUpdateTime();
           if(updateTime.isAfter(createTime)){
               visitorRecordVO.setIsNewVisitor(true);
           }else{
               visitorRecordVO.setIsNewVisitor(false);
           }

           return Result.success(visitorRecordVO);
       }catch (Exception exception){

           exception.printStackTrace();
           throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);


       }


    }


}
