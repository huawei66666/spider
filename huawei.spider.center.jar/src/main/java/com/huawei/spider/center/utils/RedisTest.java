package com.huawei.spider.center.utils;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisTest {

    public static void test2() {
        Jedis j = new Jedis("10.211.55.7", 6379);
        List<String> list = j.mget("name", "age");
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        test2();
    }

}
