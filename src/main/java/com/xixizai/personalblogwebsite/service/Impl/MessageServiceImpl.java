package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.constant.StatusConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.MessageMapper;
import com.xixizai.personalblogwebsite.pojo.dto.MessageDTO;
import com.xixizai.personalblogwebsite.pojo.dto.MessageEditDTO;
import com.xixizai.personalblogwebsite.pojo.dto.MessageReplyDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Messages;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.MessageVO;
import com.xixizai.personalblogwebsite.service.MessageService;
import com.xixizai.personalblogwebsite.utils.IpUtil;
import com.xixizai.personalblogwebsite.utils.MarkdownUtil;
import com.xixizai.personalblogwebsite.utils.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {


    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserAgentUtil userAgentUtil;

    /**
     * 批量审核留言
     * @param ids
     * @return
     * @throws BatchApproveMessageException
     */
    @Override
    public Result batchApproveMessages(List<Long> ids) throws BatchApproveMessageException {
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
                if(messageMapper.findMessagesById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量审核文章评论
            if(!updatedIds.isEmpty()){
                messageMapper.batchApproveMessages(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量审核成功，共审核 " + updatedIds.size() + " 条留言"));
            }
            return Result.success("批量审核成功，成功审核 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchApproveMessageException(MessageConstant.BATCH_APPROVE_MESSAGE_FAILSURE);
        }
    }

    /**
     * 批量删除留言
     * @param ids
     * @return
     * @throws BatchDeleteMessageException
     */
    @Transactional
    @Override
    public Result batchDeleteMessages(List<Long> ids) throws BatchDeleteMessageException {
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
                if(messageMapper.findMessagesById(id)==null){
                    nulledIds.add(id);
                }else{
                    updatedIds.add(id);
                }
            }

            //批量审核文章评论
            if(!updatedIds.isEmpty()){
                messageMapper.batchDeleteMessages(ids);
            }

            //返回结果
            if(updatedIds.isEmpty()){
                return Result.error("传入的ID列表中，没有任何一个存在：" +nulledIds);
            }
            if(nulledIds.isEmpty()){
                return Result.success(("批量审核成功，共审核 " + updatedIds.size() + " 条留言"));
            }
            return Result.success("批量审核成功，成功审核 " + updatedIds.size() + " 条，"
                    + "不存在的ID：" + nulledIds);

        }catch (Exception exception){
            exception.printStackTrace();
            throw new BatchDeleteMessageException(MessageConstant.BATCH_DELETE_MESSAGE_FAILSURE);
        }
    }

    /**
     * 用户回复留言
     * @param messageReplyDTO
     * @param request
     * @return
     * @throws AdminReplyMessageException
     */
    @Override
    public Result adminReplyMessage(MessageReplyDTO messageReplyDTO, HttpServletRequest request) throws AdminReplyMessageException {
        try{

            //判空一下
            if(messageReplyDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(messageReplyDTO.getParentId()==null){
                throw new PassedParameterException("父留言id不能为空");
            }

            if(messageReplyDTO.getContent()==null||messageReplyDTO.getContent().trim()==null){
                throw new PassedParameterException("回复内容不能为空");
            }

            Messages messages = BeanUtil.toBean(messageReplyDTO, Messages.class);

            //设置管理员回复相关字段
            messages.setIsApproved(StatusConstant.ENABLE);
            messages.setIsEdited(StatusConstant.ENABLE);
            messages.setIsAdminReply(StatusConstant.ENABLE);

            //再处理一下markdown
            if(messageReplyDTO.getIsMarkdown()!=null&&messageReplyDTO.getIsMarkdown()==1){
                //如果是markdown
                String html=MarkdownUtil.toHtml(messageReplyDTO.getContent());
                messages.setContentHtml(html);
            }else{
                messages.setContentHtml(messageReplyDTO.getContent());
            }

            //获取客户端信息
            if(request!=null){
                String clientIp = IpUtil.getClientIp(request);

                //获取地理位置信息
                Map<String, String> geoInfo = IpUtil.getGeoInfo(clientIp);
                    //获取省
                String province = geoInfo.get("province");
                    //获取城市
                String city = geoInfo.get("city");

                //构建字符串
                String location="";
                if(!province.isEmpty()){
                    location=province;
                    if(!city.isEmpty()&&!city.equals(province)){
                        location+="-"+city;
                    }
                }

                if(location.isEmpty()){
                    messages.setLocation(location);
                }

                log.info("管理员回复-IP:{},位置:{}",clientIp,location);
            }
            messageMapper.adminReplyMessage(messages);
            return Result.success("管理员回复成功");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new AdminReplyMessageException(MessageConstant.REPLY_MESSAGE_FAILSURE);
        }

    }


    /**
     * 提交留言
     * @param messageDTO
     * @return
     * @throws AddOperationException
     */
    @Transactional
    @Override
    public Result submitMessage(MessageDTO messageDTO,HttpServletRequest request) throws AddOperationException {
       try{

           if(messageDTO==null){
               throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
           }
            Messages messages=new Messages();
           //判断是否是markdown,然后处理contentHtml
           Integer isMarkdown = messageDTO.getIsMarkdown();
           String contentHtml = messages.getContentHtml();
           if(MarkdownUtil.isHtml(messageDTO.getContent())){
                isMarkdown=0;
                contentHtml=messageDTO.getContent();
            }else{
                isMarkdown=1;
                contentHtml=MarkdownUtil.toHtml(messageDTO.getContent());
            }
            //获取ip地址操作系统和使用的浏览器
           String clientIp = IpUtil.getClientIp(request);
           if(IpUtil.isLocalIp(clientIp)){
               clientIp=IpUtil.getLocalHostIp();
           }
           //获取地址
           Map<String, String> geoInfo = IpUtil.getGeoInfo(clientIp);
           String country = geoInfo.get("country");
           String city = geoInfo.get("city");
           String location=country+"-"+city;
           //获取操作系统和浏览器
           String userAgentString = request.getHeader("User-Agent");
           String browserName = userAgentUtil.getBrowserName(userAgentString);
           String osName = userAgentUtil.getOsName(userAgentString);


           messages=messages.builder()
                    .content(messageDTO.getContent())
                    .contentHtml(contentHtml)
                    .isMarkdown(isMarkdown)
                    .rootId(messageDTO.getRootId())
                    .parentId(messageDTO.getParentId())
                    .parentNickname(messageDTO.getParentNickname())
                    .visitorId(messageDTO.getVisitorId())
                    .nickname(messageDTO.getNickname())
                    .emailOrQq(messageDTO.getEmailOrQq())
                    .isSecret(messageDTO.getIsSecret())
                    .isNotice(messageDTO.getIsNotice())
                   .location(location)
                   .userAgentBrowser(browserName)
                   .userAgentOs(osName)
                            .build();
            messageMapper.submitMessage(messages);
            return Result.success("提交成功");
       }catch (Exception exception){
        exception.printStackTrace();
        throw new AddOperationException(MessageConstant.ADD_OPERATION_FAILSURE);
       }

    }

    /**
     * 获取留言列表
     * @param visitorId
     * @return
     * @throws GetOptsException
     */
    @Override
    public Result getMessagesList(Long visitorId) throws GetOptsException {
        try{

            if(visitorId==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }

            if(visitorId<=0){
                throw new PassedParameterException(MessageConstant.ID_NOT_VALID);
            }

            List<MessageVO>list=messageMapper.getMessagesList(visitorId);

            //构造树结构
            List<MessageVO>rootMessages=new ArrayList<>();
            Map<Long,MessageVO>messageMap=list.stream()
                    .collect(Collectors.toMap(MessageVO::getId,m->m));

            for(MessageVO msg:list){
                if (msg.getRootId() == null || msg.getRootId() == 0) {
                    msg.setChildren(new ArrayList<>());
                    rootMessages.add(msg);
                } else {
                    MessageVO rootMsg = messageMap.get(msg.getRootId());
                    if (rootMsg != null) {
                        if (rootMsg.getChildren() == null) {
                            rootMsg.setChildren(new ArrayList<>());
                        }
                        rootMsg.getChildren().add(msg);
                    }
                }
            }
            return Result.success(rootMessages);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new GetOptsException(MessageConstant.GET_OPERATIONS_FAILSURE);
        }
    }

    /**
     * 编辑留言
     * @param messageEditDTO
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Result editMessage(MessageEditDTO messageEditDTO) throws Exception {
        try{

            if(messageEditDTO==null){
                throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
            }
            Messages messagesById = messageMapper.findMessagesById(messageEditDTO.getId());
            Integer isMarkdown = messagesById.getIsMarkdown();
            Messages messages=new Messages();
            String html=new String();
            //处理content,contentHtml
            if(MarkdownUtil.isHtml(messageEditDTO.getContent())){
                html=messageEditDTO.getContent();
                isMarkdown=0;
            }else{
                 html = MarkdownUtil.toHtml(messageEditDTO.getContent());
                messages.setContentHtml(html);
                isMarkdown=1;
            }

            messages=messages.builder()
                    .isMarkdown(isMarkdown)
                    .content(messageEditDTO.getContent())
                    .visitorId(messageEditDTO.getVisitorId())
                    .id(messageEditDTO.getId())
                    .contentHtml(html)
                            .build();

            messageMapper.editMessge(messages);
            return Result.success("编辑成功");
        }catch(Exception exception){
            exception.printStackTrace();
            throw new Exception(MessageConstant.UPDATE_OPERATIONS_FAILSURE);
        }

    }

    /**
     * 删除留言
     * @param id
     * @param visitorId
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Result deleteMessage(Long id, Long visitorId) throws Exception {
       try{

           if(id==null||visitorId==null){
               throw new PassedParameterException(MessageConstant.PASSED_PARAMETER_NOT_NULL);
           }

           if(id<=0||visitorId<=0){
               throw new IdNotValidException(MessageConstant.ID_NOT_VALID);
           }
            Messages messages=messageMapper.findMessagesById(id);
            //如果是根留言删除连结所有子留言
            if(messages.getRootId()==null||messages.getRootId()==0){
              Integer childCount= messageMapper.countByRootId(id);
              if(childCount!=null&&childCount>0){
                messageMapper.deleteByRootId(id);
              }
            }
            messageMapper.deleteById(id);
            return Result.success("删除成功");
       }catch (Exception exception){

           exception.printStackTrace();
           throw new Exception("删除留言失败");

       }
    }
}
