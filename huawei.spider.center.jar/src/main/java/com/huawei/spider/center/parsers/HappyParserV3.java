package com.huawei.spider.center.parsers;

import com.huawei.spider.center.beans.UrlInfoBo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提取猜你喜欢链接地址
 */
public class HappyParserV3 {

    public String domain = "http://dcdiban.com";// 域名
    public Map<String, String> urlPool = new HashMap<>();// url池

    public HappyParserV3() {
    }

    public HappyParserV3(String domain) {
        this.domain = domain;
    }

    /**
     * url递归解析，喜欢该电影的人还喜欢
     *
     * @param startUrl 起始地址，默认为域名
     * @param level    递归层数，默认1层，即是提取该域名下的"最新电影"
     * @return
     * @throws Exception
     */
    public List<UrlInfoBo> parseFavorite(String startUrl, Integer level) throws Exception {
        try {
            // 参数处理
            startUrl = StringUtils.isBlank(startUrl) ? domain : startUrl;
            level = level == null ? 1 : level;

            System.out.println("链接开始提取...");
            List<UrlInfoBo> result = new ArrayList<>();
            Document doc = Jsoup.parse(new URL(startUrl).openStream(), "gb2312", startUrl);
            recrution(doc, level);// 进入递归
            System.out.println("链接提取完成！一共提取" + this.urlPool.size() + "个文件！");

            if (this.urlPool.size() > 0) {
                for (Map.Entry<String, String> entry : this.urlPool.entrySet()) {
                    UrlInfoBo bo = new UrlInfoBo();
                    bo.setName(entry.getKey());
                    bo.setUrl(entry.getValue());
                    result.add(bo);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 递归
     *
     * @param doc
     * @param level 递归层数
     * @throws Exception
     */
    public void recrution(Document doc, Integer level) throws Exception {
        if (doc != null && level > 0) {
            level--;// 层数减一
            Elements elements = doc.select(".movie_list ul li a:has(img)");
            for (Element a : elements) {
                String href = a.attr("href");// 集数地址，一般为第一集
                if (StringUtils.isNoneBlank(href)) {
                    Document episodeDoc = Jsoup.parse(new URL(domain + href).openStream(), "gb2312", domain + href);
                    if (episodeDoc != null) {
                        String episodeHref = episodeDoc.select(".film_bar ul li a").get(0).attr("href");
                        String videourl = domain + episodeHref;
                        Document videoDoc = Jsoup.parse(new URL(videourl), 30000);// video播放页面
                        if (videoDoc != null) {
                            Elements scripts = videoDoc.select("script[src^='/playdata']");
                            String js = domain + scripts.attr("src");
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
                            System.out.println("原始链接：" + s);

                            getUrls(s);// 提取链接
                            recrution(videoDoc, level);// 递归
                        } else {
                            System.out.println("没有获取到地址");
                        }
                    }
                }
            }
        }
    }

    /**
     * java代码调用js变量提取链接，只提取mp4
     *
     * @param js js原始链接
     * @throws Exception
     */
    public void getUrls(String js) throws Exception {
        if (StringUtils.isNoneBlank(js)) {

            // 原始链接处理
            if (js.indexOf("VideoListJson") == -1) {
                return;
            }
            if (js.indexOf("urlinfo") != -1) {
                js = js.substring(0, js.indexOf("urlinfo") - 1);
            }

            // 创建js引擎
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine se = sem.getEngineByName("js");
            try {
                se.eval(js);
                Object json = se.getContext().getAttribute("VideoListJson");// 获取变量值
                if (json instanceof Map) {
                    Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) json;
                    if (map != null && map.size() > 0) {
                        for (Map<String, Object> map1 : map.values()) {
                            String separator = "";
                            for (Object o : map1.values()) {
                                if (o instanceof String) {
                                    separator = (String) o;
                                }
                                if (separator.toLowerCase().indexOf("ckplayer") != -1 && o instanceof Map) {
                                    Map<String, String> map2 = (Map<String, String>) o;
                                    for (String s : map2.values()) {
                                        if (StringUtils.isNoneBlank(s) && (s.indexOf("http") != -1 || s.indexOf("https") != -1) && s.indexOf(".mp4") != -1) {
                                            if (s.indexOf("$ckplayer") != -1) {
                                                if (s.indexOf("https:") != -1) {
                                                    s = s.substring(s.indexOf("https"), s.indexOf("$ckplayer"));
                                                }
                                                if (s.indexOf("http:") != -1) {
                                                    s = s.substring(s.indexOf("http"), s.indexOf("$ckplayer"));
                                                }
                                            } else {
                                                s = s.replace("$", "");
                                                if (s.indexOf("https:") != -1) {
                                                    s = s.substring(s.indexOf("https"));
                                                }
                                                if (s.indexOf("http:") != -1) {
                                                    s = s.substring(s.indexOf("http"));
                                                }
                                            }
                                            if ((s.startsWith("http://") || s.startsWith("https://")) && s.endsWith(".mp4")) {
                                                writeToUrlPool(s);
                                                System.out.println("已提取链接：" + s);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入地址池，去重
     *
     * @throws Exception
     */
    public void writeToUrlPool(String url) throws Exception {
        if (StringUtils.isNoneBlank(url)) {
            String name = url.substring(url.lastIndexOf("/") + 1);
            if (this.urlPool.size() > 0) {
                if (StringUtils.isNoneBlank(name)) {
                    String exist = this.urlPool.get(name);
                    if (StringUtils.isBlank(exist)) {
                        this.urlPool.put(name, url);
                    }
                }
            } else {
                this.urlPool.put(name, url);
            }
        }
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
