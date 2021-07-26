package com.daka.user.controller;


import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器 用于前端访问员工图片
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/usrpic/**").addResourceLocations("file:" + "D://meiya//image//usrpic//");
        registry.addResourceHandler("/checkpic/**").addResourceLocations("file:" + "D://meiya//image//checkPic//");
    }
}
