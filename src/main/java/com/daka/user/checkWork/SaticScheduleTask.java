package com.daka.user.checkWork;

import com.daka.user.entity.CheckWork;
import com.daka.user.entity.Checker;
import com.daka.user.service.CheckWorkService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Autowired
    InitMonthTable initMonthTable;

    @Autowired
    CheckWorkService checkWorkService;

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMM");

    @Scheduled(cron = "30 43 12 ? * *")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=20000)
    public void configureTasks() {
        String tableName_month = "checkwork_month_"+LocalDate.now().format(df);
        LocalDate sqlDate = LocalDate.now();
        //LocalDate sqlDate = LocalDate.parse("2021-07-25");
        List<Checker> checkers = null;
        try{
            checkers = checkWorkService.selectAllCheckerbydate(sqlDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, CheckWork> checkmap = checkWorkService.creCheckWork(checkers);
        List<CheckWork> checkWorks = checkWorkService.mergeCheckWork(checkmap);
        if(!checkWorks.isEmpty()){
            checkWorkService.add(checkWorks,tableName_month);
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now().toString());
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    //每月最后一天10点15运行
    public void configureTasks_month() {
        String tableName_month = "checkwork_month_"+LocalDate.now().format(df);
        try {
            initMonthTable.init(tableName_month);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
