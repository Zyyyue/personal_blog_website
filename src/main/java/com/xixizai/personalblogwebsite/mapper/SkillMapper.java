package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.dto.SkillDTO;
import com.xixizai.personalblogwebsite.pojo.entity.Skills;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SkillMapper {
    //获取所有技能
    @Select("select * from skills")
    List<Skills> getAllSkills();

    //添加技能
    @Insert("insert into skills (name,description,icon,sort,is_visible,create_time,update_time) values (#{name},#{description},#{icon},#{sort},#{isVisible},now(),now())")
    void addSkill(SkillDTO skillDTO);

    //根据id查找技能
    @Select("select * from skills where id=#{id}")
    Skills findById(Long id);

    //更新技能
    @Update("update skills set name=#{name},description=#{description},icon=#{icon},sort=#{sort},is_visible=#{isVisible},update_time=now() where id=#{id}")
    void updateSkill(SkillDTO skillDTO);

    //批量删除技能
    void batchDeleteSkills(List<Long> ids);
}
