package com.huawei.spider.center.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesTest {

    /**
     * 通过文件读取InputStream
     *
     * @throws Exception
     */
    public static void m1() throws Exception {
        String url = "/mydoc/workspace/idea/study/spider/huawei.spider.center.jar/src/main/resources/system.properties";
        InputStream in = new BufferedInputStream(new FileInputStream(url));
        Properties p = new Properties();
        p.load(in);
        String username = p.getProperty("jdbc.username");
        System.out.println(username);
    }

    /**
     * 通过ClassLoader的3种获取流方法
     *
     * @throws Exception
     */
    public static String m2() throws Exception {
        String url = "system.properties";
        InputStream in = PropertiesTest.class.getClassLoader().getResourceAsStream(url);
//        InputStream in = PropertiesTest.class.getResourceAsStream(url);// 会报错，可能路径不对
//        InputStream in = ClassLoader.getSystemResourceAsStream(url);

        if(in != null) {
            Properties p = new Properties();
            p.load(in);
            return p.getProperty("");
        }
        return null;
    }

    /**
     * 使用java.util.ResourceBundle类的构造函数
     * @throws Exception
     */
    public static void m3() throws Exception {
        String url = "system";
        ResourceBundle bundle = ResourceBundle.getBundle(url);
        String username = bundle.getString("jdbc.username");
        System.out.println(username);
    }

    /**
     * 使用java.util.PropertyResourceBundle类的构造函数
     * @throws Exception
     */
    public static void m4() throws Exception {
        String url = "/mydoc/workspace/idea/study/spider/huawei.spider.center.jar/src/main/resources/system.properties";
        InputStream in = new BufferedInputStream(new FileInputStream(url));
        PropertyResourceBundle bundle = new PropertyResourceBundle(in);
        String username = bundle.getString("jdbc.username");
        System.out.println(username);
    }

    public static void main(String[] args) {
        try {
            m4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
