package com.huawei.spider.center.utils;

public class ThreadPrint26 implements Runnable {

    TwoThreadPrintAZ26 twoThreadPrintAZ26 = null;

    public ThreadPrint26(TwoThreadPrintAZ26 twoThreadPrintAZ26) {
        this.twoThreadPrintAZ26 = twoThreadPrintAZ26;
    }

    @Override
    public void run() {
        synchronized (this.twoThreadPrintAZ26) {
            for (int i = 0; i < 26; i++) {
                System.out.println(i + 1);
                twoThreadPrintAZ26.notify();
                try {
                    if (i == 25) break;
                    twoThreadPrintAZ26.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
