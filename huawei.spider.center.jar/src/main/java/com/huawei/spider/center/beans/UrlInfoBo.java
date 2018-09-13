package com.huawei.spider.center.beans;

/**
 * 功能：爬取信息
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/13日 17:54
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class UrlInfoBo {

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 资源图片
     */
    private String img;

    /**
     * 资源描述
     */
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
