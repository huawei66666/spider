package com.huawei.spider.center.test;

import com.huawei.spider.center.utils.LogIntercepter;
import com.huawei.spider.center.utils.UserDao;
import com.huawei.spider.center.utils.UserDaoImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class JdkProxyTest {

    @Test
    public void testJdk() throws Exception {
        UserDao dao = new UserDaoImpl();
        LogIntercepter intercepter = new LogIntercepter();
        intercepter.setTarget(dao);
        UserDao proxy = (UserDao) Proxy.newProxyInstance(dao.getClass().getClassLoader(), new Class[]{ UserDao.class}, intercepter);
        proxy.save();
        proxy.delete(1);
    }
}
