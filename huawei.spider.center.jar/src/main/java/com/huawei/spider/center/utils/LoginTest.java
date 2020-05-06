package com.huawei.spider.center.utils;

import com.alibaba.druid.support.json.JSONUtils;

public class LoginTest {

    public static void main(String[] args) {
        Login login = new Login();
        login.setUserName("超级管理员");
        login.setEndTime("20991231121212");
        login.setCallTime("20200415121212");
        login.setExpire(true);

        Inspect inspect = new Inspect();
        inspect.setCallTime("20200415121212");

        String loginJson = JSONUtils.toJSONString(login);
        String inspectJson = JSONUtils.toJSONString(inspect);
        System.out.println("loginJson: " + loginJson);
        System.out.println();
        System.out.println("inspectJson: " + inspectJson);
    }

}
