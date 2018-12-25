package com.huawei.spider.center.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by huawei on 2018/9/8.
 */
public class CallJsTest {

    /**
     * java引擎调用js
     *
     * @throws Exception
     */
    @Test
    public void testCallJs() throws Exception {
        String js = "function test(o) {\n" +
                "    if (o == 1) {\n" +
                "        return 1;\n" +
                "    } else if (o == 2) {\n" +
                "        return 2;\n" +
                "    } else {\n" +
                "        return 0;\n" +
                "    }\n" +
                "}";

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//        engine.eval(new StringReader(js));

        //如果js存在文件里，举例
        Resource aesJs = new ClassPathResource("test.js");
        engine.eval(new FileReader(aesJs.getFile()));

        Invocable invocable = (Invocable) engine;
        try {
            int result = (int) invocable.invokeFunction("test", 1);
            System.out.println(result);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2() throws Exception {
//        String js = "var VideoListJson=1, test=2;";
//        String js = "var VideoListJson=[['CKplayer',['\\u7B2C1\\u96C6$https://dns.63mimi.com/20180111/1/1/xml/91_16d132d9a2bb41029b93e91a670a570d.mp4$ckplayer']]],urlinfo='http://'+document.domain+'/vidol/index801.html?801-<from>-<pos>';";
//        String js = "var urlinfo='http://'+document.domain+'/vidol/index801.html?801-<from>-<pos>';";
        String js = "var VideoListJson=[['$ckplayer',['\\u7B2C1\\u96C6$https://201712mp4.89soso.com/20180315/22/1/xml/91_41849eecc44440a69b47f7ac739b8443.mp4']],['$CKplayer',['\\u7B2C1\\u96C6$https://201712mp4.89soso.com/20180331/21/1/xml/91_cc2c4f9179e64b75887e6eb146725663.mp4']],['ckplayer',['\\u7B2C1\\u96C6$https://201712mp4.89soso.com/20180315/22/1/xml/91_41849eecc44440a69b47f7ac739b8443.mp4$']]]";

        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("js");
        try {
            se.eval(js);
            Object json = se.getContext().getAttribute("VideoListJson");// 获取变量值
            System.out.println(json);
            if (json instanceof Map) {
                Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) json;
                if (map != null && map.size() > 0) {
                    for (Map<String, Object> map1 : map.values()) {
//                        for(Object o : map1.values()) {
//                            if(o instanceof Map) {
//                                Map<String, String> map2 = (Map<String, String>) o;
//                                for(String s : map2.values()) {
//                                    System.out.println(s);
//                                }
//                            }
//                        }

                        System.out.println(map1.get("0"));
                        System.out.println(map1.get("1"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws Exception {
        String s = "var VideoListJson=[['$ckplayer',['\\u7B2C1\\u96C6$https://201712mp4.89soso.com/20180315/22/1/xml/91_41849eecc44440a69b47f7ac739b8443.mp4']],['$CKplayer',['\\u7B2C1\\u96C6$https://201712mp4.89soso.com/20180331/21/1/xml/91_cc2c4f9179e64b75887e6eb146725663.mp4']],['ckplayer',['\\u7B2C1\\u96C6$https://201712mp4.89soso.com/20180315/22/1/xml/91_41849eecc44440a69b47f7ac739b8443.mp4$']]],urlinfo='http://'+document.domain+'/vidol/index1524.html?1524-<from>-<pos>';";
        if (StringUtils.isNoneBlank(s)) {
            if (s.indexOf("urlinfo") != -1) {
                s = s.substring(0, s.indexOf("urlinfo") - 1);
                System.out.println(s);
            }
        }
    }

}
