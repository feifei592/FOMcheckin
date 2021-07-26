package com.daka.user.AliFace;


import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.facebody.model.v20191230.*;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FaceSearch {

    @Autowired
    IAcsClient client;

    @Autowired
    UpLoad upLoad;

    private String mDbName = "default";

    @Test
    public void test1() {
        CreateFaceDb();
    }

    /* "faceId":"6516060"
     * "faceId":"6516061"
     * "faceId":"6516062"} */


    @Test
    public void testAddFace() {
        String entityId1 = "compare_face_test_entity1";
        String entityId2 = "compare_face_test_entity2";
//        String entityId3 = "compare_face_test_entity3";
        AddFaceEntity(entityId1);
        AddFaceEntity(entityId2);
//        AddFaceEntity(entityId3);

        AddFacePath(entityId1, "D:\\LearnSoftware\\IDEA\\ali_face\\test_测试图像\\staff\\p1.jpg");
        AddFacePath(entityId2, "D:\\LearnSoftware\\IDEA\\ali_face\\test_测试图像\\staff\\sun1.jpg");
//        AddFacePath(entityId3, "Z:\\项目\\夏令营\\test\\staff\\yanjl.jpg");

    }

    public void CreateFaceDb() {
        CreateFaceDbRequest request = new CreateFaceDbRequest();
        request.setRegionId("cn-shanghai");
        request.setName(mDbName);

        try {
            CreateFaceDbResponse response = client.getAcsResponse(request);
            System.out.println("CreateFaceDb: " + new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    public void deleteFaceEntity(String entityId)
    {
        DeleteFaceEntityRequest deleteFaceEntityRequest = new DeleteFaceEntityRequest();
        deleteFaceEntityRequest.setDbName(mDbName);
        deleteFaceEntityRequest.setEntityId(entityId);
        try{
            DeleteFaceEntityResponse deleteFaceEntityResponse = client.getAcsResponse(deleteFaceEntityRequest);
            System.out.println("删除人脸样本：");
            System.out.println(new Gson().toJson(deleteFaceEntityResponse));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }
    public void AddFaceEntity(String entityId) {

        deleteFaceEntity(entityId);
        AddFaceEntityRequest request = new AddFaceEntityRequest();
        request.setRegionId("cn-shanghai");
        request.setDbName(mDbName);
        request.setEntityId(entityId);
//        request.setLabels("人事");

        try {
            AddFaceEntityResponse response = client.getAcsResponse(request);
            System.out.println("添加人脸样本：");
            System.out.println("AddFaceEntity: " + new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    public void AddFacePath(String entityId, String path) {
        String imageUrl = upLoad.getUrl(path);
        AddFace(entityId,imageUrl);
    }

    public void AddFace(String entityId, String imageUrl) {

        AddFaceRequest request = new AddFaceRequest();
        request.setRegionId("cn-shanghai");
        request.setDbName(mDbName);
        request.setEntityId(entityId);
        request.setImageUrl(imageUrl);
//        request.setExtraData("小明");

        try {
            AddFaceResponse response = client.getAcsResponse(request);
            System.out.println("AddFace:" + new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    public String SearchFacePath(String ImgUrl) {
        return SearchFace(ImgUrl);
    }



    public String SearchFace(String url) {

        SearchFaceRequest request = new SearchFaceRequest();
        request.setRegionId("cn-shanghai");
        request.setDbName(mDbName);
        request.setImageUrl(url);
        request.setLimit(5);

        SearchFaceResponse response = null;
        try {
            response = client.getAcsResponse(request);
            System.out.println("SearchFace rsp: " + new Gson().toJson(response));
            JSONObject jsonAll = JSONObject.fromObject(response);
            Object data_object = jsonAll.get("data");
            JSONObject data_json_object = JSONObject.fromObject(data_object);
            JSONArray matchList = data_json_object.getJSONArray("matchList");
            Object matchList_array_0 = matchList.get(0);
            JSONObject matchList_array_json_object = JSONObject.fromObject(matchList_array_0);
            Object location_object =matchList_array_json_object.get("location");
            JSONObject location_json_object = JSONObject.fromObject(location_object);
            String faceX = location_json_object.get("x").toString();
            String faceY = location_json_object.get("y").toString();
            System.out.println(location_json_object.get("x"));
            System.out.println(location_json_object.get("y"));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        finally {
            return new Gson().toJson(response);
        }
    }
}
