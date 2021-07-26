package com.daka.user.service;

import com.daka.user.entity.WorkRank;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTest extends TestCase {

    @Autowired
    StatisticsService statisticsService;

    @Test
    public void testSelectWorkHours() {
        String tableName_month = "checkwork_"+ LocalDate.now().getMonth().toString().replace("-","");
        List<WorkRank> list =  statisticsService.selectWorkHours(tableName_month);
        System.out.println(list.toString());
    }
}