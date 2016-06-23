package com.ai.opt.sdk.dts.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private ExecutorService executor;

    private ThreadPool(ExecutorService executor) {
        this.executor = executor;
    }

    public static ThreadPool newInstance(String poolName, int maxThreadCount) {
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreadCount);
        return new ThreadPool(executorService);
    }

    public void addThread(Runnable processThread) {
        executor.submit(processThread);
    }
}
