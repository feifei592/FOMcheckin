package com.daka.user.Dao;

import com.daka.user.entity.CheckWork;
import com.daka.user.entity.Checker;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Repository
public interface CheckWorkMapper {
    List<Checker> selectAllCheckerbydate(LocalDate date);
    int add(@Param("cks")List<CheckWork> checkWorks,@Param("tableName")String tableName);
    List<Checker> selectAllCheckerbyStatus(@Param("Status")Integer Status,@Param("month") String month,int start,int end);
    Integer selectAllCheckerbyStatusAllcount(@Param("Status")Integer Status,@Param("month") String month);
    Integer selectFlexibleTimesEveryMonth(String tableName,String usr_ID);
    Integer isCheckIn(String usr_ID,LocalDate date);
}
