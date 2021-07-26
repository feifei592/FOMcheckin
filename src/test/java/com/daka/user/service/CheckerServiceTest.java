package com.daka.user.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckerServiceTest {
    @Autowired
    CheckerService checkerService;
    @Test
    public void checkCollar() {
        checkerService.checkCompare("D:\\meiya\\image\\checkPic\\192.168.43.74_01_20210720173322698_FACE_DETECTION.jpg");
    }
}