package com.daka.user.controller;

import com.daka.user.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wechat")
public class WechatController {
    @Autowired
    WechatService wechatService;

    @RequestMapping(path = "addOpenID", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public int add(String usr_ID,String openID){
        try{
            System.out.println("访问1");
            return wechatService.add(usr_ID,openID);
        }catch (Exception e){
            return -1;
        }
    }

    @RequestMapping(path = "getOpenID", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public List<String> getAllopenID(){
        return wechatService.selectAllopenID();
    }
}
