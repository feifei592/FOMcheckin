package com.daka.user.python;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class CollarRecongniton {

    @Test
    public void mytest(){
        //System.out.println(pyfileUpload("D:\\hhy.jpg","83","186"));
//        System.out.println(pyfileUpload("D:\\LearnSoftware\\IDEA\\sometools\\collar_recongnition\\lfw_train\\14_0.jpg"));
    }


    public int  pyfileUpload(String pic_path ,String face_x , String face_y)   {
        String result = "";
//        String pic_path = "D:\\LearnSoftware\\IDEA\\sometools\\collar_recongnition\\lfw_train\\52_0.jpg";
        try {
            //这个方法是类似隐形开启了命令执行器，输入指令执行python脚本
            // exec中第一个参数是  torch环境中的python.exe  第二个参数是py脚本  第三个参数是识别的图片
            Process process = Runtime.getRuntime()
                    .exec("D:\\Anaconda3\\envs\\image\\python.exe " +
                            "D:\\meiya\\daka\\src\\main\\resources\\pythonFile\\CollarRecongnition_keras.py " +
                            pic_path + " "+ face_x + " "+face_y);
            //这种方式获取返回值的方式是需要用python打印输出，然后java去获取命令行的输出，在java返回
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();//中文的话这里可能会有乱码，可以尝试转一下不过问题不大
//            result1 = new String(result.getBytes("iso8859-1"),"utf-8");
            input.close();
            ir.close();
            int re = process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("调用python脚本并读取结果时出错 ：" + e.getMessage());
        }
            return Integer.parseInt(result);
//        System.out.println(result);
    }



}

