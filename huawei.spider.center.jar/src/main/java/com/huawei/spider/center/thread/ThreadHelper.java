package com.huawei.spider.center.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadHelper {

	public final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(10);

}
