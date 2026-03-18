package com.xixizai.personalblogwebsite.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * 私匙使用固定密钥
     *
     * @param secretKey jwt秘钥
     * ttlMillis(jwt规定传进去的是毫秒)
     * @param claims    设置的信息
     * @return
     */
    //生成jwt
    public static String createToken(String secretKey, long ttlMinutes,Map<String,Object>claims){
        //指定签名的时候用的签名算法，就是header部分
        SecretKey key=Keys.hmacShaKeyFor(secretKey.getBytes());

        //生成JWT的时间
        long ttlMillis=ttlMinutes*60*1000;
        long expMillis=System.currentTimeMillis()+ttlMillis;
        Date exp=new Date(expMillis);

        return Jwts.builder()
                .setClaims(claims) //添加载荷
                .setExpiration(exp) //添加过期时间
                .signWith(key) //指定算法
                .compact();
    }

    /**
     * Token解密
     *
     * @param secretKey jwt密钥
     * @param token 加密过后的token
     * @return
     *
     */

    public static Map<String,Object> parseToken(String secretKey,String token){
        SecretKey key=Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parserBuilder() //创建解析器构造器
                .setSigningKey(key)  //设置验证码
                .build() //构造构造器实例
                .parseClaimsJws(token) //解析并且验证JWT
                .getBody(); //获取载荷
    }

}

