package com.daka.user.service;

import com.daka.user.AliFace.FaceSearch;
import com.daka.user.Dao.UserMapper;
import com.daka.user.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.io.File;

@Service
public class UserService {
    @Autowired
    UserMapper usermapper;

    @Autowired
    FaceSearch faceSearch;
    
    public List<User> selectUser(String str,int start,int end){
        return usermapper.selectUser(str,start,end);
    }
    public int saveUser(User user){
        return usermapper.saveUser(user);
    }
    public int updateUser(User user){
        return usermapper.updateUser(user);
    }
    public int deleteUser(ArrayList<Integer> ids){
        int num = 0;
        for(int i=0; i<ids.size();i++){
            faceSearch.deleteFaceEntity(selectIDbyid(ids.get(i)));
            num+=usermapper.deleteUser(ids.get(i));
        }
        return num;
    }
    public List<User> selectAll(String select_str,int start, int end){
        return usermapper.selectAll(select_str,start,end);
    }
    public int selectAllCount(String select_str){
        return usermapper.selectAllCount(select_str);
    }
    public String selectIDbyid(Integer id){
        return usermapper.selectIDbyid(id);
    }
    public Integer updateOpenID(String usr_ID,String open_ID){
        return usermapper.updateOpenID(usr_ID,open_ID);
    }
    public String uploadFile(String picName,MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // 上传文件路径
            String UploadPath = "D://meiya//image//usrpic//";
            //取得原文件名字
            String fileName = file.getOriginalFilename();
            //取得文件扩展名
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            // picName(filename) + 后缀组成新的文件名
            String filename = picName+suffix;
            File filepath = new File(UploadPath, filename);
            // 判断路径是否存在,没有创建
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文档中
            System.out.println(filename);
            file.transferTo(filepath);
            System.out.println("mark");

            // 将图片传入到Ali的员工数据库中,需要图片名和路径

            String imageUrl = (UploadPath+filename).replace("//","\\\\");
            System.out.println(imageUrl);
            faceSearch.AddFaceEntity(picName);
            faceSearch.AddFacePath(picName,imageUrl);
            // 返回的是一个url对象,图片名称
            return filename;
        } else {
            return null;
        }
    }
}
