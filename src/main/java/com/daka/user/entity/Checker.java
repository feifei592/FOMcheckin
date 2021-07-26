package com.daka.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;



import java.sql.Time;
import java.time.LocalDate;



public class Checker extends User{
    private Integer id;
    private String usr_name;
    private String usr_ID;
    private Time check_time;

    private LocalDate check_date;

    private Integer check_type;
    private Integer overtime;
    private Integer device_num;
    private String check_pic;
    private String pic_Name;

    private Integer collar_round_neck;
    private String hair_color;
    private String low_cloth;
    private String expression;

    @Override
    public String toString() {
        return "Checker{" +
                "id=" + id +
                ", usr_name='" + usr_name + '\'' +
                ", usr_ID='" + usr_ID + '\'' +
                ", check_time=" + check_time +
                ", check_date=" + check_date +
                ", check_type=" + check_type +
                ", overtime=" + overtime +
                ", device_num=" + device_num +
                ", check_pic='" + check_pic + '\'' +
                ", pic_Name='" + pic_Name + '\'' +
                ", hair_color='" + hair_color + '\'' +
                ", low_cloth='" + low_cloth + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsr_name() {
        return usr_name;
    }

    public void setUsr_name(String usr_name) {
        this.usr_name = usr_name;
    }

    public String getUsr_ID() {
        return usr_ID;
    }

    public void setUsr_ID(String usr_ID) {
        this.usr_ID = usr_ID;
    }

    public Time getCheck_time() {
        return check_time;
    }

    public void setCheck_time(Time check_time) {
        this.check_time = check_time;
    }

    public LocalDate getCheck_date() {
        return check_date;
    }

    public void setCheck_date(LocalDate check_date) {
        this.check_date = check_date;
    }

    public Integer getCheck_type() {
        return check_type;
    }

    public void setCheck_type(Integer check_type) {
        this.check_type = check_type;
    }

    public Integer getDevice_num() {
        return device_num;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime;
    }

    public void setDevice_num(Integer device_num) {
        this.device_num = device_num;
    }

    public String getCheck_pic() {
        return check_pic;
    }

    public void setCheck_pic(String check_pic) {
        this.check_pic = check_pic;
    }

    public String getPic_Name() {
        return pic_Name;
    }

    public void setPic_Name(String pic_Name) {
        this.pic_Name = pic_Name;
    }

    public String getHair_color() { return hair_color; }

    public void setHair_color(String hair_color) { this.hair_color = hair_color; }

    public String getLow_cloth() { return low_cloth; }

    public void setLow_cloth(String low_cloth) { this.low_cloth = low_cloth; }

    public String getExpression() { return expression;}

    public void setExpression(String expression) { this.expression = expression; }

    public Integer getCollar_round_neck() { return collar_round_neck; }

    public void setCollar_round_neck(Integer collar_round_neck) { this.collar_round_neck = collar_round_neck; }
}
