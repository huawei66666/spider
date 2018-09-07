package com.huawei.spider.center.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;

/**
 * 功能：
 * 描述：
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年06月2018/6/29日 14:48
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class GetImgUtils {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        String imageSrc = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        Connection.Response response = Jsoup.connect(imageSrc).execute();
        byte[] img = response.bodyAsBytes();
        System.out.println(img.length);
        savaImage(img, "D:/360Downloads", "test.png");
    }

    public static void savaImage(byte[] img, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        try {
            //判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdir();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
