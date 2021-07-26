package com.daka.user.service;

import com.daka.user.AliFace.Expression;

import com.daka.user.AliFace.Hair;
import com.daka.user.AliFace.Person;
import com.daka.user.Dao.CheckerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;


@Service
public class AliService {
    @Autowired
    CheckerMapper checkermapper;

    @Autowired
    Hair hair;

    @Autowired
    Person person;

    @Autowired
    Expression expression;



    public Integer checkCollar (String pic_path , String face_x , String face_y){
        String result = "";
//        String pic_path = "D:\\LearnSoftware\\IDEA\\sometools\\collar_recongnition\\lfw_train\\52_0.jpg";
        try {
            //这个方法是类似隐形开启了命令执行器，输入指令执行python脚本
            // exec中第一个参数是  torch环境中的python.exe  第二个参数是py脚本  第三个参数是识别的图片
            File directory = new File("");// 参数为空
            String courseFile = directory.getAbsolutePath();
            Process process = Runtime.getRuntime()
                    .exec("D:\\Anaconda3\\envs\\daka\\python.exe " +
                                    courseFile+"\\src\\main\\resources\\pythonFile\\python_client.py " +
                            pic_path+ " "+ face_x + " "+face_y);
//                             " D:\\LearnSoftware\\IDEA\\sometools\\collar_recongnition\\CNN_vision-master\\mytest3"
//                            + " hello");
            //这种方式获取返回值的方式是需要用python打印输出，然后java去获取命令行的输出，在java返回
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();//中文的话这里可能会有乱码，可以尝试转一下不过问题不大
//            result1 = new String(result.getBytes("iso8859-1"),"utf-8");
            input.close();
            ir.close();
            process.waitFor();
            System.out.println(Thread.currentThread().getName()+":"+
                    LocalTime.now().toString()+"-"+"检查衣领:"+result);
            return Integer.parseInt(result);
        } catch (IOException | InterruptedException e) {
            System.out.println("调用python脚本并读取结果时出错：" + e.getMessage());
            return 1;
        }
    }

    public String checkHairColor(String ImgUrl){
        String res = hair.checkHairColor(ImgUrl);
        System.out.println(Thread.currentThread().getName()+":"+
                LocalTime.now().toString()+"-"+"检查发色:"+res);
        return res;
    }

    public String checkLowCloth(String ImgUrl){
        String res = person.checkLowCloth(ImgUrl);
        System.out.println(Thread.currentThread().getName()+":"+
                LocalTime.now().toString()+"-"+"检查裤子:"+res);
        return res;
    }

    public String checkExpression(String ImgUrl){
        String res = expression.checkExpression(ImgUrl);
        System.out.println(Thread.currentThread().getName()+":"+
                LocalTime.now().toString()+"-"+"检查表情:"+res);
        return res;
    }

}
