package com.xixizai.personalblogwebsite.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xixizai.personalblogwebsite.pojo.entity.Admin;
import com.xixizai.personalblogwebsite.service.EmailService;
import com.xixizai.personalblogwebsite.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.xixizai.personalblogwebsite.constant.RedisConstant.*;


@Slf4j
@Service
public class VerifyCodeCodeServiceImpl implements VerifyCodeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private EmailService emailService;

    // 生成验证码
    @Override
    public String generateCode(){
        Random random=new Random();
        String code=String.format("%06d",random.nextInt(1_000_000));
        return code;
    }
    // 保存验证码并设置发送频率
    @Override
    public void saveCode(String code, Admin admin){
    //保存验证码到 redis,并且重置尝试次数
    stringRedisTemplate.opsForValue().set(KEY_VERIFY_CODE+":"+admin.getId()+":"+admin.getUsername(),code,CODE_TTL_MINUTES, TimeUnit.MINUTES);
    stringRedisTemplate.opsForValue().set(KEY_RATE_LIMIT+":"+admin .getId()+":"+admin.getUsername(),"1",RATE_LIMIT_SECONDS,TimeUnit.SECONDS
    );
    stringRedisTemplate.opsForValue().set(KEY_ATTEMPT_COUNT+":"+admin.getId()+":"+admin.getUsername(), String.valueOf(0));
    stringRedisTemplate.delete(KEY_LOCK+":"+admin.getId()+":"+admin.getUsername());
    }


    //获取验证码冷却时间
    @Override
    public Long getRemainingCooldown(Admin admin) {
        Long time = stringRedisTemplate.getExpire(KEY_RATE_LIMIT + ":" + admin.getId() + ":" + admin.getUsername(), TimeUnit.SECONDS);
        return (time!=null?Math.max(0,time):0);
    }

    // 邮箱是否可以发送验证码（频率限制）
    @Override
    public boolean canSendCode(Admin admin) {
        String s = stringRedisTemplate.opsForValue().get(KEY_RATE_LIMIT+":"+admin .getId()+":"+admin.getUsername());
        if(!StrUtil.isBlank(s)){
            return false;
        }
        return true;
    }

    // 是否被锁定
    @Override
    public Boolean isLocked(Admin admin){
         Boolean flag=stringRedisTemplate.hasKey(KEY_LOCK+":"+admin.getId()+":"+admin.getUsername());
         return Boolean.TRUE.equals(flag);
    }


    // 获取锁定剩余时间（秒钟)
    @Override
    public Long getLockRemainingSeconds(Admin admin){
        try{
            Long ttl=stringRedisTemplate.getExpire(KEY_LOCK+":"+admin.getId()+":"+admin.getUsername());
            return ttl !=null ? Math.max(0,ttl) :0;
        }catch (NullPointerException exception){
            throw exception;
        }
    }


    // 是否允许尝试验证
    @Override
    public Boolean canAttempt(Admin admin) {
        //检查是否被锁定
        if(isLocked(admin)){
            return false;
        }
        return true;
    }


    // 验证验证码是否正确
    @Override
    public Boolean verifyCode(String code,Admin admin) {
        if(code==null||code.trim().isEmpty()){
            return false;
        }
        //检查是否被锁定
        if(isLocked(admin)){
            return false;
        }
        //检查验证码是否存在
        String redisKey = KEY_VERIFY_CODE + ":" + admin.getId() + ":" + admin.getUsername();
        String s = stringRedisTemplate.opsForValue().get(redisKey);
        if(StrUtil.isEmpty(s)){
            //验证失败尝试记录
            recordFailedAttempt(admin);
            return false;
        }
        //验证
        if(s.equals(code.trim())){
            //验证成功，清除所有数据
            clearAll(admin);
            return true;
        }else{
            //验证失败，记录尝试
            recordFailedAttempt(admin);
            return false;
        }

    }
    // 记录失败尝试
    @Override
    public void recordFailedAttempt(Admin admin) {

        try{
            //增加失败计数
            Long increment = stringRedisTemplate.opsForValue().increment(KEY_ATTEMPT_COUNT + ":" + admin.getId() + ":" + admin.getUsername());

            //如果是第一次失败
            if(increment==1){
                //设置过期时间
                Long codeTtl=stringRedisTemplate.getExpire(KEY_VERIFY_CODE+":"+admin.getId()+":"+admin.getUsername(),TimeUnit.SECONDS);
                if(codeTtl>0){
                    stringRedisTemplate.expire(KEY_ATTEMPT_COUNT+":"+admin.getId()+":"+admin.getUsername(),codeTtl,TimeUnit.SECONDS);
                }
            }

            //达到最大尝试次数，锁定
            if(increment>=MAX_ATTEMPTS){
                stringRedisTemplate.opsForValue().set(KEY_LOCK+":"+admin.getId()+":"+admin.getUsername(),"1",LOCK_MINUTES,TimeUnit.MINUTES);
            }
        }catch (NullPointerException exception){
            throw exception;
        }
    }
    // 获取当前尝试次数
    @Override
    public Long getAttemptCount(Admin admin){
        String value = stringRedisTemplate.opsForValue().get(KEY_ATTEMPT_COUNT + ":" + admin.getId() + ":" + admin.getUsername());

        if(StrUtil.isBlank(value)){
            return 0L;
        }

        try{
            return Long.parseLong(value);
        }catch (NumberFormatException exception){
            return 0L;
        }
    }
    // 获取剩余尝试次数
    @Override
    public Long getRemainingAttempts(Admin admin){
        if(isLocked(admin)){
            return 0L;
        }
        Long attemptsCount=getAttemptCount(admin);
        log.info("还可以试一试"+Math.max(MAX_ATTEMPTS-attemptsCount,0));
        return Math.max(MAX_ATTEMPTS-attemptsCount,0);
    }
    // 重置状态
    @Override
    public void clearAll(Admin admin){
        stringRedisTemplate.delete(KEY_VERIFY_CODE+":"+admin.getId()+":"+admin.getUsername());
        stringRedisTemplate.delete(KEY_RATE_LIMIT+":"+admin .getId()+":"+admin.getUsername());
        stringRedisTemplate.delete(KEY_ATTEMPT_COUNT + ":" + admin.getId() + ":" + admin.getUsername());
        stringRedisTemplate.delete(KEY_LOCK+":"+admin.getId()+":"+admin.getUsername());
    }

}
