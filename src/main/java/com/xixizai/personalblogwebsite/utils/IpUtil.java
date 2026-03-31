package com.xixizai.personalblogwebsite.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * IP 地址工具类，调用高德地图 API 获取地理位置
 */
@Slf4j
@Component
public class IpUtil {

    // 高德地图 IP 查询接口
    public static final String AMAP_IP_API = "https://restapi.amap.com/v5/ip";

    // 从配置文件读取 Key
    private static String amapKey;

    @Value("${amap.api.key}")
    public void setAmapKey(String key) {
        amapKey = key;
    }

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


    // 获取真实 IP 地址
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

        // 多级代理时，取第一个 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }

    // 获取 IP 地址信息（高德地图 API）
    public static Map<String, String> getGeoInfo(String ip){
        // 本地 IP 直接返回
        if(isLocalIp(ip)) {
            Map<String, String> geoInfo = new HashMap<>();
            geoInfo.put("country", "中国");
            geoInfo.put("province", "本地");
            geoInfo.put("city", "本地");
            geoInfo.put("latitude", "0");
            geoInfo.put("longitude", "0");
            return geoInfo;
        }

        // 构建请求 URL
        String url = AMAP_IP_API + "?key=" + amapKey + "&ip=" + ip;

        // 发送请求（传一个空的 Map）
        String result = HttpClientUtil.doGet(url, new HashMap<>());
        log.info("IP 地址信息查询结果：{}", result);

        // 封装返回结果
        Map<String, String> geoInfo = new HashMap<>();

        // 请求失败处理
        if(result == null || result.isEmpty()) {
            log.warn("IP 地址信息查询失败，IP: {}", ip);
            geoInfo.put("country", "未知");
            geoInfo.put("province", "未知");
            geoInfo.put("city", "未知");
            geoInfo.put("latitude", "0");
            geoInfo.put("longitude", "0");
            return geoInfo;
        }

        try {
            // 使用 Jackson ObjectMapper 解析 JSON
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(result, Map.class);

            // 检查是否成功 (status=1 表示成功)
            String status = (String) jsonMap.getOrDefault("status", "0");
            if(!"1".equals(status)) {
                log.warn("高德 API 返回失败，status: {}, infocode: {}",
                        status, jsonMap.getOrDefault("infocode", ""));
                geoInfo.put("country", "未知");
                geoInfo.put("province", "未知");
                geoInfo.put("city", "未知");
                geoInfo.put("latitude", "0");
                geoInfo.put("longitude", "0");
                return geoInfo;
            }

            // 提取需要的信息
            geoInfo.put("country", "中国");  // 高德 IP 库只返回国内数据
            geoInfo.put("province", stripAdminSuffix((String) jsonMap.getOrDefault("province", "未知")));
            geoInfo.put("city", stripAdminSuffix((String) jsonMap.getOrDefault("city", "未知")));

            // 从 rectangle 中提取经纬度（格式："经度 1，纬度 1，经度 2，纬度 2"）
            String rectangle = (String) jsonMap.getOrDefault("rectangle", "");
            if(rectangle != null && !rectangle.isEmpty() && rectangle.contains(",")) {
                String[] parts = rectangle.split(",");
                if(parts.length >= 4) {
                    // 取中心点
                    double lon1 = Double.parseDouble(parts[0]);
                    double lat1 = Double.parseDouble(parts[1]);
                    double lon2 = Double.parseDouble(parts[2]);
                    double lat2 = Double.parseDouble(parts[3]);
                    geoInfo.put("longitude", String.valueOf((lon1 + lon2) / 2));
                    geoInfo.put("latitude", String.valueOf((lat1 + lat2) / 2));
                } else {
                    geoInfo.put("latitude", "0");
                    geoInfo.put("longitude", "0");
                }
            } else {
                geoInfo.put("latitude", "0");
                geoInfo.put("longitude", "0");
            }

        } catch (Exception e) {
            log.error("解析 IP 地址信息失败", e);
            geoInfo.put("country", "未知");
            geoInfo.put("province", "未知");
            geoInfo.put("city", "未知");
            geoInfo.put("latitude", "0");
            geoInfo.put("longitude", "0");
        }
        return geoInfo;
    }

    /**
     * 去掉行政区划后缀（省、市、自治区、特别行政区）
     */
    private static String stripAdminSuffix(String name) {
        if (name == null || name.isEmpty()) return name;
        // 先去除复杂的行政区划后缀
        name = name.replaceAll("壮族自治区 | 维吾尔族自治区 | 回族自治区 | 自治区 | 特别行政区", "");
        // 再去除末尾的"省"或"市"（保证去除后至少保留 1 个字符）
        if (name.length() > 1 && (name.endsWith("省") || name.endsWith("市"))) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    /**
     * 获取本机（服务器）IP 地址
     */
    public static String getLocalHostIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                        String ip = address.getHostAddress();
                        if (!ip.startsWith("127.")) {
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取本机 IP 地址失败", e);
        }
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            log.error("获取本机 IP 地址失败", e);
            return "127.0.0.1";
        }
    }
}