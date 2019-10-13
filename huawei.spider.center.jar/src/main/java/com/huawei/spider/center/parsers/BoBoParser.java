package com.huawei.spider.center.parsers;

import com.huawei.spider.center.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 2019/8/29.
 */
public class BoBoParser {

    public static String local = "/mydoc/m3u8/m3u8.txt";

    public static void main(String[] args) {
//        parse2();
        getFileCommand();
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    local = "/mydoc/m3u8/m3u8-" + sdf.format(new Date()) + ".txt";
                    FileUtil.writeToFile(local, urls);
                }
            }
//
            System.out.println("链接提取完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * url解析
     *
     * @throws Exception
     */
    public static void parse2() {
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
                            Elements videoUrlList = iframeDoc.select(".mm-content .row-item");
                            for (Element element : videoUrlList) {
//                                System.out.println(element);
//                                String title = element.select(".c_white").text();
//                                System.out.println(title);
                                String aname = element.select(".item a").text();
//                                if(aname.indexOf("学生专区") != -1) {
//                                System.out.println(aname);
                                Elements links = element.select(".item a");
                                for (Element link : links) {
                                    String area = link.text();
                                    System.out.println(area);
                                    urls += area + "\n";
                                    String lk = link.attr("href");
                                    if (StringUtils.isNoneBlank(lk)) {
                                        Document videoDoc = Jsoup.connect(iframe + lk).get();
//                                    System.out.println(videoDoc);
                                        if (videoDoc != null) {
                                            Elements aa = videoDoc.select(".video-content .row").select("a[name='videoa']");
//                                        System.out.println(playera);
                                            for (Element a : aa) {
                                                String alink = a.attr("href");
                                                Document video = Jsoup.connect(iframe + alink).get();
//                                            System.out.println(video);
                                                if (video != null) {
                                                    Elements playera = video.select(".text-white");
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
//                                }

                            }
                        }
                    }
                }

                // 写入文件
                if (StringUtils.isNotBlank(urls) && StringUtils.isNotBlank(local)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    local = "/mydoc/m3u8/m3u8-" + sdf.format(new Date()) + ".txt";
                    FileUtil.writeToFile(local, urls);
                }
            }
//
            System.out.println("链接提取完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件命令
     */
    public static void getFileCommand() {
        String filepath = "/mydoc/m3u8";
        String folder = "/mydoc/videos/a/m3u8";
        List<String> names = new ArrayList<>();
        try {
            File f = new File(folder);
            if (f.exists() && f.isDirectory()) {
                File[] files = f.listFiles();
                for (File fi : files) {
                    if (!fi.isHidden()) {
                        String filename = fi.getName();
                        if(filename.indexOf(".") != -1) {
                            filename = filename.substring(0, filename.lastIndexOf("."));
//                        System.out.println(filename);
                            names.add(filename);
                        }
                    }
                }
            }

            String commands = "";
            int count = 0;
            File pathFile = new File(filepath);
            if (pathFile.exists() && pathFile.isDirectory()) {
                File[] files = pathFile.listFiles();
                for (File file : files) {
                    if (file.exists() && file.isFile()) {
                        if(file.getName().startsWith("m3u8") && file.getName().endsWith(".txt")) {
//                            System.out.println(file.getName());
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line;
                            while (StringUtils.isNoneBlank(line = reader.readLine())) {
                                if (line.indexOf("https") != -1) {
                                    String filename = line.substring(line.lastIndexOf("/") + 1, line.lastIndexOf("."));
                                    if (!names.contains(filename)) {
                                        // ffmpeg -i https://m3u8.cdnpan.com/lr8STPI1.m3u8 -vcodec copy -acodec copy -absf aac_adtstoasc -bufsize 20000k /mydoc/videos/a/m3u8/lr8STPI1.mp4
                                        String command = "ffmpeg -i https://m3u8.cdnpan.com/" + filename + ".m3u8 -vcodec copy -acodec copy -absf aac_adtstoasc -bufsize 20000k /mydoc/videos/a/m3u8/" + filename + ".mp4";
                                        commands += command + "\n";
                                        count++;
                                        System.out.println(command);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 写入文件
            System.out.println("命令总数量为：" + count);
            if (StringUtils.isNotBlank(commands)) {
                String localpath = "/mydoc/m3u8/command.txt";
                FileUtil.writeToFile(localpath, commands);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
