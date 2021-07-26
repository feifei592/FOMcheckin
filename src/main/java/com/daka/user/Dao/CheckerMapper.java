package com.daka.user.Dao;

import com.daka.user.entity.Checker;
import com.daka.user.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CheckerMapper {
    int addChecker(Checker checker);
    List<Checker> selectChecker(String str,Date startDate, Date endDate,int start,int end);
    int selectAllCount(String str,Date startDate,Date endDate);
    User selectuserbyID(String usr_ID);
    String selectStatebyExpression(String expression);
}
