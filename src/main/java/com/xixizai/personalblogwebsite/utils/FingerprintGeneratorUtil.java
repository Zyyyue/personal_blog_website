package com.xixizai.personalblogwebsite.utils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static com.xixizai.personalblogwebsite.utils.IpUtil.getClientIp;

/**
 * 浏览器指纹生成工具类
 * 用于唯一标识访客
 *
 * @author xixizai
 * @date 2026-03-31
 */
@Component
public class FingerprintGeneratorUtil {


    /**
     * 生成简单的指纹（仅使用IP和User-Agent）
     */
    public static String generateSimple(HttpServletRequest request) {
        String ip = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String rawFingerprint = ip + "|" + (userAgent != null ? userAgent : "unknown");
        String s=Md5Util.getMD5String(rawFingerprint);
        return s;
    }
}
