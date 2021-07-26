package com.daka.user.service;

import com.daka.user.Dao.StatisticsMapper;
import com.daka.user.entity.AbNormal;
import com.daka.user.entity.CheckMonth;
import com.daka.user.entity.WorkRank;
import com.google.gson.JsonObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    @Autowired
    StatisticsMapper statisticsMapper;

    public List<WorkRank> selectWorkHours(String tableName){
        return statisticsMapper.selectWorkHours(tableName);
    }
    public List<CheckMonth> selectAllCheckMonth(String tableName,int start,int end){
        return statisticsMapper.selectAllCheckMonth(tableName,start,end);
    }
    public int selectAllCount(String tableName){
        return statisticsMapper.selectAllCount(tableName);
    }
    public List<AbNormal> selectTotalAbnormal(String month,String abtype){
        return statisticsMapper.selectTotalAbnormal(month,abtype);
    }
    public List<Integer> selectAbnormalPersons(String month){
        return statisticsMapper.selectAbnormalPersons(month);
    }
}
