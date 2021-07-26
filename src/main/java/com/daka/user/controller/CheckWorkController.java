package com.daka.user.controller;


import com.daka.user.entity.CheckWork;
import com.daka.user.entity.Checker;
import com.daka.user.service.CheckWorkService;
import com.daka.user.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("checkWork")
public class CheckWorkController {

    @Autowired
    private CheckWorkService checkWorkService;

    @RequestMapping(path = "selectAllCheckerbyStatus",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> selectAllCheckerbyStatus(@RequestParam("Status") String Status, @RequestParam("select_month") String month,
                                        Integer page, Integer limit){
        System.out.println(Status+":"+month);
        int start = 0;
        int end = Integer.MAX_VALUE;
        if (page!=null&&limit!=null){
            start = (page - 1) * limit;
            end = limit;
        }
        try {
            List<Checker> checkers = checkWorkService.selectAllCheckerbyStatus(Integer.valueOf(Status),month,start,end);
            return PageService.setPageMap(checkWorkService.selectAllCheckerbyStatusAllcount(Integer.valueOf(Status),month),checkers);
        }catch (Exception e){
            e.printStackTrace();
            return PageService.setPageMap(0,new ArrayList<>());
        }
    }

    @RequestMapping(path = "isCheckIn",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public Integer isCheckIn(String usr_ID,String usr_openid){
        LocalDate date = LocalDate.now();
        return checkWorkService.isCheckIn(usr_ID,date);
    }
}
