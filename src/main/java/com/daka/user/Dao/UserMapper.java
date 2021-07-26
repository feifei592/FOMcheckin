package com.daka.user.Dao;

import com.daka.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    int saveUser(User user);
    List<User> selectUser(String str,int start,int end);
    int updateUser(User user);
    int deleteUser(Integer id);
    List<User> selectAll(String select_str, int start, int end);
    int selectAllCount(String select_str);
    String selectIDbyid(Integer id);
    Integer updateOpenID(String usr_ID,String open_ID);
}
