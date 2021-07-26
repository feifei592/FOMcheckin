package com.daka.user;

import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class TimeTest {
    @Test
    public void test(){
        LocalTime a = LocalTime.parse("09:37:21");
        LocalTime b = LocalTime.parse("10:36:29");
        System.out.println(MINUTES.between(a,b));
        File directory = new File("");// 参数为空
        String courseFile = directory.getAbsolutePath();
        System.out.println(courseFile+"\\src\\main\\resources\\pythonFile\\");
    }
}
