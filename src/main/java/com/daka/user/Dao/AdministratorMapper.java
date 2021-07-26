package com.daka.user.Dao;

import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorMapper {
    int Login(String username,String password);
}
