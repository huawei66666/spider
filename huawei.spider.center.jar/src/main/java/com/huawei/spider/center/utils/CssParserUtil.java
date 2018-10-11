package com.huawei.spider.center.utils;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * css文件解析器
 */
public class CssParserUtil {

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

            /*for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                String cssText = rule.getCssText(); //获取样式内容
                if (cssText.indexOf(".png") != -1 || cssText.indexOf(".jpg") != -1 || cssText.indexOf(".gif") != -1) {
                    //获取样式名称
                    String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                    System.out.println(selectorText_);
                }
            }*/

            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取选择器名称
//                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();

                //获取样式内容
                String cssText = rule.getCssText();
                if (cssText.indexOf(".png") != -1 || cssText.indexOf(".jpg") != -1 || cssText.indexOf(".gif") != -1) {
                    CSSStyleDeclaration ss = ((CSSStyleRule) rule).getStyle();

                    CSSValue value = ss.getPropertyCSSValue("background");
                    if(value != null) {
                        System.out.println(value.getCssText());

//                    String propertyValue = ss.getPropertyValue("background");
//                    System.out.println(propertyValue);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
