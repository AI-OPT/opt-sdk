package com.ai.opt.sdk.test.paas.dts;

import com.ai.opt.sdk.dts.demo.TestTask;
import com.ai.opt.sdk.dts.factory.DTSSchedulerFactory;
import com.ai.opt.sdk.dts.service.param.TaskData;

public class DTSAdd {

    public static void main(String[] args) throws Exception {
        TaskData job = new TaskData();
        job.setSchedulerName("opt-dts-test");
        job.setJobStatus("1");
        job.setImplClassName(TestTask.class.getName());
        job.setDesc("ָ测试任务");
        job.setCronExpression("0/5 * * * * ?");
        job.setJobGroup("testGrp1");
        job.setJobName("测试1");
        job.getEnvVars().put("var1", "1");
         DTSSchedulerFactory.addOrUpdateTask(job);
        // DTSSchedulerFactory.deleteTask(job);
        // DTSSchedulerFactory.pauseTask(job);
        //DTSSchedulerFactory.getAllTasks("runner-test");
         DTSSchedulerFactory.resumeJob(job);
        System.exit(-1);
        System.out.println("OK");
    }

}
