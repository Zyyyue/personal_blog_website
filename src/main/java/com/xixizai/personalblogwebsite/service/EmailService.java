package com.xixizai.personalblogwebsite.service;

import com.xixizai.personalblogwebsite.exception.EmailSendErrorException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    /**
     * 发送验证码邮件
     */
    void sendVerifyCode(String email,String code) throws EmailSendErrorException;

    /**
     * 构建发送验证码的邮件内容
     */

    /**
     *发送评论/留言回复通知邮件
     */
}
