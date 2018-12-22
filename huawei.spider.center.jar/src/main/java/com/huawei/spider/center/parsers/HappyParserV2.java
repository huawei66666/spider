package com.huawei.spider.center.parsers;

import com.huawei.spider.center.beans.UrlInfoBo;
import com.huawei.spider.center.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huawei on 2018/9/8.
 */
public class HappyParserV2 {

    //    private String localFilePath = "D:/happy.txt";
    String localFilePath = "/mydoc/happy.txt";

    public HappyParserV2() {
    }

    public HappyParserV2(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    /**
     * url解析
     *
     * @throws Exception
     */
    public List<UrlInfoBo> parse() throws Exception {
        List<UrlInfoBo> result = new ArrayList<>();
        try {
            System.out.println("链接开始提取...");

            String domain = "http://dcdiban.com";
            Document doc = Jsoup.parse(new URL(domain).openStream(), "gb2312", domain);
            Elements elements = doc.select(".movie_list ul li a:has(img)");
            String info = "";
            for (Element a : elements) {
                String href = a.attr("href");
//                System.out.println(domain + href);
                String url = getDownloadUrl(domain + href);
                if (StringUtils.isNoneBlank(url)) {
                    System.out.println(url);
                    info += " downloadUrl: " + url + "\n";
                    UrlInfoBo urlInfoBo = new UrlInfoBo();
                    urlInfoBo.setUrl(url);
                    String name = url.substring(url.lastIndexOf("/") + 1);
                    urlInfoBo.setName(name);
                    result.add(urlInfoBo);
                }
            }
            if (StringUtils.isNotBlank(this.localFilePath)) {
                FileUtil.writeToFile(this.localFilePath, info);// 写入文件
            }
            System.out.println("链接提取完成！一共提取" + result.size() + "个文件！");
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
//            Document doc = Jsoup.parse(new URL(url), 30000);
            Document doc = Jsoup.parse(new URL(url).openStream(), "gb2312", url);
            if (doc != null) {
                String href = doc.select(".film_bar ul li a").get(0).attr("href");
                String videourl = "http://dcdiban.com" + href;
                Document video = Jsoup.parse(new URL(videourl), 30000);
////                        System.out.println(video.toString());
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
                            return s;
                        }
                    }
                } else {
                    System.out.println("没有获取到地址");
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
