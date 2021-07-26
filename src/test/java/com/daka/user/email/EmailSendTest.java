package com.daka.user.email;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailSendTest extends TestCase {
    @Autowired
    EmailSend emailSend;
    @Test
    public void test(){
        try {
            //emailSend.sendSimpleMail("806859957@qq.com");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}