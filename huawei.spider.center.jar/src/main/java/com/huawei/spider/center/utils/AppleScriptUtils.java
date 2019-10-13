package com.huawei.spider.center.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by huawei on 2019/10/13.
 */
public class AppleScriptUtils {

    public static void main(String[] args) {
        testAppleScriptFromFile();
    }

    public static void testAppleScript() {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("AppleScriptEngine");
            if (engine != null) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("tell application \"Terminal\"\n");
                buffer.append("do script \"echo 'hello'\"\n");
                buffer.append("end tell\n ");
                System.out.println(buffer.toString());
                engine.eval(buffer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testAppleScriptFromFile() {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("AppleScriptEngine");
            if (engine != null) {
                String filepath = "/mydoc/workspace/applescript/test.scpt";
                File script = new File(filepath);
                if(script.exists()) {
                    Reader reader = new FileReader(script);
                    engine.eval(reader);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
