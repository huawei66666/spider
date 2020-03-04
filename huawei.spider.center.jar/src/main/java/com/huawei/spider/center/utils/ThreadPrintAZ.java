package com.huawei.spider.center.utils;

public class ThreadPrintAZ implements Runnable {

    TwoThreadPrintAZ26 twoThreadPrintAZ26 = null;

    public ThreadPrintAZ(TwoThreadPrintAZ26 twoThreadPrintAZ26) {
        this.twoThreadPrintAZ26 = twoThreadPrintAZ26;
    }

    @Override
    public void run() {
        synchronized (this.twoThreadPrintAZ26) {
            for (int i = 0, temp = 65; i < 26; i++) {
                System.out.println((char) (temp + i));
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
