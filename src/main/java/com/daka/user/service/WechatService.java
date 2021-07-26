package com.daka.user.service;

import com.daka.user.Dao.WechatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WechatService {
    @Autowired
    WechatMapper wechatMapper;
    public int add(String usr_ID,String openID){
        return wechatMapper.add(usr_ID,openID);
    }
    public List<String> selectAllopenID(){
        return wechatMapper.selectAllopenID();
    }
}
