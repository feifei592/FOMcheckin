package com.daka.user.entity;

import org.springframework.web.multipart.MultipartFile;

public class User {
    private Integer id;
    private String usr_ID;
    private String usr_name;
    private String usr_pic;
    private String usr_job;
    private MultipartFile file = null;
    private String email_address;
    private String usr_openid;

    @Override
    public String toString() {
        return "id is "+id+"; usr_ID is "+ usr_ID + "; usr_name is "+ usr_name + "; usr_pic is "+ usr_pic
                + "; usr_job is "+ usr_job;
    }

    public MultipartFile getFile(){return file;};

    public void setFile(MultipartFile file){this.file = file;}

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

    public String getUsr_name() {
        return usr_name;
    }

    public void setUsr_name(String usr_name) {
        this.usr_name = usr_name;
    }

    public String getUsr_pic() {
        return usr_pic;
    }

    public void setUsr_pic(String usr_pic) {
        this.usr_pic = usr_pic;
    }

    public String getUsr_job() {
        return usr_job;
    }

    public void setUsr_job(String usr_job) {
        this.usr_job = usr_job;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getUsr_openid() {
        return usr_openid;
    }

    public void setUsr_openid(String usr_openid) {
        this.usr_openid = usr_openid;
    }
}
