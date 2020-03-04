package com.huawei.spider.center.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：文件工具类
 * 日期：2018年06月2018/6/25日 16:41
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

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
            String localPath = "/mydoc/dytt.txt";
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

    /**
     * 写入文件
     *
     * @param localPath
     * @param content
     */
    public static void writeToFile2(String localPath, String content) {
        if (StringUtils.isEmpty(localPath) || StringUtils.isEmpty(content)) return;
        try {
            File file = new File(localPath);
            File parent = new File(file.getParent());
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists() || !file.isDirectory()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 写到文件
     *
     * @param content
     */
    public static void writeToFile(String localPath, String content) {
        File file = null;
        try {
            if (StringUtils.isEmpty(localPath)) {
                return;
            }
            if (StringUtils.isEmpty(content)) {
                System.out.println("文件内容为空！");
                logger.error("文件内容为空！");
                return;
            }
            file = new File(localPath);
            File parent = new File(file.getParent());// 输出的文件
            parent.mkdirs();

            if (!file.exists() && !file.isDirectory()) {
                file.createNewFile();
            }

            System.out.println("正在写入文件：" + file.getName() + "...");
//            PrintStream printStream = new PrintStream(new FileOutputStream(file), true, "UTF-8");
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.print(content);
            printStream.close();
            System.out.println("写入文件：" + file.getName() + "完成！");
        } catch (Exception e) {
            logger.error("内容写到文件：" + file.getName() + "失败！", e);
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹下的空文件
     *
     * @param folder
     */
    public static void deleteEmptyFiles(String folder) {
        try {
            List<File> fileList = new ArrayList<>();
            if (StringUtils.isNoneBlank(folder)) {
                File f = new File(folder);
                if (f.exists() && f.isDirectory()) {
                    File[] files = f.listFiles();
                    for (File fi : files) {
                        if (!fi.isHidden()) {
                            BufferedReader reader = new BufferedReader(new FileReader(fi));
//                            System.out.println("reader lenth: " + reader.readLine());
//                            System.out.println("length: " + fi.length());
                            if (fi.length() == 0 || StringUtils.isBlank(reader.readLine())) {
                                fi.delete();
                                fileList.add(fi);
                            }
                        }
                    }
                }
            }
            System.out.println("文件删除完成！一共删除了：" + fileList.size() + "个文件！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     * @param localPath
     */
    public static void readFile(String localPath) {
        if (StringUtils.isEmpty(localPath)) return;
        try {
            File file = new File(localPath);
            if (!file.exists()) return;

            // 读文件方式一：
//            Reader reader = new FileReader(file);
//            char[] chars = new char[1024];
//            int len = reader.read(chars);
//            System.out.println(new String(chars, 0, len));

            // 读文件方式二：
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            for(String line;StringUtils.isNoneBlank(line = reader.readLine());) {
//                System.out.println(line);
//            }

            // 读文件方式二：
            InputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            System.out.println(new String(bytes));
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String filepath = "/mydoc/videos/a/m3u8";
//        deleteEmptyFiles(filepath);

        String filepath2 = "/mydoc/test/a/b/c/test.txt";
        String s = "哇啊无哇无哈哈哈\n 呵呵呵呵呵呵呵呵";
        writeToFile2(filepath2, s);
//        readFile(filepath2);

    }

}
