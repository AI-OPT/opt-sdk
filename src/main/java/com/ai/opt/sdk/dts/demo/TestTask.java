package com.ai.opt.sdk.dts.demo;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.dts.base.ITask;

/**
 * 任务
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestTask implements ITask {
	private static final Logger LOG = LoggerFactory.getLogger(TestTask.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.error("开始执行任务了");

    }

}
