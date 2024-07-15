package org.deslre.common.config;

import org.deslre.common.interceptor.LoginInterceptor;
import org.deslre.common.interceptor.RefreshTokenInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configurable
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns().order(1);
        //  token刷新的
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }
}
