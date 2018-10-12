package com.huawei.spider.center.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RenameFileUtil {

    public static void main(String[] args) {
        String path = "/media/wei/d/spider/lamadaogou/css";
        List<String> fileList = getFiles(path);
        System.out.println(fileList.size());
        for (String file : fileList) {
//            System.out.println(file);
            rename(file);
        }
    }

    /**
     * 重命名
     *
     * @param path
     */
    public static void rename(String path) {
        if (StringUtils.isNoneBlank(path) && path.indexOf("?v=") != -1) {
            System.out.println(path);
            String filename = path.substring(path.lastIndexOf("/") + 1);
            String newname = filename.substring(0, filename.indexOf("?v="));
            String basePath = path.substring(0, path.lastIndexOf("/"));
            File oldfile = new File(path);
            File newfile = new File(basePath + "/" + newname);
            if (!oldfile.exists()) {
                System.out.println("文件不存在！");
                return;//重命名文件不存在
            }
            if (newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname + "已经存在！");
                return;
            } else {
                oldfile.renameTo(newfile);
                System.out.println("文件：" + newfile + "重命名成功！");
            }
        }
    }

    /**
     * 遍历所有文件
     *
     * @param path
     * @return
     */
    public static List<String> getFiles(String path) {
        List<String> fileList = new ArrayList<>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
//                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
//                    System.out.println("文件:" + file2.getAbsolutePath());
                    fileList.add(file2.getAbsolutePath());
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
//                        System.out.println("文件:" + file2.getAbsolutePath());
                        fileList.add(file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }

        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        return fileList;
    }
}
