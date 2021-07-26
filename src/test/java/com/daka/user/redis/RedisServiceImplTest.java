package com.daka.user.redis;

import com.alibaba.fastjson.JSONObject;
import com.daka.user.entity.Checker;
import junit.framework.TestCase;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceImplTest extends TestCase {

    private JSONObject json = new JSONObject();

    @Autowired
    private RedisService redisService;

    @Test
    public void testSet() {
        Checker checker = new Checker();
        checker.setUsr_ID("2028076");
        checker.setPic_Name("tangqi");
        java.util.Date date = new java.util.Date();
        Date sqlDate = new Date(date.getTime());
        Time sqlTime = new Time(date.getHours(),date.getMinutes(),date.getSeconds());
        checker.setCheck_time(sqlTime);

        Checker checker2 = new Checker();
        checker2.setUsr_ID("20283333");
        checker2.setPic_Name("tanddfc");
        checker2.setCheck_time(sqlTime);

        List<Checker> list = new ArrayList<>();
        list.add(checker);
        list.add(checker2);
        redisService.set("redis_obj_test",json.toJSONString(list));
    }

    @Test
    public void testGet() {
        String result = redisService.get("redis_obj_test");
        System.out.println(result);
    }

    public void testExpire() {
    }

    public void testRemove() {
    }
}