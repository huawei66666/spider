package com.huawei.spider.center.test;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;

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

}
