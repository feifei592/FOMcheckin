package com.daka.user.service;

import com.daka.user.Dao.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    AdministratorMapper administratorMapper;
    public int Login(String username,String password){
        return administratorMapper.Login(username,password);
    }
}
