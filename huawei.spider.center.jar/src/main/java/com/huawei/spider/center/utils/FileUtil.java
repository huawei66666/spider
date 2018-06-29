package com.huawei.spider.center.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 功能：文件工具类
 * 日期：2018年06月2018/6/25日 16:41
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 写到文件
     *
     * @param content
     */
    public static void writeToFile(String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                System.out.println("文件内容为空！");
                logger.error("文件内容为空！");
                return;
            }
            String localPath = "D:/dytt.txt";
            File file = new File(localPath);
            if (file.exists()) {
//                PrintStream printStream = new PrintStream(new FileOutputStream(file), true, "UTF-8");
                PrintStream printStream = new PrintStream(new FileOutputStream(file));
                printStream.print(content);
                printStream.close();
            }
        } catch (Exception e) {
            logger.error("内容写到文件失败！", e);
            e.printStackTrace();
        }
    }
}
