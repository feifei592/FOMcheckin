package com.daka.user.entity;

import java.time.LocalDateTime;

public class EmailObj extends User{
    private LocalDateTime time;
    private String type;
    private String cloths;
    private String hair;
    private String statement;

    @Override
    public String toString() {
        return "EmailObj{" +
                "time=" + time +
                ", type='" + type + '\'' +
                ", cloths='" + cloths + '\'' +
                ", hair='" + hair + '\'' +
                ", statement='" + statement + '\'' +
                '}';
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCloths() {
        return cloths;
    }

    public void setCloths(String cloths) {
        this.cloths = cloths;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
