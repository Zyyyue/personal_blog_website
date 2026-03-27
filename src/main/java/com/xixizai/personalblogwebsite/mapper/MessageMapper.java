package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Messages;
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
}
