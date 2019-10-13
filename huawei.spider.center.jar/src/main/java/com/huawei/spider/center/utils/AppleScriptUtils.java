package com.huawei.spider.center.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created by huawei on 2019/10/13.
 */
public class AppleScriptUtils {

    public static void main(String[] args) {
        testAppleScript();
    }

    public static void testAppleScript() {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("AppleScriptEngine");
            if (engine != null) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("tell application \"Terminal\" ");
                buffer.append("do script echo 'hello' ");
                buffer.append("end tell");
                System.out.println(buffer.toString());
//                engine.eval(buffer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
