package com.huawei.spider.center.parsers;

import com.huawei.spider.center.beans.UrlInfoBo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/8/29.
 */
public class BoBoParser {

    private String localFilePath = "D:/happy.txt";

    public BoBoParser() {
    }

    public BoBoParser(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public static void main(String[] args) {
        parse();
    }

    /**
     * url解析
     *
     * @throws Exception
     */
    public static List<UrlInfoBo> parse() {
        List<UrlInfoBo> result = new ArrayList<>();
        try {
            System.out.println("链接开始提取...");

            String domain = "http://dazhizhaoshang.com/";
            Document doc = Jsoup.connect(domain).get();
//            System.out.println(doc);
            if (doc != null) {
                String scripts = doc.select("script").attr("src");
                System.out.println(scripts);
                if (scripts.indexOf("fuck/ip") != -1) {
                    URL u = new URL(scripts);
                    InputStream ism = u.openStream();
                    BufferedInputStream bf = new BufferedInputStream(ism);
                    byte[] bytes = new byte[1024];
                    int len = bf.read(bytes);
                    StringBuffer sb = new StringBuffer();
                    while (len != -1) {
                        len = bf.read(bytes);
                        String str = new String(bytes, "utf-8");
                        sb.append(str);
                    }
                    String s = sb.toString();
                    System.out.println(sb);


                }

            }

//            Elements elements = doc.select(".caoporn_Maincontentfive .index_videoshow a");
//            String info = "";
//            for (Element a : elements) {
//                String desc = a.select("p").html();
//
//                String src = a.select("img").attr("src");
//                String img = domain + src;
//
//                String html = a.attr("href");
//                String url = getDownloadUrl(domain + html);
//                if (StringUtils.isNoneBlank(url)) {
//                    info += desc + "  img: " + img + "   downloadUrl: " + url + "\n";
//                    System.out.println(info);
//
//                    UrlInfoBo urlInfoBo = new UrlInfoBo();
//                    urlInfoBo.setUrl(url);
//                    String name = url.substring(url.lastIndexOf("/") + 1);
//                    urlInfoBo.setName(name);
//                    urlInfoBo.setDesc(desc);
//                    result.add(urlInfoBo);
//                }
//            }
//            if (StringUtils.isNotBlank(this.localFilePath)) {
//                FileUtil.writeToFile(this.localFilePath, info);// 写入文件
//            }
//
//            System.out.println("链接提取完成！");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取下载地址
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String getDownloadUrl(String url) throws Exception {
//        String url = "http://dcdiban.com/" + "view/index5880.html";
        if (StringUtils.isNoneBlank(url)) {
            Document doc = Jsoup.parse(new URL(url), 30000);
            if (doc != null) {
                Elements a = doc.select(".videourl a");
                if (a != null && a.size() > 0) {
                    String videourl = "http://dcdiban.com" + a.attr("href");
                    Document video = Jsoup.parse(new URL(videourl), 30000);
//                        System.out.println(video.toString());
                    if (video != null) {
                        Elements scripts = video.select("script[src^='/playdata']");
                        String js = "http://dcdiban.com" + scripts.attr("src");
                        URL u = new URL(js);
                        InputStream ism = u.openStream();
                        BufferedInputStream bf = new BufferedInputStream(ism);
                        byte[] bytes = new byte[1024];
                        int len = bf.read(bytes);
                        StringBuffer sb = new StringBuffer();
                        while (len != -1) {
                            len = bf.read(bytes);
                            String str = new String(bytes, "utf-8");
                            sb.append(str.trim());
                        }
                        String s = sb.toString();
                        if (StringUtils.isNoneBlank(s)) {
                            if (s.indexOf("https") != -1 && s.indexOf("$ckplayer") != -1) {
                                s = s.substring(s.indexOf("https"), s.indexOf("$ckplayer"));
                                //  "https://201806.53didi.com/20180826/16/1/xml/91_0632500b07744276b46f665e56a4e871.mp4";
                                return s;
                            }
                        }
                    } else {
                        System.out.println("没有获取到地址");
                    }
                }
            }
        }

        return "";
    }


    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }
}
