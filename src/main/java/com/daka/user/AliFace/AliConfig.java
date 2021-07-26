package com.daka.user.AliFace;

import com.aliyun.teaopenapi.models.Config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class AliConfig {
    private  static String accessKey = "LTAI5t9abcQcGSAMqVGAWYpn";    //您的AccessKeyID
    private  static String accessKeySecret = "kYAMvcaDcJqD5PpCKOKMJQPNZ4ZuSm";   //您的AccessKeySecret
    private static String regionId = "cn-shanghai";

    public AliConfig() {
        System.out.println("SpringBoot容器初始化");
    }

    //发色1
    @Bean
    public com.aliyun.imageseg20191230.Client getImageseg() throws Exception{
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKey)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "imageseg.cn-shanghai.aliyuncs.com";
        return new com.aliyun.imageseg20191230.Client(config);
    }
    //发色2
    @Bean
    public com.aliyun.imagerecog20190930.Client getImagerecog() throws Exception{
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKey)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "imagerecog.cn-shanghai.aliyuncs.com";
        return new com.aliyun.imagerecog20190930.Client(config);
    }

    //下衣 and 表情
    @Bean
    public com.aliyun.facebody20191230.Client getFacebody() throws Exception{
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKey)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "facebody.cn-shanghai.aliyuncs.com";
        return new com.aliyun.facebody20191230.Client(config);
    }


    //人脸识别
    @Bean
    public IAcsClient getClient(){
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    @Bean
    public ViapiFileUtilAdvance getViapiFileUtilAdvance() throws Exception{
        ViapiFileUtilAdvance fileUtils = new ViapiFileUtilAdvance();
        return fileUtils;
    }
}