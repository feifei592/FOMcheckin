package com.daka.user.service;

import java.util.HashMap;
import java.util.Map;

public class PageService {
    public static Map<String,Object> setPageMap(int count,Object o){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","");
        map.put("count",count);
        map.put("data",o);
        return map;
    }
}
