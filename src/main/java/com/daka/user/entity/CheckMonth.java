package com.daka.user.entity;

public class CheckMonth extends User{
    private int normalTimes;
    private int lateTimes;
    private int earlyTimes;
    private int outTimes;
    private int flexibleTimes;

    public int getNormalTimes() {
        return normalTimes;
    }

    public void setNormalTimes(int normalTimes) {
        this.normalTimes = normalTimes;
    }

    public int getLateTimes() {
        return lateTimes;
    }

    public void setLateTimes(int lateTimes) {
        this.lateTimes = lateTimes;
    }

    public int getEarlyTimes() {
        return earlyTimes;
    }

    public void setEarlyTimes(int earlyTimes) {
        this.earlyTimes = earlyTimes;
    }

    public int getOutTimes() {
        return outTimes;
    }

    public void setOutTimes(int outTimes) {
        this.outTimes = outTimes;
    }

    public int getFlexibleTimes() {
        return flexibleTimes;
    }

    public void setFlexibleTimes(int flexibleTimes) {
        this.flexibleTimes = flexibleTimes;
    }
}
