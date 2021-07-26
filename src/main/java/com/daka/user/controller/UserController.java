package com.daka.user.controller;


import com.daka.user.entity.User;
import com.daka.user.service.CheckerService;
import com.daka.user.service.PageService;
import com.daka.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CheckerService checkService;
    /**
     * 添加员工信息
     *
     * @param user usr_name,usr_ID,usr_pic,usr_job
     * @return
     */
    @RequestMapping(path = "add", method = RequestMethod.POST)
    @CrossOrigin
    public int add(User user) {
        System.out.println(user.toString());
        try {
            if(checkService.selectuserbyID(user.getUsr_ID())!=null){
                return -1;
            }
            user.setUsr_pic(userService.uploadFile(user.getUsr_ID(),user.getFile()));
            return userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除员工信息
     *
     * @param ids 员工数据id
     * @return
     */
    @CrossOrigin
    @RequestMapping(path = "delete",method = RequestMethod.POST)
    public int delete(@RequestBody Map<String,String> ids){
        System.out.println(ids.get("ids"));
        String ids_str= ids.get("ids");
        String[] ids_str_arr;
        if(ids_str.length()==1){
            ids_str_arr = new String[]{ids_str};
        }else {
            ids_str_arr= ids_str.split(",");
        }
        ArrayList<Integer> ids_arrayList = new ArrayList<>();
        for (String id :ids_str_arr) {
            int id_int = Integer.parseInt(id);
            ids_arrayList.add(id_int);
        }
        return userService.deleteUser(ids_arrayList);
    }
    /**
     * 修改员工数据
     *
     * @param user
     * @return
     */
    @CrossOrigin
    @RequestMapping(path = "update", method = RequestMethod.POST)
    public int update(User user) {
        try{
            System.out.println(user.toString());
            if(user.getFile()!=null){
                user.setUsr_pic(userService.uploadFile(userService.selectIDbyid(user.getId()),user.getFile()));
            }
            return userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @CrossOrigin
    @RequestMapping(path = "updateOpenID", method = RequestMethod.POST)
    public String updateOpenID(String usr_ID,String usr_openid){
        try {
            if(userService.updateOpenID(usr_ID,usr_openid)>0){
                return "绑定成功！";
            }else{
                return "绑定失败";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "绑定失败！";
        }
    }
    /**
     * 分页查询
     * @return
     */
    @RequestMapping(path = "selectAll", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public Map<String, Object> selectAll(String select_str,Integer page,Integer limit) {
        int start = 0;
        int end = Integer.MAX_VALUE;
        if (page!=null&&limit!=null){
             start = (page - 1) * limit;
             end = limit;
        }
        return PageService.setPageMap(userService.selectAllCount(select_str),userService.selectAll(select_str,start,end));
    }
}

