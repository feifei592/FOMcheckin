package com.daka.user.entity;

import java.sql.Date;
import java.sql.Time;

public class AbNormal extends User implements Comparable<AbNormal>{
    private String abtype;
    private Date date;
    private Time time;
    private String pic_name;

    public String getAbtype() {
        return abtype;
    }

    public void setAbtype(String abtype) {
        this.abtype = abtype;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    @Override
    public int compareTo(AbNormal o) {
        int i = (int)(this.date.getTime()-o.getDate().getTime());
        if(i==0){
            return -(int)(this.time.getTime()-o.getTime().getTime());
        }
        return -i;
    }
}
