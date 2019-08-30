package com.huawei.spider.center.parsers;

import com.huawei.spider.center.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created on 2019/8/29.
 */
public class BoBoParser {

    public static String local = "/mydoc/m3u8/m3u8.txt";

    public static void main(String[] args) {
        parse();
    }

    /**
     * url解析
     *
     * @throws Exception
     */
    public static void parse() {
        try {
            System.out.println("链接开始提取...");

            String domain = "http://dazhizhaoshang.com/";
            Document doc = Jsoup.connect(domain).get();
//            System.out.println(doc);
            if (doc != null) {
                String urls = "";
                String scripts = doc.select("script").attr("src");
//                System.out.println("========= js地址: ======>>>" + scripts);
                if (scripts.indexOf("fuck/ip") != -1) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(scripts).openStream()));
                    String line, iframe = "";
                    while (StringUtils.isNotBlank(line = reader.readLine())) {
                        if (line.indexOf("frame1") != -1) {
                            iframe = line.substring(line.indexOf("http"), line.indexOf(".com") + 4);
                            if (StringUtils.isNotBlank(iframe)) {
                                iframe = iframe.replaceAll("\\\\", "");
//                                System.out.println("========= iframe: ======>>>" + iframe);
                            }
                            break;
                        }
                    }
                    if (StringUtils.isNotBlank(iframe)) {
                        Document iframeDoc = Jsoup.connect(iframe).get();
                        if (iframeDoc != null) {
                            Elements videoUrlList = iframeDoc.select(".video-col a[href]");
                            for (Element element : videoUrlList) {
                                String videodetail = element.attr("href");
                                if (StringUtils.isNotBlank(videodetail)) {
//                                    System.out.println("===== 视频详情链接地址： ====>>>" + iframe + videodetail);
                                    Document videoDoc = Jsoup.connect(iframe + videodetail).get();
                                    if (videoDoc != null) {
                                        Elements playera = videoDoc.select(".text-white");
                                        if (playera != null) {
                                            String playeraddr = iframe + playera.attr("href");
                                            if (StringUtils.isNotBlank(playeraddr)) {
//                                                System.out.println("===== 视频播放页面： ====>>>" + playeraddr);
                                                Document playerDoc = Jsoup.connect(playeraddr).get();
                                                Elements playerscripts = playerDoc.select("script");
                                                if (playerscripts != null) {
                                                    for (Element e : playerscripts) {
                                                        if (e.toString().indexOf("player_data") != -1) {
                                                            String m3u8 = e.toString();
                                                            if (StringUtils.isNotBlank(m3u8) && m3u8.indexOf("https") != -1) {
                                                                m3u8 = m3u8.substring(m3u8.indexOf("https"), m3u8.indexOf(".m3u8") + 5);
                                                                m3u8 = m3u8.replaceAll("\\\\", "");
                                                                urls += m3u8 + "\n";
                                                                System.out.println("===== 已提取视频源地址： ====>>> " + m3u8);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 写入文件
                if (StringUtils.isNotBlank(urls) && StringUtils.isNotBlank(local)) {
                    FileUtil.writeToFile(local, urls);
                }
            }
//
            System.out.println("链接提取完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
