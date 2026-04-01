package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.MessageDTO;
import com.xixizai.personalblogwebsite.pojo.dto.MessageEditDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Messages;
import com.xixizai.personalblogwebsite.pojo.vo.MessageVO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface MessageMapper {
    //根据id查找message
    @Select("select * from messages where id =#{id}")
    Messages findMessagesById(Long id);

    //批量审核留言
    void batchApproveMessages(List<Long> ids);

    //批量删除留言
    void batchDeleteMessages(List<Long> ids);

    //回复留言
    @Insert("insert into messages (parent_id,root_id,parent_nickname,content,content_html,is_markdown,is_admin_reply,is_edited,is_approved,update_time) values (#{parentId},#{rootId},#{parentNickname},#{content},#{contentHtml},#{isMarkdown},#{isAdminReply},#{isEdited},#{isApproved},now()) ")
    void adminReplyMessage(Messages messages);

    //添加留言
    @Insert("insert into messages (content, content_html, root_id, parent_id, parent_nickname, visitor_id, nickname, email_or_qq, location, user_agent_os, user_agent_browser, create_time, update_time,is_markdown,is_secret,is_notice) values (#{content},#{contentHtml},#{rootId},#{parentId},#{parentNickname},#{visitorId},#{nickname},#{emailOrQq},#{location},#{userAgentOs},#{userAgentBrowser},now(),now(),#{isMarkdown},#{isSecret},#{isNotice})")
    void submitMessage(Messages messages);

    //获取留言列表
    List<MessageVO> getMessagesList(Long visitorId);

    //编辑留言
    @Update("update messages set is_markdown=#{isMarkdown},visitor_id=#{visitorId},content=#{content},content_html=#{contentHtml},update_time =now()  where id=#{id}")
    void editMessge(Messages messages);

    //统计根目录下的子留言数量
    @Select("select  count(*) from messages where root_id=#{id}")
    Integer countByRootId(Long id);

    //删除根留言id下的所有子留言
    @Delete("delete from messages where root_id=#{id}")
    void deleteByRootId(Long id);

    //删除该留言
    @Delete("delete from messages where id=#{id}")
    void deleteById(Long id);
}
