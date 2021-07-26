package com.daka.user.service;


import com.daka.user.AliFace.DakaID;
import com.daka.user.AliFace.FaceSearch;
import com.daka.user.AliFace.UpLoad;
import com.daka.user.AliFace.ViapiFileUtilAdvance;
import com.daka.user.Dao.CheckerMapper;
import com.daka.user.email.EmailSend;

import com.daka.user.email.EmailThread;
import com.daka.user.entity.Checker;
import com.daka.user.entity.EmailObj;
import com.daka.user.entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CountDownLatch;


@Service
public class CheckerService {
    @Autowired
    private CheckerMapper checkermapper;
    @Autowired
    private AliService aliService;
    @Autowired
    private AsyncRequestService asyncRequestService;
    @Autowired
    private EmailThread emailThread;
    @Autowired
    UpLoad upLoad;
    @Autowired
    FaceSearch faceSearch;
    @Autowired
    DakaID dakaID;


    private com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();

    public final static int check_in = 1;
    public final static int check_in_warn = -1;
    public final static int check_out = 2;
    public final static int check_out_warn = -2;

    private Time moringstartSqlTime = new Time(Time.valueOf("05:00:00").getTime());
    private Time moringendSqlTime = new Time(Time.valueOf("8:40:00").getTime());
    private Time midTime = new Time(Time.valueOf("12:00:00").getTime());
    private Time nightstartSqlTime = new Time(Time.valueOf("17:40:00").getTime());
    private Time nightendSqlTime = new Time(Time.valueOf("23:50:00").getTime());

    public int addChecker(Checker checker) {
        return checkermapper.addChecker(checker);
    }

    public String selectStatebyExpression(String expression){
        return checkermapper.selectStatebyExpression(expression);
    }

    public List<Checker> selectChecker(String str,Date startDate, Date endDate,int start,int end){
        return checkermapper.selectChecker(str,startDate,endDate,start,end);
    }
    public int selectAllCount(String str,Date startDate, Date endDate){
        return checkermapper.selectAllCount(str,startDate,endDate);
    }
    public User selectuserbyID(String usr_ID) {
        return checkermapper.selectuserbyID(usr_ID);
    }

    String result_low_cloth;
    String result_expression;
    Integer result_collar;
    String result_hair_color;


    String faceX = null;
    String faceY = null;
    public void checkCompare(String pic_url) {

        String entityId = null;
        String imageUrl = upLoad.getUrl(pic_url);
        try {
            //通过解析json 得到返回的Json中人脸的第一条（得分最高）数据
            String resJson = faceSearch.SearchFacePath(imageUrl);
            JSONObject jsonAll = JSONObject.fromObject(resJson);
            Object data_object = jsonAll.get("data");
            JSONObject data_json_object = JSONObject.fromObject(data_object);
            JSONArray matchList = data_json_object.getJSONArray("matchList");
            Object faceItems_object = matchList.get(0);
            JSONObject faceItems_json_object = JSONObject.fromObject(faceItems_object);
            JSONArray target_json_Array = faceItems_json_object.getJSONArray("faceItems");
            Object target_object = target_json_Array.get(0);
            JSONObject target_json_object = JSONObject.fromObject(target_object);

            //解析json  得到上传图片中人脸的X,Y坐标 。 由于坐标轴定义不同，X和Y坐标需要颠倒下
            Object matchList_array_0 = matchList.get(0);
            JSONObject matchList_array_json_object = JSONObject.fromObject(matchList_array_0);
            Object location_object =matchList_array_json_object.get("location");
            JSONObject location_json_object = JSONObject.fromObject(location_object);
            faceX = location_json_object.get("y").toString();
            faceY = location_json_object.get("x").toString();
            //threshold 设置阈值为0.5
            if ((double) target_json_object.get("score") > 0.3) {
                entityId = target_json_object.get("entityId").toString();
            }else{
                System.out.println("人脸比对失败！");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("人脸比对失败！");
        }

        if(entityId!=null){
            if (dakaID.isIn(entityId)){
                System.out.println("20s内已打卡");
                return;
            }
            System.out.println("entityId:"+entityId);
            Checker checker = new Checker();
            try {
                CountDownLatch cd = new CountDownLatch(4);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("t:"+pic_url+faceX+faceY);
                        result_collar = aliService.checkCollar(pic_url,faceX,faceY);
                        cd.countDown();
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result_hair_color = aliService.checkHairColor(imageUrl);
                        cd.countDown();
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result_low_cloth = aliService.checkLowCloth(imageUrl);
                        cd.countDown();
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result_expression = aliService.checkExpression(imageUrl);
                        cd.countDown();
                    }
                }).start();
//                System.out.println(Thread.currentThread().getName()+":"+
//                        LocalTime.now().toString()+"-"+"主线程停止");
                cd.await();
//                System.out.println(Thread.currentThread().getName()+":"+
//                        LocalTime.now().toString()+"-"+"主线程开始");

                checker = creCheckrecord(entityId,pic_url,1,result_collar,result_hair_color,result_low_cloth,result_expression);
                if(dakaID.isIn(entityId)){
                    System.out.println("20s内已打卡");
                    return;
                }
                addChecker(checker);
                System.out.println("插入打卡记录成功:"+checker.toString());
                String check = json.toJSONString(checker);
                asyncRequestService.postValue(check);
                dakaID.add(entityId);
            }catch (Exception e){
                //e.printStackTrace();
                System.out.println("插入打卡记录异常！");
            }
            emailThread.sendEmail(checker);
        }
    }

    public Checker creCheckrecord(String usr_ID, String pic_url, int device_num, Integer collar,String hair_color, String low_cloth,String expression) {
        Checker checker = new Checker();
        java.util.Date date = new java.util.Date();
        LocalDate sqlDate = LocalDate.now();
        Time sqlTime = new Time(date.getHours(),date.getMinutes(),date.getSeconds());
        checker.setCheck_date(sqlDate);
        checker.setCheck_time(sqlTime);
        checker.setDevice_num(device_num);
        checker.setCheck_type(checktypeJudge(sqlTime));
        int time = 0;
        if(checker.getCheck_type()==-1){
            time = (int)(checker.getCheck_time().getTime()-moringendSqlTime.getTime())/60000;
        }else if(checker.getCheck_type()==-2){
            time = (int)(nightstartSqlTime.getTime()-checker.getCheck_time().getTime())/60000;
        }
        checker.setOvertime(time);
        checker.setUsr_ID(usr_ID);
        checker.setUsr_name(selectuserbyID(usr_ID).getUsr_name());
        checker.setCheck_pic(pic_url);
        checker.setCollar_round_neck(collar);
        checker.setHair_color(hair_color);
        checker.setLow_cloth(low_cloth);
        checker.setExpression(expression);
        return checker;
    }

    public int checktypeJudge(Time time){
        if(time==null){
            return 0;
        }
        if(time.after(moringstartSqlTime)&&time.before(moringendSqlTime)){
            return check_in;
        }else if(time.after(nightstartSqlTime)&&time.before(nightendSqlTime)){
            return check_out;
        }else if(time.after(moringendSqlTime)&&time.before(midTime)){
            return check_in_warn;
        }else if(time.after(midTime)&&time.before(nightstartSqlTime)){
            return check_out_warn;
        }
        return 0;
    }

}

