package com.xixizai.personalblogwebsite.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xixi.visitor")
public class VisitorProperties {
    private  String verifyCode;
}
