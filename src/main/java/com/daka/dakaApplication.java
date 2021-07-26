package com.daka;


import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;





@MapperScan("com.daka.user.Dao")
@ServletComponentScan("com.daka.user.FtpServer")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class dakaApplication {
    public static void main(String[] args) {
        SpringApplication.run(dakaApplication.class);
    }
}
