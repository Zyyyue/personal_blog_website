package com.xixizai.personalblogwebsite.config;

import com.xixizai.personalblogwebsite.Interceptor.JwtTokenAdminInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Resource
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/sendCode");
    }

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
               .allowedOriginPatterns("*") //允许所有源，指定域名
               .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") //允许的HTTP方法
               .allowedHeaders("*")
               .allowCredentials(true)
               .maxAge(3600); //预检请求缓存时间
    }


}
