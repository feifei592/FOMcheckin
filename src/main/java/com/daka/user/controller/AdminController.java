package com.daka.user.controller;

import com.daka.user.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdministratorService administratorService;


    @ResponseBody
    @RequestMapping(path = "login",method = RequestMethod.POST)
    @CrossOrigin
    public Integer Login(@RequestBody Map<String,String> ad){
        String username = ad.get("username");
        String password = ad.get("password");
        System.out.println(username+"/"+password);
        return administratorService.Login(username,password);
    }
}
