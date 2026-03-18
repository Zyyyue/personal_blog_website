package com.xixizai.personalblogwebsite.controller.admin;

import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.pojo.dto.*;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.AdminLoginVO;
import com.xixizai.personalblogwebsite.pojo.vo.AdminVO;
import com.xixizai.personalblogwebsite.pojo.vo.ArticleVO;
import com.xixizai.personalblogwebsite.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 管理端用户接口
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 客户端发送验证码
     * @param sendCodeDTO
     * @return
     */
    @PostMapping ("/admin/sendCode")
    public Result sendCode(@RequestBody SendCodeDTO sendCodeDTO) throws VisitorSendCodeException, AccountNotFoundException, VerifyCodeCoolDownException, EmailSendErrorException {
    String username=sendCodeDTO.getUsername();
     adminService.sendVerifyCode(username);
     return Result.success("发送成功");
    }

    /**
     * 登录
     * @param adminLoginDTO
     * @return
     * @throws VerifyCodeLockException
     * @throws PasswordErrorException
     * @throws VerifyCodeErrorException
     * @throws AccountNotFoundException
     */
    @PostMapping("/admin/login")
    public Result<AdminLoginVO>login(@RequestBody @Valid AdminLoginDTO adminLoginDTO) throws VerifyCodeLockException, PasswordErrorException, VerifyCodeErrorException, AccountNotFoundException {
        return adminService.login(adminLoginDTO);
    }

    /**
     * 获取管理员信息
     * @return
     * @throws FindNoAdminByIdException
     */
    @GetMapping("/admin")
    public Result<AdminVO>getAdminInfor() throws FindNoAdminByIdException {
        return adminService.getAdminInfor();
    }

    /**
     * 修改管理员密码
     * @param adminChangePasswordDTO
     * @return
     * @throws PasswordNotNullException
     * @throws UpdateAdminPasswordException
     * @throws PasswordErrorException
     */
    @PutMapping("/admin/changePassword")
    public Result changeAdminPassword(@RequestBody AdminChangePasswordDTO adminChangePasswordDTO) throws PasswordNotNullException, UpdateAdminPasswordException, PasswordErrorException {
        return adminService.changeAdminPassword(adminChangePasswordDTO);
    }

    /**
     * 修改管理员昵称
     * @param adminChangeNicknameDTO
     * @return
     */
    @PutMapping("/admin/changeNickname")
    public Result changeAdminNickname(@RequestBody @Valid AdminChangeNicknameDTO adminChangeNicknameDTO) throws UpdateAdminNicknameException {
        return adminService.changeAdminNickname(adminChangeNicknameDTO);
    }

    /**
     * 换绑管理员邮箱
     * @param adminChangeEmailDTO
     * @return
     * @throws VerifyCodeLockException
     * @throws AccountNotFoundException
     * @throws VerifyCodeErrorException
     * @throws UpdateAdminEmailException
     */
    @PutMapping("/admin/changeEmail")
    public Result changeAdminEmail(@RequestBody @Valid AdminChangeEmailDTO adminChangeEmailDTO) throws VerifyCodeLockException, AccountNotFoundException, VerifyCodeErrorException, UpdateAdminEmailException {
        return adminService.changeAdminEmail(adminChangeEmailDTO);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/admin/logout")
    public Result logout(@RequestHeader("Authorization") String token) throws LogoutFailsureException {
        return adminService.logout(token);
    }






}
