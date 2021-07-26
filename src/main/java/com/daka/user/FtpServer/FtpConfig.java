package com.daka.user.FtpServer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class FtpConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/ftp/**").addResourceLocations("file:" + "D://LearnSoftware//IDEA//ali_face//test_测试图像//cap//");
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }

}