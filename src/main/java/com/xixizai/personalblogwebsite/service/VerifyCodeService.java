package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.pojo.entity.Admin;
import org.springframework.stereotype.Service;

@Service
public interface VerifyCodeService {
    //获取验证码冷却时间
    Long getRemainingCooldown(Admin admin);

    //邮箱是否可以发送验证码
    boolean canSendCode(Admin admin);

    //生成验证码
    String generateCode();

    //保存验证码并设置发送频率
    void saveCode(String code, Admin admin);

    //校验验证码是否正确
    Boolean verifyCode(String code,Admin admin);

    //是否被锁住
    Boolean isLocked(Admin admin);

    //获取锁剩余时间(秒)
    Long getLockRemainingSeconds(Admin admin);

    //是否允许尝试验证
    Boolean canAttempt(Admin admin);

    //记录失败尝试
    void recordFailedAttempt(Admin admin);

    //获取当前尝试次数
    Long getAttemptCount(Admin admin);

    //获取剩余尝试次数
    Long getRemainingAttempts(Admin admin);

    //重置状态
    void clearAll(Admin admin);
}
