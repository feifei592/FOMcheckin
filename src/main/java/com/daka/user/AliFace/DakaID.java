package com.daka.user.AliFace;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DakaID {
    private Set<String> DakaIDList = new HashSet<>();

    public synchronized void add(String usr_ID){
        DakaIDList.add(usr_ID);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                DakaIDList.remove(usr_ID);
            }
        }).start();
    }

    public synchronized boolean isIn(String usr_ID){
        if(DakaIDList.contains(usr_ID)){
            return true;
        }else{
            return false;
        }
    }

}
