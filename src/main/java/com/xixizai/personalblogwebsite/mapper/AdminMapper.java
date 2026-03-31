package com.xixizai.personalblogwebsite.mapper;

import com.xixizai.personalblogwebsite.pojo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    /**
     * 根据姓名查找admin的所有信息
     * @param username
     * @return
     */
    @Select("select * from admin where username=#{username}")
    Admin getByUsername(String username);

    /**
     * 根据id查找admin的所有信息
     * @param adminId
     * @return
     */
    @Select("select * from admin where id=#{adminId}")
    Admin findById(Long adminId);


    /**
     * 修改admin的密码
     * @param newPassword
     */
    @Update("update admin set password=#{newPassword} where id=#{adminId}")
    void updatePassword(String newPassword,Long adminId);

    /**
     * 修改admin昵称
     * @param nickname
     */
    @Update("update admin set nickname=#{nickname} where id=#{adminId}")
    void updateNickName(String nickname,Long adminId);

    /**
     * 修改管理员信息
     * @param admin
     */

    void update(Admin admin);

}
