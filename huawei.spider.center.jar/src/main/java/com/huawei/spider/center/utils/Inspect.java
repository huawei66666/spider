package com.huawei.spider.center.utils;

import java.io.Serializable;

public class Inspect implements Serializable {

    public String editionName;
    public String editionNumber;
    public String downloadAddress;
    public String callTime;
    public String token;

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public String getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(String editionNumber) {
        this.editionNumber = editionNumber;
    }

    public String getDownloadAddress() {
        return downloadAddress;
    }

    public void setDownloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress;
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
