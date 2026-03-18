package com.xixizai.personalblogwebsite;

import com.xixizai.personalblogwebsite.properties.EmailProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EmailProperties.class)
@MapperScan("com.xixizai.personalblogwebsite.mapper")
public class PersonalBlogWebsiteApplication {

    //启动
    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogWebsiteApplication.class, args);
    }

}
