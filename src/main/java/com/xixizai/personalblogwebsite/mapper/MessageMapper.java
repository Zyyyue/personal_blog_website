package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Messages;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Insert("insert into messages (parent_id,root_id,parent_nickname,content,content_html,is_markdown,is_admin_reply,is_edited,is_approved,update_time) values (#{parentId},#{rootId},#{parentNickname},#{content},#{contentHtml},#{isMarkdown},#{isAdminReply},#{isEdited},#{isApproved},now()) ")
    void adminReplyMessage(Messages messages);
}
