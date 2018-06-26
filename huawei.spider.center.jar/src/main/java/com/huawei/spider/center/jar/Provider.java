package com.huawei.spider.center.jar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：Provider
 * 描述：程序入口
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年06月2018/6/12日 10:51
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class Provider {

    private static final Logger logger = LoggerFactory.getLogger(Provider.class);

    public static void main(String[] args) {
        try {
            String profile = "development";
            if (args != null && args.length > 0) {
                profile = args[0];
            }
            System.setProperty("spring.profiles.active", profile);
            new ClassPathXmlApplicationContext("huawei-spider-center-context.xml");
            logger.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " service server started!");
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }

//        com.alibaba.dubbo.container.Main.main(args);
    }
}
