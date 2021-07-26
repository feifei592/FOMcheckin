package com.daka.user.service;

import com.daka.user.Dao.CheckWorkMapper;
import com.daka.user.entity.CheckWork;
import com.daka.user.entity.Checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckWorkService {
    @Autowired
    CheckWorkMapper checkWorkMapper;

    @Autowired
    CheckerService checkerService;

    private final static int check_in = 1;
    private final static int check_in_warn = -1;
    private final static int check_out = 2;
    private final static int check_out_warn = -2;
    private final static int out = 0;

    DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyyMM");


    private Time fleximoringTime = new Time(Time.valueOf("09:00:00").getTime());

    public List<Checker> selectAllCheckerbydate(LocalDate date){
        return checkWorkMapper.selectAllCheckerbydate(date);
    }
    public Integer isCheckIn(String usr_ID,LocalDate date){
        return checkWorkMapper.isCheckIn(usr_ID,date);
    }
    public List<Checker> selectAllCheckerbyStatus(Integer Status,String month,int start,int end) {
        return  checkWorkMapper.selectAllCheckerbyStatus(Status,month,start,end);
    }
    public Integer selectAllCheckerbyStatusAllcount(Integer Status,String month){
        return checkWorkMapper.selectAllCheckerbyStatusAllcount(Status,month);
    }

    public int add(List<CheckWork> checkWorks,String tableName){
        return checkWorkMapper.add(checkWorks,tableName);
    }
    public Integer selectFlexibleTimesEveryMonth(String tableName,String usr_ID){
        return checkWorkMapper.selectFlexibleTimesEveryMonth(tableName,usr_ID);
    }

    public Map<String, CheckWork> creCheckWork(List<Checker> checkers){
        Map<String, CheckWork> checkWorkMap = new HashMap<>();
        for(Checker checker : checkers){
            CheckWork checkWork;
            if(checkWorkMap.containsKey(checker.getUsr_ID())){
                checkWork = checkWorkMap.get(checker.getUsr_ID());
            }else{
                checkWork = new CheckWork();
            }
            checkWork.setUsr_ID(checker.getUsr_ID());
            checkWork.setCheck_date(checker.getCheck_date());
            if(checker.getCheck_type()==check_in||checker.getCheck_type()==check_in_warn){
                checkWork.getCheck_in_time_list().add(checker.getCheck_time());
            }else if(checker.getCheck_type()==check_out||checker.getCheck_type()==check_out_warn){
                checkWork.getCheck_out_time_list().add(checker.getCheck_time());
            }
            int expressScore = 0;
            if(checker.getExpression()!=null) {
                switch (checker.getExpression()) {
                    case "happiness":
                        expressScore = 1;
                        break;
                    case "sadness":
                        expressScore = -1;
                        break;
                    case "fear":
                        expressScore = -1;
                        break;
                    case "anger":
                        expressScore = -2;
                        break;
                    case "disgust":
                        expressScore = -2;
                        break;
                    default:
                        break;
                }
            }
            checkWork.setExpression(checkWork.getExpression()+expressScore);
            checkWorkMap.put(checkWork.getUsr_ID(),checkWork);
        }
        return checkWorkMap;
    }
    public List<CheckWork> mergeCheckWork(Map<String, CheckWork> checkWorkMap){
        String tableName_month = "checkwork_month_"+LocalDate.now().format(df2);
        List<CheckWork> checkWorks = new ArrayList<>();
        for(String usr_ID:checkWorkMap.keySet()){
            CheckWork checkWork = checkWorkMap.get(usr_ID);
            Time inTime = checkWork.getCheck_in_time_list().peek();
            checkWork.setCheck_in_time(inTime);
            Time outTime = checkWork.getCheck_out_time_list().peek();
            checkWork.setCheck_out_time(outTime);
            Integer flexibleTimes = selectFlexibleTimesEveryMonth(tableName_month,checkWork.getUsr_ID());
            if (flexibleTimes==null){
                flexibleTimes = 0;
            }
            try{
                if(checkWork.getCheck_in_time()!=null&&checkWork.getCheck_out_time()!=null){
                    long timeMills = outTime.getTime()-inTime.getTime();
                    double timeHours = timeMills/3600000.0;
                    checkWork.setWorkHours(timeHours);
                    double workoverhours = timeHours-9.0;
                    checkWork.setWorkOverHours(workoverhours>0?workoverhours:0);
                }
                if(checkerService.checktypeJudge(inTime) == check_in){
                    checkWork.setIsBeLate(0);
                    checkWork.setIsFlexible(0);
                    checkWork.setIsOut(0);
                }else if(inTime!=null&&inTime.before(fleximoringTime)&&checkWork.getWorkHours()>=9&&(flexibleTimes<10)){
                    checkWork.setIsBeLate(0);
                    checkWork.setIsFlexible(1);
                    checkWork.setIsOut(0);
                }else if(checkerService.checktypeJudge(inTime) == out){
                    checkWork.setIsOut(1);
                    checkWork.setIsBeLate(0);
                    checkWork.setIsFlexible(0);
                }else{
                    checkWork.setIsOut(0);
                    checkWork.setIsBeLate(1);
                    checkWork.setIsFlexible(0);
                }
                if(checkerService.checktypeJudge(outTime) == check_out){
                    checkWork.setIsLeaveEarly(0);
                }else if(checkerService.checktypeJudge(outTime) == check_out_warn){
                    checkWork.setIsLeaveEarly(1);
                }else if(checkerService.checktypeJudge(outTime) == out){
                    checkWork.setIsLeaveEarly(0);
                    checkWork.setIsBeLate(0);
                    checkWork.setIsOut(1);
                }
                if(checkWork.getIsBeLate()==0&&checkWork.getIsLeaveEarly()==0&&checkWork.getIsOut()==0){
                    checkWork.setIsNormal(1);
                }else{
                    checkWork.setIsNormal(0);
                }
            }catch (Exception e){
                e.printStackTrace();
                checkWork.setIsNormal(0);
            }
            checkWorks.add(checkWork);
        }
        return checkWorks;
    }
}
