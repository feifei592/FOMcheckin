package com.daka.user.AliFace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.InputStream;


@Component
public class UpLoad {

    @Autowired
    @Qualifier(value = "getViapiFileUtilAdvance")
    ViapiFileUtilAdvance fileUtils;

    public String getUrl(String path) {
        InputStream inputStream = null;
        try {
            inputStream = fileUtils.buildInputStream(path);
            String ossTempFileUrl = fileUtils.upload(inputStream);
            return ossTempFileUrl;
        } catch ( Exception e) {
        }finally {
            try{
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
