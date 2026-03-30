package com.xixizai.personalblogwebsite.utils;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserAgentUtil {

    private UserAgentAnalyzer userAgentAnalyzer;

    // 无效的操作系统名称列表（Java 8 兼容写法）
    private static final Set<String> INVALID_OS_NAMES = new HashSet<>(Arrays.asList(
            "Hacker", "Unknown", "Other", "null", "undefined", "", "None"
    ));

    @PostConstruct
    public void init() {
        // 初始化 UserAgent 解析器
        userAgentAnalyzer = UserAgentAnalyzer
                .newBuilder()
                .withFields(
                        UserAgent.OPERATING_SYSTEM_NAME,
                        UserAgent.AGENT_NAME,
                        UserAgent.AGENT_VERSION
                )
                .build();
    }

    /**
     * 获取操作系统名称
     */
    public String getOsName(String userAgentString) {
        if (userAgentString == null || userAgentString.isEmpty()) {
            return "Unknown";
        }

        try {
            UserAgent agent = userAgentAnalyzer.parse(userAgentString);
            String osName = agent.getValue(UserAgent.OPERATING_SYSTEM_NAME);

            // 过滤无效值
            if (osName == null || INVALID_OS_NAMES.contains(osName)) {
                return detectOsFromUserAgent(userAgentString);
            }

            // 截断过长字符串（防止数据库字段溢出）
            if (osName.length() > 50) {
                osName = osName.substring(0, 50);
            }

            return osName;
        } catch (Exception e) {
            return detectOsFromUserAgent(userAgentString);
        }
    }

    /**
     * 获取浏览器名称
     */
    public String getBrowserName(String userAgentString) {
        if (userAgentString == null || userAgentString.isEmpty()) {
            return "Unknown";
        }

        try {
            UserAgent agent = userAgentAnalyzer.parse(userAgentString);
            String browserName = agent.getValue(UserAgent.AGENT_NAME);

            if (browserName == null || INVALID_OS_NAMES.contains(browserName)) {
                return detectBrowserFromUserAgent(userAgentString);
            }

            return simplifyBrowserName(browserName);
        } catch (Exception e) {
            return detectBrowserFromUserAgent(userAgentString);
        }
    }

    /**
     * 获取浏览器完整信息（名称+版本）
     */
    public String getBrowserFullInfo(String userAgentString) {
        if (userAgentString == null || userAgentString.isEmpty()) {
            return "Unknown";
        }

        try {
            UserAgent agent = userAgentAnalyzer.parse(userAgentString);
            String browserName = agent.getValue(UserAgent.AGENT_NAME);
            String browserVersion = agent.getValue(UserAgent.AGENT_VERSION);

            if (browserName == null || INVALID_OS_NAMES.contains(browserName)) {
                return detectBrowserFromUserAgent(userAgentString);
            }

            String simplified = simplifyBrowserName(browserName);
            if (browserVersion != null && !browserVersion.isEmpty()) {
                return simplified + " " + browserVersion;
            }
            return simplified;
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /**
     * 从 User-Agent 中简单判断操作系统（备用方案）
     */
    private String detectOsFromUserAgent(String userAgentString) {
        if (userAgentString == null) return "Unknown";

        String ua = userAgentString.toLowerCase();

        if (ua.contains("windows")) {
            if (ua.contains("windows nt 10.0")) return "Windows 10/11";
            if (ua.contains("windows nt 6.3")) return "Windows 8.1";
            if (ua.contains("windows nt 6.2")) return "Windows 8";
            if (ua.contains("windows nt 6.1")) return "Windows 7";
            return "Windows";
        } else if (ua.contains("mac os") || ua.contains("macintosh")) {
            return "macOS";
        } else if (ua.contains("linux")) {
            if (ua.contains("android")) return "Android";
            return "Linux";
        } else if (ua.contains("iphone") || ua.contains("ipad")) {
            return "iOS";
        } else if (ua.contains("cros")) {
            return "Chrome OS";
        }

        return "Unknown";
    }

    /**
     * 从 User-Agent 中简单判断浏览器（备用方案）
     */
    private String detectBrowserFromUserAgent(String userAgentString) {
        if (userAgentString == null) return "Unknown";

        String ua = userAgentString.toLowerCase();

        if (ua.contains("edg") || ua.contains("edge")) {
            return "Edge";
        } else if (ua.contains("chrome") && !ua.contains("edg")) {
            return "Chrome";
        } else if (ua.contains("firefox") && !ua.contains("seamonkey")) {
            return "Firefox";
        } else if (ua.contains("safari") && !ua.contains("chrome")) {
            return "Safari";
        } else if (ua.contains("opera") || ua.contains("opr")) {
            return "Opera";
        } else if (ua.contains("msie") || ua.contains("trident")) {
            return "Internet Explorer";
        } else if (ua.contains("qqbrowser")) {
            return "QQ Browser";
        } else if (ua.contains("ucbrowser")) {
            return "UC Browser";
        }

        return "Unknown";
    }

    /**
     * 简化浏览器名称
     */
    private String simplifyBrowserName(String browserName) {
        if (browserName == null) return "Unknown";

        if (browserName.contains("Chrome") && !browserName.contains("Edg")) {
            return "Chrome";
        } else if (browserName.contains("Firefox")) {
            return "Firefox";
        } else if (browserName.contains("Safari") && !browserName.contains("Chrome")) {
            return "Safari";
        } else if (browserName.contains("Edg")) {
            return "Edge";
        } else if (browserName.contains("Opera") || browserName.contains("OPR")) {
            return "Opera";
        } else if (browserName.contains("MSIE") || browserName.contains("Trident")) {
            return "Internet Explorer";
        }

        // 截断过长名称
        if (browserName.length() > 30) {
            browserName = browserName.substring(0, 30);
        }

        return browserName;
    }
}