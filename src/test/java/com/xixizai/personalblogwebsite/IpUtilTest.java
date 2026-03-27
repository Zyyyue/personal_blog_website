package com.xixizai.personalblogwebsite;

import com.xixizai.personalblogwebsite.utils.IpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class IpUtilTest {

    @Test
    public void testGetGeoInfo(){
        Map<String, String> geoInfo = IpUtil.getGeoInfo("8.8.8.8");
        System.out.println("国家: " + geoInfo.get("country"));
        System.out.println("省份: " + geoInfo.get("province"));
        System.out.println("城市: " + geoInfo.get("city"));
    }



}
