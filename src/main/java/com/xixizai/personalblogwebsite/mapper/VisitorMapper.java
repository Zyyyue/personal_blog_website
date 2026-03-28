package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Visitors;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.javassist.compiler.ast.Visitor;

import java.util.List;

@Mapper
public interface VisitorMapper {
    
    //根据id查找
    @Select("select * from visitors where id=#{id}")
    Visitors findById(Long id);

    //批量封禁游客
    void batchBlockVisitors(List<Long> ids);
}
