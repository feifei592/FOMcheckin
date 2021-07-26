package com.daka.user.Dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatMapper {
    int add(String usr_ID,String openID);
    List<String> selectAllopenID();
}
