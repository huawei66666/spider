package com.huawei.spider.center.utils;

import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * css文件解析器
 */
public class CssParserUtil {

    final static Pattern inlineImagePattern = Pattern.compile("data\\s*:\\s*image");

    final static Pattern imageURLPattern = Pattern.compile("url(\\s*)");

    /**
     * 打印样式文件内容
     *
     * @param inStream
     * @return
     */
    public static boolean showCssText(InputStream inStream) {
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if (rules.getLength() == 0) {
                return false;
            }

            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                String cssText = rule.getCssText(); //获取样式内容
                System.out.println(cssText);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 获取css图片
     *
     * @param inStream
     * @return
     */
    public static List<String> getCssImage(InputStream inStream) {
        List<String> urlList = new ArrayList<>();
        try {
            final InputSource source = new InputSource(new InputStreamReader(inStream, "UTF-8"));
            final CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
            final CSSStyleSheet theInputSheet = parser.parseStyleSheet(source, null, null);

            CSSRuleList rules = theInputSheet.getCssRules();
            int theImageOutputCount = 0;
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                if (CSSRule.STYLE_RULE == rule.getType()) {
                    final CSSStyleDeclaration theOutputStyles = new CSSStyleDeclarationImpl();
                    final CSSStyleDeclaration theImageStyles = new CSSStyleDeclarationImpl();
                    final CSSStyleRuleImpl theCSSStyleRule = (CSSStyleRuleImpl) rule;
                    final CSSStyleDeclaration theCSSStyleDeclaration = theCSSStyleRule.getStyle();
                    for (int j = 0; j < theCSSStyleDeclaration.getLength(); j++) {
                        final String thePropertyName = theCSSStyleDeclaration.item(j);
                        final CSSValue theCSSValue = theCSSStyleDeclaration.getPropertyCSSValue(thePropertyName);
                        final String thePriority = theCSSStyleDeclaration.getPropertyPriority(thePropertyName);
                        if (("background-image".equals(thePropertyName)) || ("background".equals(thePropertyName)) && (null != theCSSValue)) {
                            if (theCSSValue.toString().startsWith("url(") && !theCSSValue.toString().startsWith("url(data:image")) {
//                                System.out.println(theCSSValue.toString());
                                String url = theCSSValue.toString().substring(theCSSValue.toString().indexOf("(") + 1, theCSSValue.toString().indexOf(")"));
//                                System.out.println(url);
                                if (!urlList.contains(url)) {// 去重复
                                    urlList.add(url);
                                }
                            }
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return urlList;
    }
}
