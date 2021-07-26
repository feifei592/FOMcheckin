package com.daka.user.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.PriorityQueue;

public class CheckWork extends User{
    private Integer id;
    private String usr_ID;
    private Time check_in_time;
    private Time check_out_time;
    private PriorityQueue<Time> check_in_time_list;
    private PriorityQueue<Time> check_out_time_list;
    private LocalDate check_date;
    private Integer isBeLate;
    private Integer isLeaveEarly;
    private Integer isNormal;
    private Integer isFlexible;
    private Integer isOut;
    private double workHours;
    private double workOverHours;
    private int hair_color;
    private int low_cloth;
    private int expression;

    public CheckWork() {
        check_in_time_list = new PriorityQueue<>(20, new Comparator<Time>() {
            @Override
            public int compare(Time o1, Time o2) {
                if(o2.after(o1)){
                    return -1;
                }else if(o2.before(o1)){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        check_out_time_list = new PriorityQueue<>(20, new Comparator<Time>() {
            @Override
            public int compare(Time o1, Time o2) {
                if(o2.after(o1)){
                    return 1;
                }else if(o2.before(o1)){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsr_ID() {
        return usr_ID;
    }

    public void setUsr_ID(String usr_ID) {
        this.usr_ID = usr_ID;
    }

    public Time getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(Time check_in_time) {
        this.check_in_time = check_in_time;
    }

    public Time getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(Time check_out_time) {
        this.check_out_time = check_out_time;
    }

    public LocalDate getCheck_date() {
        return check_date;
    }

    public void setCheck_date(LocalDate check_date) {
        this.check_date = check_date;
    }

    public Integer getIsBeLate() {
        return isBeLate;
    }

    public void setIsBeLate(Integer isBeLate) {
        this.isBeLate = isBeLate;
    }

    public Integer getIsLeaveEarly() {
        return isLeaveEarly;
    }

    public void setIsLeaveEarly(Integer isLeaveEarly) {
        this.isLeaveEarly = isLeaveEarly;
    }

    public Integer getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Integer isNormal) {
        this.isNormal = isNormal;
    }

    public Integer getIsFlexible() {
        return isFlexible;
    }

    public void setIsFlexible(Integer isFlexible) {
        this.isFlexible = isFlexible;
    }

    public Integer getIsOut() {
        return isOut;
    }

    public void setIsOut(Integer isOut) {
        this.isOut = isOut;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getWorkOverHours() {
        return workOverHours;
    }

    public void setWorkOverHours(double workOverHours) {
        this.workOverHours = workOverHours;
    }

    public PriorityQueue<Time> getCheck_in_time_list() {
        return check_in_time_list;
    }

    public void setCheck_in_time_list(PriorityQueue<Time> check_in_time_list) {
        this.check_in_time_list = check_in_time_list;
    }

    public PriorityQueue<Time> getCheck_out_time_list() {
        return check_out_time_list;
    }

    public void setCheck_out_time_list(PriorityQueue<Time> check_out_time_list) {
        this.check_out_time_list = check_out_time_list;
    }

    public int getHair_color() {
        return hair_color;
    }

    public void setHair_color(int hair_color) {
        this.hair_color = hair_color;
    }

    public int getLow_cloth() {
        return low_cloth;
    }

    public void setLow_cloth(int low_cloth) {
        this.low_cloth = low_cloth;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }
}
