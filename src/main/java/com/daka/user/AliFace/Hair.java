package com.daka.user.AliFace;


import com.aliyun.tea.*;
import com.aliyun.imageseg20191230.*;
import com.aliyun.imageseg20191230.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.imagerecog20190930.*;
import com.aliyun.imagerecog20190930.models.*;
import com.aliyun.teaconsole.*;
import com.aliyun.teautil.*;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLConnection;
import java.io.*;

@Component
public class Hair {

    @Autowired
    com.aliyun.imageseg20191230.Client client1;

    @Autowired
    com.aliyun.imagerecog20190930.Client client2;

    @Autowired
    UpLoad upLoad;

    public String checkHairColor(String ImgUrl) {

        SegmentHairResponse resp_hair = null;
        try {
            //这个SegmentHairRequest() 需要传imgURL
            SegmentHairRequest segmentHairRequest = new SegmentHairRequest();
            segmentHairRequest.setImageURL(ImgUrl);
            resp_hair = client1.segmentHair(segmentHairRequest);
            //System.out.println(new Gson().toJson(resp_hair));
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("分割头发上传图片异常");
            return null;
        }

        // 解析返回的json  得到分割头发的网址
        Object res_imageUrl = null;
        try {
            JSONObject jsonAll = JSONObject.fromObject(new Gson().toJson(resp_hair));
            Object body_object = jsonAll.get("body");
            JSONObject body_json_object = JSONObject.fromObject(body_object);
            Object data_object = body_json_object.get("data");
            JSONObject data_json_object = JSONObject.fromObject(data_object);
            Object elements_object = data_json_object.get("elements");
            JSONArray elements_json_array = JSONArray.fromObject(elements_object);
            Object imageURL_object = elements_json_array.get(0);
            JSONObject imageURL_json_object = JSONObject.fromObject(imageURL_object);
            res_imageUrl = imageURL_json_object.get("imageURL");
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("解析头发抠图Json错误");
            return null;
        }
        //从解析的链接中下载头发抠图
        try {
            URL url = new URL(res_imageUrl.toString());
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            String filename = "D:\\meiya\\image\\hair\\hair.jpg";
            File file = new File(filename);
            FileOutputStream os = new FileOutputStream(file, false);
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            //e.printStackTrace();
            //System.out.println("下载头发抠图错误");
            return null;
        }

        try {
            //用阿里云对发色抠图进行颜色检测
            String path_hair_color = "D:\\meiya\\image\\hair\\hair.jpg";
            String imageUrl_hair_color = upLoad.getUrl(path_hair_color);
            RecognizeImageColorRequest recognizeImageColorRequest = new RecognizeImageColorRequest();
            recognizeImageColorRequest.setUrl(imageUrl_hair_color);
            // 复制代码运行请自行打印 API 的返回值
            RecognizeImageColorResponse resp_hair_color = client2.recognizeImageColor(recognizeImageColorRequest);

            JSONObject jsonAll = JSONObject.fromObject(new Gson().toJson(resp_hair_color));
            Object body_object = jsonAll.get("body");
            JSONObject body_json_object = JSONObject.fromObject(body_object);
            Object data_object = body_json_object.get("data");
            JSONObject data_json_object = JSONObject.fromObject(data_object);
            Object colorTemplateList_object = data_json_object.get("colorTemplateList");
            JSONArray colorTemplateList_json_array = JSONArray.fromObject(colorTemplateList_object);
            Object colorTemplateList_array_0 = colorTemplateList_json_array.get(0);
            JSONObject colorTemplateList_0_json_object = JSONObject.fromObject(colorTemplateList_array_0);
            Object label = colorTemplateList_0_json_object.get("label");
            return label.toString();
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("上传头发抠图到阿里云异常或解析发色json异常");
        }
        return null;
    }
}
