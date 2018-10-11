package com.huawei.spider.center.parsers;

import com.huawei.spider.center.downloader.http.HttpDownloader;
import com.huawei.spider.center.utils.CssParserUtil;
import com.huawei.spider.center.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 网页爬虫解析器
 */
public class HtmlParser {

    private static final Logger logger = LoggerFactory.getLogger(HtmlParser.class);

    private static String localPath = "/media/wei/d/spider/lamadaogou";

    public static void main(String[] args) {
        parseHtml();
    }

    private static void parseHtml() {
        try {
            String domain = "http://www.lamadaogou.com";// 电影港
            Document doc = Jsoup.connect(domain).get();
            if (doc != null) {
//                System.out.println(doc.toString());
//
//                // html文件
//                FileUtil.writeToFile(localPath + "/index.html", doc.toString());
//
//                // css文件
                getCssFile(doc);

                // script文件
//                getJsFile(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取image
     *
     * @param doc
     * @throws Exception
     */
    private static void getImages(Document doc) throws Exception {
        if (doc == null) {
            System.out.println("image文件 doc为空");
            return;
        }

        // TODO
    }

    /**
     * 获取css文件
     *
     * @param doc
     * @throws Exception
     */
    private static void getCssFile(Document doc) throws Exception {
        if (doc == null) {
            System.out.println("css文件 doc为空");
            return;
        }
        Elements links = doc.select("link");
        if (links != null) {
            for (Element link : links) {
                String linkHref = link.attr("href");
                System.out.println(linkHref);

                // 截取名称，写入文件
                /*String cssName;
                if (linkHref.indexOf(".css") != -1) {
                    cssName = linkHref.substring(linkHref.lastIndexOf("/") + 1, linkHref.lastIndexOf(".css") + 4);
                } else {
                    cssName = linkHref + ".css";
                }
                System.out.println("正在提取：" + linkHref);
                HttpDownloader httpDownloader = new HttpDownloader();
                httpDownloader.download(linkHref, cssName, localPath + "/css/", 30000);*/

                // 看是否有引用图片，若有，进行提取
                URL u = new URL(linkHref);// 构造URL
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();// 打开连接
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(30000);
                connection.connect();
                if (connection.getResponseCode() == 200) {// 200:请求资源成功
                    CssParserUtil.showCssText(connection.getInputStream());
                }

            }
        }
    }

    /**
     * 获取js文件
     *
     * @param doc
     * @throws Exception
     */
    private static void getJsFile(Document doc) throws Exception {
        if (doc == null) {
            System.out.println("js文件 doc为空");
            return;
        }
        Elements jslinks = doc.select("script[src]");
        if (jslinks != null) {
            for (Element link : jslinks) {
                String src = link.attr("src");
                System.out.println("正在提取：" + src);
                if (StringUtils.isNoneBlank(src)) {
                    String filename = "";
                    if (src.indexOf(".js") != -1) {
                        filename = src.substring(src.lastIndexOf("/") + 1, src.lastIndexOf(".js") + 3);
                    } else {
                        filename = src + ".js";
                    }
                    HttpDownloader httpDownloader = new HttpDownloader();
                    httpDownloader.download(src, filename, localPath + "/js/", 30000);
                }
            }
        }
    }

}
