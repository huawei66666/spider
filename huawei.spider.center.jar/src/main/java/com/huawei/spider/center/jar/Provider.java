package com.huawei.spider.center.jar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：Provider
 * 日期：2018年06月2018/6/12日 10:51
 */
public class Provider {

    private static final Logger logger = LoggerFactory.getLogger(Provider.class);

    public static void main(String[] args) {
        try {
            logger.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " service server started!");
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
