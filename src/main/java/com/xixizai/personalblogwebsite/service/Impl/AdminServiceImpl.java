package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.constant.StatusConstant;
import com.xixizai.personalblogwebsite.exception.*;
import com.xixizai.personalblogwebsite.mapper.AdminMapper;
import com.xixizai.personalblogwebsite.pojo.dto.*;
import com.xixizai.personalblogwebsite.pojo.entity.Admin;
import com.xixizai.personalblogwebsite.pojo.result.Result;
import com.xixizai.personalblogwebsite.pojo.vo.AdminLoginVO;
import com.xixizai.personalblogwebsite.pojo.vo.AdminVO;
import com.xixizai.personalblogwebsite.properties.VisitorProperties;
import com.xixizai.personalblogwebsite.service.AdminService;
import com.xixizai.personalblogwebsite.service.EmailService;
import com.xixizai.personalblogwebsite.service.TokenService;
import com.xixizai.personalblogwebsite.service.VerifyCodeService;
import com.xixizai.personalblogwebsite.utils.Md5Util;
import com.xixizai.personalblogwebsite.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Map;

import static com.xixizai.personalblogwebsite.constant.MessageConstant.ACCOUNT_NOT_FOUND;
import static com.xixizai.personalblogwebsite.constant.MessageConstant.VISITOR_NOT_SEND_VERIFYCODE;
import static com.xixizai.personalblogwebsite.constant.StatusConstant.DISABLE;
import static com.xixizai.personalblogwebsite.constant.StatusConstant.ENABLE;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private EmailService emailService;

    @Resource
    private VisitorProperties visitorProperties;

    @Resource
    private TokenService tokenService;
    /**
     * 发送管理端验证码
     * @param username
     */
    @Override
    public void sendVerifyCode(String username) throws AccountNotFoundException, VisitorSendCodeException, VerifyCodeCoolDownException, EmailSendErrorException {
//        1.验证用户是否存在通过adminMapper.getByUsername()查询
            Admin admin=adminMapper.getByUsername(username);
            AdminVO adminVO= BeanUtil.toBean(admin,AdminVO.class);
            if(adminVO==null){
                //账号未发现
                log.info("账号未发现");
                throw new AccountNotFoundException(ACCOUNT_NOT_FOUND);
            }
//        2.检查用户状态游客账户（DISABLE==0）不能发送邮箱验证码
            if(admin.getRole()==DISABLE){
                throw new VisitorSendCodeException(VISITOR_NOT_SEND_VERIFYCODE);
            }
//        3. 检查发送频率 - 调用 `verifyCodeService.canSendCode()` 检查冷却时间
            if(verifyCodeService.canSendCode(admin)){
                //如果验证码还在冷却时间,可以生成验证码
                //4.生成验证码 - 调用 `verifyCodeService.generateCode()` 生成 6 位数字
                String verifyCode=verifyCodeService.generateCode();
                //5. 保存验证码 - 存储到 Redis，有效期 5 分钟
                verifyCodeService.saveCode(verifyCode,admin);
                log.info("验证码为:{}，已保存到 Redis，adminId={}, username={}", verifyCode, admin.getId(), admin.getUsername());
//        6. 发送邮件 - 调用 `emailService.sendVerifyCode()`
                emailService.sendVerifyCode(admin.getEmail(),verifyCode);

            }else {
                Long time=verifyCodeService.getRemainingCooldown(admin);
                throw new VerifyCodeCoolDownException("验证码冷却中，请等待"+time+"秒!");
            }

            
    }

    /**
     * 登录
     * @param adminLoginDTO
     * @return
     * @throws AccountNotFoundException
     * @throws PasswordErrorException
     * @throws VerifyCodeLockException
     * @throws VerifyCodeErrorException
     */
    @Override
    public Result<AdminLoginVO> login(AdminLoginDTO adminLoginDTO) throws AccountNotFoundException, PasswordErrorException, VerifyCodeLockException, VerifyCodeErrorException {
        Admin adminGetByUsername = adminMapper.getByUsername(adminLoginDTO.getUsername());
        log.info("查询到的管理员信息：id={}, username={}, role={}", adminGetByUsername != null ? adminGetByUsername.getId() : null, adminGetByUsername != null ? adminGetByUsername.getUsername() : null, adminGetByUsername != null ? adminGetByUsername.getRole() : null);
        //如果账号不存在，抛出异常AccountNotFoundException(ACCOUNT_NOT_FOUND)
        if (BeanUtil.isEmpty(adminGetByUsername)){
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND);
        }

        //如果密码不相同,抛出异常
        if(!Md5Util.checkPassword(adminLoginDTO.getPassword(),adminGetByUsername.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //区分管理员和游客
        if(adminGetByUsername.getRole()== ENABLE){
            //如果是管理员,管理员需要校验邮箱验证码
            //先检查验证码是否被锁定（错误次数过多）
            Long lockRemainingSeconds = verifyCodeService.getLockRemainingSeconds(adminGetByUsername);
            if(lockRemainingSeconds > 0){
                throw new VerifyCodeLockException(MessageConstant.VERIFY_CODE_LOCK+lockRemainingSeconds+"秒钟");
            }

            //校验验证码是否正确
            boolean isValid=verifyCodeService.verifyCode(adminLoginDTO.getCode(),adminGetByUsername);

            if(!isValid){
                Long remainingAttempts = verifyCodeService.getRemainingAttempts(adminGetByUsername);
                throw new VerifyCodeErrorException(MessageConstant.VERIFY_CODE_ERROR+",还可以试"+remainingAttempts+"次");
            }

        }else{
            //游客校验使用固定验证码
            if(!adminLoginDTO.getCode().equals(visitorProperties.getVerifyCode())){
                throw new VerifyCodeErrorException(MessageConstant.VERIFY_CODE_ERROR+",请输入:"+visitorProperties.getVerifyCode());
            }

        }
        String token=tokenService.createAndStoreToken(Long.valueOf(adminGetByUsername.getId()),adminGetByUsername.getRole());
        AdminLoginVO adminLoginVO=new AdminLoginVO();
        adminLoginVO.setId(adminGetByUsername.getId());
        adminLoginVO.setToken(token);
        return Result.success(adminLoginVO);
    }

    /**
     * 获取管理员信息
     * @return
     */
    @Override
    public Result<AdminVO> getAdminInfor() throws FindNoAdminByIdException {
        try{
            Map<String,Object> map = ThreadLocalUtil.get();
            Long adminId= (Long) map.get("adminId");
            Admin adminFindById=adminMapper.findById(adminId);
            AdminVO adminVO = BeanUtil.toBean(adminFindById, AdminVO.class);
            return Result.success(adminVO);
        }catch (Exception e){
            throw new FindNoAdminByIdException(MessageConstant.FIND_NO_ADMININFORMATION_BY_ID);
        }
    }

    /**
     *修改管理员密码
     * @return
     */
    @Override
    public Result changeAdminPassword(AdminChangePasswordDTO adminChangePasswordDTO) throws PasswordNotNullException, PasswordErrorException, UpdateAdminPasswordException {
        //DTO里面的旧密码
        String oldPasswordFromadminChangePasswordDTO = adminChangePasswordDTO.getOldPassword();
        //新密码
        String newPassword = adminChangePasswordDTO.getNewPassword();
        //确认密码
        String confirmNewPassword = adminChangePasswordDTO.getConfirmNewPassword();

        //1.首先先判断三个来自AdminChangePasswordDTO的密码是否为空


        //1.1如果DTO里面的旧密码为空
        if(StrUtil.isBlank(oldPasswordFromadminChangePasswordDTO)){
            throw new PasswordNotNullException(MessageConstant.PASSWORD_NOT_NULL);
        }

        //1.2如果DTO里面的新密码为空
        if(StrUtil.isBlank(newPassword)){
            throw new PasswordNotNullException(MessageConstant.PASSWORD_NOT_NULL);
        }

        //1.3如果DTO里面的确认密码为空
        if(StrUtil.isBlank(confirmNewPassword)){
            throw new PasswordNotNullException(MessageConstant.PASSWORD_NOT_NULL);
        }

        //2.如果输入的DTO的密码不为空，判断输入的旧密码是否正确(和adminMapper的老密码进行比较)


        //2.1首先找到老密码
        Map<String,Object>map=ThreadLocalUtil.get();
        Long adminId = (Long) map.get("adminId");
        Admin admin = adminMapper.findById(adminId);
        String oldPassword = admin.getPassword();

        //2.2找到了老密码，(注意这个老密码是MD5加密过后的),请进行比较
        if(!Md5Util.checkPassword(oldPasswordFromadminChangePasswordDTO,oldPassword)){
            //2.3如果密码错误的话就返回异常
            log.info("输入的密码错误");
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //2.4.如果输入的密码正确的话就比较输入的新密码和确认密码了，注意输入的长度要在5，16位之间
        if(!newPassword.equals(confirmNewPassword)){
            //2.5如果输入的新密码和确认密码错误返回异常
            throw new PasswordErrorException(MessageConstant.PASSWORD_DONT_MATCH);
        }

        //3.修改密码


        //3.1往adminMapper中修改
        try{
            newPassword=Md5Util.getMD5String(newPassword);
            adminMapper.updatePassword(newPassword,adminId);
            log.info("密码修改成功");
            return Result.success("修改成功");
        }catch (Exception exception){
            throw new UpdateAdminPasswordException(MessageConstant.UPDATE_PASSWORD_FAILSURE);
        }

    }


    /**
     * 修改管理员昵称
     * @param adminChangeNicknameDTO
     * @return
     */
    @Override
    public Result changeAdminNickname(AdminChangeNicknameDTO adminChangeNicknameDTO) throws UpdateAdminNicknameException {
        //不判空了，@Valid已经判了，直接写了
        try{
            String nickname = adminChangeNicknameDTO.getNickname();
            Map<String,Object>map=ThreadLocalUtil.get();
            Long adminId= (Long) map.get("adminId");
            adminMapper.updateNickName(nickname,adminId);
            return Result.success("修改昵称成功");
        }catch (Exception  e){
            throw  new UpdateAdminNicknameException(MessageConstant.UPDATE_NICKNAME_FAILSURE);
        }

    }

    /**
     * 换绑管理员邮箱
     * @param adminChangeEmailDTO
     * @return
     * @throws AccountNotFoundException
     * @throws VerifyCodeLockException
     * @throws VerifyCodeErrorException
     * @throws UpdateAdminEmailException
     */
    @Override
    public Result changeAdminEmail(AdminChangeEmailDTO adminChangeEmailDTO) throws AccountNotFoundException, VerifyCodeLockException, VerifyCodeErrorException, UpdateAdminEmailException {

        Map<String,Object>map=ThreadLocalUtil.get();
        Long adminId= (Long) map.get("adminId");
        //这里判空一下
        if(adminId==null){
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND);
        }
        Admin admin=adminMapper.findById(adminId);

        //校验邮箱验证码是否被锁住
        if(!verifyCodeService.canAttempt(admin)){
            Long lockRemainingSeconds = verifyCodeService.getLockRemainingSeconds(admin);
            throw new VerifyCodeLockException(MessageConstant.VERIFY_CODE_LOCK+lockRemainingSeconds+"秒钟");
        }

        //校验验证码是否正确
        boolean isValid=verifyCodeService.verifyCode(adminChangeEmailDTO.getCode(),admin);
        if(!isValid){
            Long remainingAttempts = verifyCodeService.getRemainingAttempts(admin);
            throw new VerifyCodeErrorException(MessageConstant.VERIFY_CODE_ERROR+",还可以试"+remainingAttempts+"次");
        }

        try{
            //更新邮箱
            admin.setEmail(adminChangeEmailDTO.getEmail());
            admin.setUpdateTime(LocalDateTime.now());

            // 添加日志，查看要更新的数据
            log.info("准备更新管理员信息 - ID: {}, 新邮箱: {}, 更新时间: {}",
                    admin.getId(), admin.getEmail(), admin.getUpdateTime());

            adminMapper.update(admin);
            return Result.success("成功");
        }catch (Exception exception){
            // 打印完整异常信息
            log.error("更新邮箱失败，异常信息：", exception);
            exception.printStackTrace(); // 临时添加，确保能看到异常

            // 获取异常的具体消息
            String errorMessage = exception.getMessage();
            log.error("异常详细信息：{}", errorMessage);

            throw new UpdateAdminEmailException(MessageConstant.UPDATE_ADMIN_EMAIL_FAILSURE + "：" + errorMessage);
        }
    }


    /**
     * 退出登录
     * @param token
     * @return
     * @throws LogoutFailsureException
     */
    @Override
    public Result logout(String token) throws LogoutFailsureException {
        try{
            Map<String,Object>map=ThreadLocalUtil.get();
            Long adminId = (Long) map.get("adminId");
            if(adminId == null){
                throw new LogoutFailsureException("无效的id");
            }
            tokenService.logout(adminId,token);
            return Result.success("退出成功");
        }catch (Exception e){
            throw new LogoutFailsureException(MessageConstant.LOGOUT_FAILSURE_EXCEPTION);
        }
    }


}
