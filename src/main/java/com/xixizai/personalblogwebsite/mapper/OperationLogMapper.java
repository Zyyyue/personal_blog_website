package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.OperationLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface OperationLogMapper {

    //根据id查找操作日志
    @Select("select * from  operation_logs where id=#{id}")
    OperationLogs findById(Long id);

    //批量删除操作日志
    void batchDeleteOperationLogs(List<Long> ids);
}
