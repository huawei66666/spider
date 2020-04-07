package com.huawei.spider.center.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogIntercepter implements InvocationHandler {

    private Object target;

    private void beforeMethod(Method m) {
        System.out.println(m.getName() + " start...");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeMethod(method);
        method.invoke(target, args);
        return null;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
