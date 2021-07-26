package com.daka.user.controller;


import com.daka.user.entity.Checker;

import com.daka.user.service.AsyncRequestService;
import com.daka.user.service.CheckerService;

import com.daka.user.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("checker")
public class CheckerController {
    @Autowired
    private CheckerService checkerService;

    @Autowired
    private AsyncRequestService asyncRequestService;

    @RequestMapping(path = "selectChecker",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> selectChecker(String select_str, String startDate, String endDate,
                                             Integer page, Integer limit){
        int start = 0;
        int end = Integer.MAX_VALUE;
        if (page!=null&&limit!=null){
            start = (page - 1) * limit;
            end = limit;
        }
        Date startDate_d = null;
        Date endDate_d = new Date(new java.util.Date().getTime());
        if(startDate!=null){
            startDate_d = Date.valueOf(startDate);
        }
        if (endDate!=null){
            endDate_d = Date.valueOf(endDate);
        }
        List<Checker> checkers = checkerService.selectChecker(select_str,startDate_d,endDate_d,start,end);
        for(Checker checker : checkers){
            checker.setPic_Name(checker.getCheck_pic().split("\\\\\\\\")[4]);
        }
        return PageService.setPageMap(checkerService.selectAllCount(select_str,startDate_d,endDate_d),checkers);
    }

    @RequestMapping(path = "getChecker",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public String getValue(){
        //System.out.println("ajax访问");
        String msg = null;
        Future<String> result = null;
        try{
            result = asyncRequestService.getValue();
            msg = result.get(10, TimeUnit.SECONDS);
        }catch (Exception e){
            //e.printStackTrace();
        }finally {
            if (result != null){
                result.cancel(true);
            }
        }
        asyncRequestService.postValue(null);
        return msg;
    }
}
