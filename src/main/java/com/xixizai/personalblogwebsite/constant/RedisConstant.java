package com.xixizai.personalblogwebsite.constant;
// Redis key
public class RedisConstant {

    //验证码的key
    public static final String KEY_VERIFY_CODE = "verify_code";
    //时间频率的key
    public static final String KEY_RATE_LIMIT = "rate_limit";
    //尝试次数的key
    public static final String KEY_ATTEMPT_COUNT = "attempt_count";
    //锁的key
    public static final String KEY_LOCK = "lock";
    // 时间常量
    public static final int RATE_LIMIT_SECONDS = 60; // 发送频率限制60秒
    public static final int CODE_TTL_MINUTES = 5;    // 验证码有效期5分钟
    public static final int MAX_ATTEMPTS = 5;        // 最大尝试次数
    public static final int LOCK_MINUTES = 30;       // 锁定30分钟
    public static final String TOKEN_PREFIX="token:active";//token的前缀
    //这个是如果一个游客浏览一次后要在1小时之后才计算浏览量
    public static final String VIEW_LIMIT_PREFIX="view:limit";
    //时间是1小时
    public static final long VIEW_LIMIT_SECONDS = 3600;
}
