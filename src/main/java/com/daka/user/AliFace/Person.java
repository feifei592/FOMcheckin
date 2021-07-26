package com.daka.user.AliFace;


import com.aliyun.facebody20191230.models.*;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Person {

    @Autowired
    com.aliyun.facebody20191230.Client client;

    public String checkLowCloth(String ImgUrl){
        try {
            PedestrianDetectAttributeRequest pedestrianDetectAttributeRequest = new PedestrianDetectAttributeRequest();
            pedestrianDetectAttributeRequest.setImageURL(ImgUrl);
            // 复制代码运行请自行打印 API 的返回值
            PedestrianDetectAttributeResponse resp_person = client.pedestrianDetectAttribute(pedestrianDetectAttributeRequest);
            JSONObject jsonAll = JSONObject.fromObject(new Gson().toJson(resp_person));
            Object body_object = jsonAll.get("body");
            JSONObject body_json_object = JSONObject.fromObject(body_object);
            Object data_object = body_json_object.get("data");
            JSONObject data_json_object = JSONObject.fromObject(data_object);
            Object attributes_object = data_json_object.get("attributes");
            JSONArray attributes_json_array = JSONArray.fromObject(attributes_object);
            Object person_object = attributes_json_array.get(0);
            JSONObject person_json_object = JSONObject.fromObject(person_object);
            Object gender_object =person_json_object.get("gender");
            JSONObject gender_json_object = JSONObject.fromObject(gender_object);
            //得到性别  male男； female女；
            Object gender_name_object = gender_json_object.get("name");
            Object lowerWear_object =person_json_object.get("lowerWear");
            JSONObject lowerWear_json_object = JSONObject.fromObject(lowerWear_object);
            //得到裤子种类  Trousers长裤； Short短裤；
            Object lowerWear_name_object = lowerWear_json_object.get("name");
            return lowerWear_name_object.toString();
        } catch (Exception e) {
            return null;
            //e.printStackTrace();
            //System.out.println("传输person图片到阿里云异常或解析裤子Json异常");
        }
    }
}
