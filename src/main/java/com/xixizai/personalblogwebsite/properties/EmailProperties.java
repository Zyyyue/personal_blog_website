package com.xixizai.personalblogwebsite.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "xixi.email")
@Component
@Data
public class EmailProperties {

    /**
     * 邮箱服务器邮箱
     */
    private String personal;

    private String from;

}
