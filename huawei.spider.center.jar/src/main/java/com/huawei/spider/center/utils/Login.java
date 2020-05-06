package com.huawei.spider.center.utils;

import java.io.Serializable;

public class Login implements Serializable {

    public String userName;
    public int controlNumber;
    public String startTime;
    public String endTime;
    public Boolean isExpire;
    public String callTime;
    public String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(int controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getExpire() {
        return isExpire;
    }

    public void setExpire(Boolean expire) {
        isExpire = expire;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
