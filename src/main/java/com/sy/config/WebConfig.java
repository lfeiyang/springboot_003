package com.sy.config;

import com.sy.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 将拦截器配置到Spring Boot环境中
 *
 * @author lfeiyang
 * @since 2022-05-04 0:39
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加接口幂等拦截器，配置拦截地址
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/frameUser/**");
    }
}
