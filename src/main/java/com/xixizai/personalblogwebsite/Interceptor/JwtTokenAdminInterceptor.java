package com.xixizai.personalblogwebsite.Interceptor;

import com.xixizai.personalblogwebsite.constant.JwtClaimsConstant;
import com.xixizai.personalblogwebsite.constant.MessageConstant;
import com.xixizai.personalblogwebsite.constant.StatusConstant;
import com.xixizai.personalblogwebsite.exception.GuestReadOnlyException;
import com.xixizai.personalblogwebsite.exception.NotLoginException;
import com.xixizai.personalblogwebsite.exception.UnauthorizedException;
import com.xixizai.personalblogwebsite.properties.JwtProperties;
import com.xixizai.personalblogwebsite.service.TokenService;
import com.xixizai.personalblogwebsite.utils.JwtUtil;
import com.xixizai.personalblogwebsite.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            log.info("拦截到静态资源，直接放行");
            return true;
        }

        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());

        // 如果令牌为空，抛出未登录异常
        if (!StringUtils.hasText(token)) {
            throw new NotLoginException(MessageConstant.NOT_LOGIN);
        }

        try {
            // 校验令牌
            log.info("开始解析token");
            Map<String, Object> claims = JwtUtil.parseToken(jwtProperties.getSecretKey(), token);
            log.info("token解析成功，claims: {}", claims);

            Long adminId = Long.valueOf(claims.get(JwtClaimsConstant.ADMIN_ID).toString());
            Integer role = Integer.valueOf(claims.get(JwtClaimsConstant.ADMIN_ROLE).toString());

            log.info("jwt校验,当前管理员id:{}, role:{}", adminId, role);

            // token无效（不存在于redis中）
            log.info("开始验证token是否在redis中有效");
            boolean isValid = tokenService.isValidToken(adminId, token);
            log.info("token验证结果: {}", isValid);

            if (!isValid) {
                log.warn("token无效或已过期, adminId:{}", adminId);
                throw new UnauthorizedException(MessageConstant.NOT_AUTHORIZED);
            }

            // 游客账号(role=0)只允许GET查询操作
            if (role.equals(StatusConstant.DISABLE) && !"GET".equalsIgnoreCase(request.getMethod())) {
                throw new GuestReadOnlyException(MessageConstant.VISITOR_ONLY_READ_PRIVILEGE);
            }

            // 存储用户信息到ThreadLocal
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("adminId", adminId);
            userInfo.put("role", role);
            ThreadLocalUtil.set(userInfo);

            return true;

        } catch (GuestReadOnlyException exception) {
            throw exception;
        } catch (Exception exception) {
            // 验证失败，抛出未授权异常
            exception.printStackTrace();
            throw new UnauthorizedException(MessageConstant.NOT_AUTHORIZED);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            ThreadLocalUtil.remove();
            log.debug("ThreadLocal清理完成");
        } catch (Exception exception) {
            log.error("清理ThreadLocal失败", exception);
        }
    }
}