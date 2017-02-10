package com.ai.opt.sdk.dts.thread;


import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务线程
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public abstract class AbstractTaskThread<T> implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractTaskThread.class);

    private T bo;

    private CountDownLatch lanch;

    public AbstractTaskThread(T bo) {
        this.bo = bo;
    }

    public AbstractTaskThread(T bo, CountDownLatch lanch) {
        this.bo = bo;
        this.lanch = lanch;
    }

    @Override
    public void run() {
        try {
            this.execute(this.bo);
        } catch (Exception e) {
            LOG.error("执行失败", e);
        } finally {
            if (this.lanch != null) {
                this.lanch.countDown();
            }
        }
    }

    public abstract void execute(T bo) throws Exception;

}
