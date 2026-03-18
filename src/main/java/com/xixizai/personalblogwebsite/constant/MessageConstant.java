package com.xixizai.personalblogwebsite.constant;

public class MessageConstant {
    public static  final String ACCOUNT_NOT_FOUND="账号未发现";
    public static  final String VISITOR_NOT_SEND_VERIFYCODE="游客不能发送验证码";
    public static  final String EMAIL_SEND_ERROR="邮件验证码发送失败";
    public static  final String NOT_LOGIN="未登录，请先登录";
    public static final String NOT_AUTHORIZED = "登录状态失效，请重新登录";
    public static final String PASSWORD_ERROR="密码错误";
    public static final String VERIFY_CODE_LOCK="验证码输入错误次数过多,验证码已被锁定";
    public static final String VERIFY_CODE_ERROR="邮件验证码错误";
    public static final String FIND_NO_ADMININFORMATION_BY_ID ="没有根据id找到管理员的信息" ;
    public static final String VISITOR_ONLY_READ_PRIVILEGE="游客仅仅只有查看权限，无法进行此操作";
    public static final String PASSWORD_NOT_NULL = "输入的密码不能为空";
    public static final String PASSWORD_DONT_MATCH = "两次密码不一致";
    public static final String UPDATE_PASSWORD_FAILSURE = "密码修改失败";
    public static final String UPDATE_NICKNAME_FAILSURE = "昵称修改失败";
    public static final String UPDATE_ADMIN_EMAIL_FAILSURE = "邮箱换绑失败";
    public static final String ARTICLE_NOT_FOUND_EXCEPTION = "没有发现任何文章";

    public static final String ID_NOT_VALID = "id不合法";
    public static final String LOGOUT_FAILSURE_EXCEPTION = "退出登录失败";
}
