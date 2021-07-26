package com.daka.user.AliFace;

import com.aliyun.tea.*;
import com.aliyun.facebody20191230.*;
import com.aliyun.facebody20191230.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Expression {

    @Autowired
    com.aliyun.facebody20191230.Client client;

    public String checkExpression(String ImgUrl) {
        try {
            RecognizeExpressionRequest recognizeExpressionRequest = new RecognizeExpressionRequest();
            recognizeExpressionRequest.setImageURL(ImgUrl);
            // 复制代码运行请自行打印 API 的返回值
            RecognizeExpressionResponse resp_expression = client.recognizeExpression(recognizeExpressionRequest);
            String res_expression;
            JSONObject jsonAll = JSONObject.fromObject(new Gson().toJson(resp_expression));
            Object body_object = jsonAll.get("body");
            JSONObject body_json_object = JSONObject.fromObject(body_object);
            Object data_object = body_json_object.get("data");
            JSONObject data_json_object = JSONObject.fromObject(data_object);
            Object elements_object = data_json_object.get("elements");
            JSONArray elements_json_array = JSONArray.fromObject(elements_object);
            Object expression_object = elements_json_array.get(0);
            JSONObject expression_json_object = JSONObject.fromObject(expression_object);
            res_expression = expression_json_object.get("expression").toString();
            return res_expression;
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("表情图片上传阿里云异常或者解析表情Json异常");
        }
        return null;
    }
}
