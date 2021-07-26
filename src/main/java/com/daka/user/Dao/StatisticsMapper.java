package com.daka.user.Dao;

import com.daka.user.entity.*;
import com.google.gson.JsonObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StatisticsMapper {
    List<WorkRank> selectWorkHours(@Param(value = "tableName") String tableName);
    List<CheckMonth> selectAllCheckMonth(@Param(value = "tableName") String tableName,int start,int end);
    int selectAllCount(String tableName);
    List<AbNormal> selectTotalAbnormal(@Param(value = "month") String month,@Param(value = "abtype")String abtype);
    List<Integer> selectAbnormalPersons(@Param(value = "month") String month);
}
