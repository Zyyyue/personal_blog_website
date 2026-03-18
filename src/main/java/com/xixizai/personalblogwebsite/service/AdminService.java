package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.pojo.dto.*;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.AdminLoginVO;
import com.xixizai.personalblogwebsite.pojo.vo.AdminVO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    //发送管理端验证码
    void sendVerifyCode(String username) throws AccountNotFoundException, VisitorSendCodeException, VerifyCodeCoolDownException, EmailSendErrorException;
    //登录
    Result<AdminLoginVO> login(AdminLoginDTO adminLoginDTO) throws AccountNotFoundException, PasswordErrorException, VerifyCodeLockException, VerifyCodeErrorException;
    //获取管理员信息
    Result<AdminVO> getAdminInfor() throws FindNoAdminByIdException, FindNoAdminByIdException;
    //修改管理员密码
    Result changeAdminPassword(AdminChangePasswordDTO adminChangePasswordDTO) throws PasswordNotNullException, PasswordErrorException, UpdateAdminPasswordException;
    //修改昵称
    Result changeAdminNickname(AdminChangeNicknameDTO adminChangeNicknameDTO) throws UpdateAdminNicknameException;
    //换绑邮箱
    Result changeAdminEmail(AdminChangeEmailDTO adminChangeEmailDTO) throws AccountNotFoundException, VerifyCodeLockException, VerifyCodeErrorException, UpdateAdminEmailException;
    //退出登录
    Result logout(String token) throws LogoutFailsureException;
}
