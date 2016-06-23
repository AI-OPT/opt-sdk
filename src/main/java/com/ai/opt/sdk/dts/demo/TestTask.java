package com.ai.opt.sdk.dts.demo;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import com.ai.opt.sdk.dts.base.ITask;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestTask implements ITask {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("开始执行任务了");

    }

}
