package com.xixizai.personalblogwebsite.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * IP地址工具类,这个可以获取具体国家城市，这个调用的是ip-api.com,每分钟最多150次
 */
@Slf4j
public class IpUtil {
    // IP地址查询接口
    public static final String IP_API = "http://ip-api.com/json/";
    public static final String LANGUAGE = "zh-CN";

    // 判断是否是本地 IP
    public static boolean isLocalIp(String ip) {
        if (ip == null) return true;

        // IPv4 本地地址
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1") || ip.equals("::1")) {
            return true;
        }

        // IPv4 私有地址段
        if (ip.startsWith("192.168.") ||
                ip.startsWith("10.") ||
                ip.startsWith("172.16.") ||
                ip.startsWith("172.17.") ||
                ip.startsWith("172.18.") ||
                ip.startsWith("172.19.") ||
                ip.startsWith("172.20.") ||
                ip.startsWith("172.21.") ||
                ip.startsWith("172.22.") ||
                ip.startsWith("172.23.") ||
                ip.startsWith("172.24.") ||
                ip.startsWith("172.25.") ||
                ip.startsWith("172.26.") ||
                ip.startsWith("172.27.") ||
                ip.startsWith("172.28.") ||
                ip.startsWith("172.29.") ||
                ip.startsWith("172.30.") ||
                ip.startsWith("172.31.")) {
            return true;
        }

        return false;
    }


    // 获取真实IP地址
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多级代理时，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }

    // 获取IP地址信息
    public static Map<String, String> getGeoInfo(String ip){
        Map<String,String> params = new HashMap<>();
        params.put("lang",LANGUAGE);
        String doneGet = HttpClientUtil.doGet(IP_API + ip, params);
        log.info("IP地址信息查询结果：{}",doneGet);
        // 封装返回结果
        Map<String, String> geoInfo = new HashMap<>();

        try {
            // 使用Jackson ObjectMapper解析JSON
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(doneGet, Map.class);

            // 提取需要的信息
            geoInfo.put("country", (String) jsonMap.getOrDefault("country", ""));
            geoInfo.put("province", stripAdminSuffix((String) jsonMap.getOrDefault("regionName", "")));
            geoInfo.put("city", stripAdminSuffix((String) jsonMap.getOrDefault("city", "")));
            geoInfo.put("latitude", String.valueOf(jsonMap.getOrDefault("lat", "")));
            geoInfo.put("longitude", String.valueOf(jsonMap.getOrDefault("lon", "")));

        } catch (Exception e) {
            log.error("解析IP地址信息失败", e);
        }
        return geoInfo;
    }

    /**
     * 去掉行政区划后缀（省、市、自治区、特别行政区）
     * 每个字段都独立校验"省"和"市"后缀
     */
    private static String stripAdminSuffix(String name) {
        if (name == null || name.isEmpty()) return name;
        // 先去除复杂的行政区划后缀
        name = name.replaceAll("壮族自治区|维吾尔自治区|回族自治区|自治区|特别行政区", "");
        // 再去除末尾的"省"或"市"（保证去除后至少保留 1 个字符）
        if (name.length() > 1 && (name.endsWith("省") || name.endsWith("市"))) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    /**
     * 获取本机（服务器）IP地址
     * @return 本机IP地址
     */
    public static String getLocalHostIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                // 跳过回环接口和未启用的接口
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    // 只获取 IPv4 地址
                    if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                        String ip = address.getHostAddress();
                        if (!ip.startsWith("127.")) {
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取本机IP地址失败", e);
        }
        // 获取默认IP地址
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            log.error("获取本机IP地址失败", e);
            return "127.0.0.1";
        }
    }
}
