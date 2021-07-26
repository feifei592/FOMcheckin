package com.daka.user.checkWork;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaticScheduleTaskTest extends TestCase {

    @Autowired
    SaticScheduleTask scheduleTask;
    @Test
    public void testConfigureTasks() {

        scheduleTask.configureTasks();
        
    }
    @Test
    public void testConfigureTasks_month() {
        scheduleTask.configureTasks_month();
    }
}