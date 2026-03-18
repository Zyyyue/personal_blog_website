package com.xixizai.personalblogwebsite.service;

import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    //验证token是否有效
    boolean isValidToken(Long userId,String token);

    //创建并且保存token
    String createAndStoreToken(Long userId, Integer role);

    //退出登录
    void logout(Long id, String token);
}
