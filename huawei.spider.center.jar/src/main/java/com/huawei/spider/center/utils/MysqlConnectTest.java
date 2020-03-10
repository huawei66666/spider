package com.huawei.spider.center.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlConnectTest {

    public Connection connection;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // ?useUnicode=true&characterEncoding=utf-8
            return this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taokecms", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            MysqlConnectTest test = new MysqlConnectTest();
            Connection con = test.getConnection();
            if (con != null) {
                PreparedStatement statement = con.prepareStatement("select name from sys_user");
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    String name = set.getString("name");
                    System.out.println(name);
                }

                statement = con.prepareStatement("update sys_user set name = ? where id = ?");
                statement.setString(1, "哈哈哈");
                statement.setObject(2, 9);// 济南市场部
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
