package com.daka.user.entity;

public class WorkRank {
    private String usr_ID;
    private double workHours;
    private double workOverHours;
    private String usr_name;
    private String usr_pic;

    public String getUsr_ID() {
        return usr_ID;
    }

    public void setUsr_ID(String usr_ID) {
        this.usr_ID = usr_ID;
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

    @Override
    public String toString() {
        return "WorkRank{" +
                "usr_ID='" + usr_ID + '\'' +
                ", workHours=" + workHours +
                ", workOverHours=" + workOverHours +
                ", usr_name='" + usr_name + '\'' +
                ", usr_pic='" + usr_pic + '\'' +
                '}';
    }
}
