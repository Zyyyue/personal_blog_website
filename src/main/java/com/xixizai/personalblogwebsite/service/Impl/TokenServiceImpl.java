package com.xixizai.personalblogwebsite.service.Impl;

import com.xixizai.personalblogwebsite.constant.JwtClaimsConstant;
import com.xixizai.personalblogwebsite.properties.JwtProperties;
import com.xixizai.personalblogwebsite.service.TokenService;
import com.xixizai.personalblogwebsite.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xixizai.personalblogwebsite.constant.RedisConstant.TOKEN_PREFIX;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JwtProperties jwtProperties;

    //token是否有效
    @Override
    public boolean isValidToken(Long userId, String token) {
        String key=TOKEN_PREFIX+userId;
        return Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key,token));
    }

    //创建并且存储token
    @Override
    public String createAndStoreToken(Long userId, Integer role) {
        //生成token
        Map<String,Object>claims=new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ROLE,role);
        claims.put(JwtClaimsConstant.ADMIN_ID,userId);
        String token= JwtUtil.createToken(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );

        // 将token存储至Redis,用set可以多端登录
        String tokenKey = TOKEN_PREFIX + userId;
        stringRedisTemplate.opsForSet().add(tokenKey, token);
        stringRedisTemplate.expire(tokenKey, jwtProperties.getTtl(), TimeUnit.MINUTES);

        return token;
    }

    /**
     * 退出登录
     * @param id
     * @param token
     */
    @Override
    public void logout(Long id, String token) {
        // 1. 参数校验
        if (id == null) {
            log.warn("退出登录失败：id 为空");
            return;
        }

        if (token == null || token.isEmpty()) {
            log.warn("退出登录失败：token 为空，id={}", id);
            return;
        }

        // 2. 构建 key
        String key = TOKEN_PREFIX + id;
        log.info("退出登录 - key: {}, token: {}", key, token);

        // 3. 从 Set 中移除 token
        Long removed = stringRedisTemplate.opsForSet().remove(key, token);
        log.info("从Redis移除token结果 - 移除数量: {}", removed);

        // 4. 如果 Set 为空，删除整个 key（可选）
        if (removed != null && removed > 0) {
            Long size = stringRedisTemplate.opsForSet().size(key);
            if (size == null || size == 0) {
                Boolean deleted = stringRedisTemplate.delete(key);
                log.info("Set为空，删除key: {}, 结果: {}", key, deleted);
            }
        }
    }


}