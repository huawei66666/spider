package com.huawei.spider.center.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 功能：redis连接器
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/12日 10:44
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class RedisTest {

    @Test
    public void testRedis() throws Exception {
        String host = "127.0.0.1";
        int port = 6379;
        String password = "1234";
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        System.out.println(jedis.ping());
    }


}
