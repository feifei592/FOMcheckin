package com.daka.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.daka.user.entity.AbNormal;
import com.daka.user.entity.CheckMonth;
import com.daka.user.entity.CheckWork;
import com.daka.user.entity.WorkRank;
import com.daka.user.service.PageService;
import com.daka.user.service.StatisticsService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("statistic")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    /**
     * 统计加班时长与排名
     * @param month 选择月份 eg:2021-07
     * @return
     */

    @RequestMapping(path = "select_WorkHours", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public List<WorkRank> selectWorkHours(@RequestParam("select_month") String month){
        try {
            String tableName_month = "checkwork_month_"+month.replace("-","");
            return statisticsService.selectWorkHours(tableName_month);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    /**
     * 按月对所有人考勤统计
     * @param month
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(path = "selectAllCheckMonth", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> selectAllCheckMonth(@RequestParam("select_month") String month,
                                   Integer page, Integer limit){
        int start = 0;
        int end = Integer.MAX_VALUE;
        if (page!=null&&limit!=null){
            start = (page - 1) * limit;
            end = limit;
        }
        try {
            String tableName_month = "checkwork_month_"+month.replace("-","");
            List<CheckMonth> checkMonths = statisticsService.selectAllCheckMonth(tableName_month,start,end);
            return PageService.setPageMap(statisticsService.selectAllCount(tableName_month),checkMonths);
        }catch (Exception e){
            e.printStackTrace();
            return PageService.setPageMap(0,null);
        }
    }

    /**
     *查询着装异常打卡记录
     * @param month
     * @param abtype hair_color:发色,low_cloth:裤子,collar:衣领
     * @return
     */
    @RequestMapping(path = "select_TotalAbnormal", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public List<AbNormal> selectTotalAbnormal(@RequestParam("select_month") String month,String abtype){
        List<AbNormal> result = new ArrayList<>();
        try {
            if(abtype.equals("all")){
                List<AbNormal> result1 = statisticsService.selectTotalAbnormal(month,"hair_color");
                List<AbNormal> result2 = statisticsService.selectTotalAbnormal(month,"low_cloth");
                List<AbNormal> result3 = statisticsService.selectTotalAbnormal(month,"collar");
                result.addAll(result1);
                result.addAll(result2);
                result.addAll(result3);
            }else{
                result = statisticsService.selectTotalAbnormal(month,abtype);
            }
            Collections.sort(result);
            for(AbNormal abNormal : result){
                abNormal.setPic_name(abNormal.getPic_name());
            }
            return result;
        }catch (Exception e){
            return result;
        }
    }

    /**
     * 统计异常着装的人数
     * @param month
     * @return 分三个类别返回
     */
    @RequestMapping(path = "selectAbnormalPersons", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JSONObject selectAbnormalPersons(@RequestParam("select_month") String month){
        try {
            List<Integer> result=statisticsService.selectAbnormalPersons(month);
            JSONObject json = new JSONObject();
            json.fluentPut("hair_color",result.get(0));
            json.fluentPut("low_cloth",result.get(1));
            json.fluentPut("collar",result.get(2));
            return json;
        }catch (Exception e){
            return new JSONObject();
        }
    }
}
