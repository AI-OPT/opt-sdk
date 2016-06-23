package com.ai.opt.sdk.dts.factory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.dts.base.ITask;
import com.ai.opt.sdk.dts.service.param.TaskData;
import com.ai.paas.ipaas.util.StringUtil;

public final class DTSSchedulerFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DTSSchedulerFactory.class);

    private static final String DTS_QUARTZ_CONF_FILE = "/dts/dts-config.properties";

    private DTSSchedulerFactory() {

    }

    /**
     * 启动调度器
     * 
     * @param schedulerName
     * @param instanceId
     * @author zhangchao
     * @throws Exception
     */
    public static void start(String schedulerName) throws Exception {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("启动任务失败，DTS调度器称为空");
        }
        schedulerName = schedulerName.trim();
        Scheduler scheduler = getScheduler(schedulerName);
        scheduler.start();
    }

    /**
     * 创建调度器
     * 
     * @param schedulerName
     * @author zhangchao
     */
    private static Scheduler getScheduler(String schedulerName) {
        // 从配置中心读取DTS QUARTZ的配置信息
        Properties p = ConfigTool.getDTSQuartzProperties();
        if (p == null) {
            // 如果没有的在配置中心配置，则读取文件的
            try {
                p = new Properties();
                p.load(DTSSchedulerFactory.class.getResourceAsStream(DTS_QUARTZ_CONF_FILE));
            } catch (IOException e) {
                throw new SystemException("读取DTS配置文件[" + DTS_QUARTZ_CONF_FILE + "]失败", e);
            }
        }
        p.setProperty("org.quartz.scheduler.instanceName", schedulerName);
        p.setProperty("org.quartz.scheduler.instanceId", "AUTO");

        try {
            SchedulerFactory schedFact = new StdSchedulerFactory(p);
            Scheduler scheduler = schedFact.getScheduler();
            return scheduler;
        } catch (SchedulerException e) {
            throw new SystemException("获取调度器失败", e);
        }

    }

    /**
     * 获取某个调度器所有的任务信息
     * 
     * @param schedulerName
     * @return
     * @throws Exception
     * @author zhangchao
     */
    public static List<TaskData> getAllTasks(String schedulerName) {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("没有指定调度器名称");
        }
        LOG.debug("开始获取调度器[" + schedulerName + "]的所有任务列表");
        Scheduler scheduler = getScheduler(schedulerName);
        if (scheduler == null) {
            return null;
        }
        List<TaskData> list = new ArrayList<TaskData>();

        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                TaskData task = new TaskData();
                task.setSchedulerName(scheduler.getSchedulerName());
                task.setCronExpression(trigger.getCronExpression());
                task.setDesc(jobDetail.getDescription());
                JobDataMap jdMap = jobDetail.getJobDataMap();
                task.getEnvVars().clear();
                for (String key : jdMap.keySet()) {
                    Object v = jdMap.get(key);
                    task.getEnvVars().put(key, v);
                }
                Trigger.TriggerState state =scheduler.getTriggerState(triggerKey);
                task.setTriggerState(state.name());
                if(state==Trigger.TriggerState.NORMAL){
                    task.setColor("bg-blue");
                    task.setLabelColor("label-primary");
                    task.setTriggerStateName("调度正常");
                }else if(state==Trigger.TriggerState.PAUSED){
                    task.setColor("bg-red");
                    task.setLabelColor("label-danger");
                    task.setTriggerStateName("调度暂停");
                }else if(state==Trigger.TriggerState.BLOCKED){
                    task.setColor("bg-red");
                    task.setLabelColor("label-danger");
                    task.setTriggerStateName("调度阻塞");
                }else if(state==Trigger.TriggerState.COMPLETE){
                    task.setColor("bg-olive");
                    task.setLabelColor("label-success");
                    task.setTriggerStateName("调度完成");
                }else if(state==Trigger.TriggerState.ERROR){
                    task.setColor("bg-red");
                    task.setLabelColor("label-danger");
                    task.setTriggerStateName("调度错误");
                }else if(state==Trigger.TriggerState.NONE){
                    task.setColor("bg-red");
                    task.setLabelColor("label-danger");
                    task.setTriggerStateName("没有调度");
                }
                task.setImplClassName(jobDetail.getJobClass().getName());
                task.setJobName(jobKey.getName());
                task.setNextFireTime(trigger.getNextFireTime());
                task.setJobGroup(jobKey.getGroup());
                list.add(task);
            }
        } catch (SchedulerException e) {
            LOG.error("获取任务列表失败", e);
            throw new SystemException(e);
        }
        return list;
    }

    public static TaskData getTaskData(String schedulerName, String jobName, String jobGroup) {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("没有指定调度器名称");
        }
        if (StringUtil.isBlank(jobName)) {
            throw new SystemException("没有指定任务名称");
        }
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("没有指定任务分组");
        }
        Scheduler scheduler = getScheduler(schedulerName);
        if (scheduler == null) {
            return null;
        }
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            TaskData task = new TaskData();
            task.setSchedulerName(scheduler.getSchedulerName());
            task.setCronExpression(trigger.getCronExpression());
            task.setDesc(jobDetail.getDescription());
            JobDataMap jdMap = jobDetail.getJobDataMap();
            task.getEnvVars().clear();
            for (String key : jdMap.keySet()) {
                Object v = jdMap.get(key);
                task.getEnvVars().put(key, v);
            }
            task.setImplClassName(jobDetail.getJobClass().getName());
            task.setJobName(jobKey.getName());
            task.setNextFireTime(trigger.getNextFireTime());
            task.setJobGroup(jobKey.getGroup());
            return task;
        } catch (SchedulerException e) {
            LOG.error("获取任务列表失败", e);
            throw new SystemException(e);
        }
    }

    public static int getTaskCount(String schedulerName) {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("没有指定调度器名称");
        }
        LOG.debug("开始获取调度器[" + schedulerName + "]的任务数量");
        Scheduler scheduler = getScheduler(schedulerName);
        if (scheduler == null) {
            return 0;
        }
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            return jobKeys == null ? 0 : jobKeys.size();
        } catch (SchedulerException e) {
            LOG.error("获取任务列表失败", e);
            throw new SystemException(e);
        }
    }

    /**
     * 新增或者维护JOB任务
     * 
     * @param task
     * @throws Exception
     * @author zhangchao
     */
    public static void addOrUpdateTask(TaskData task) {
        if (task == null) {
            throw new SystemException("没有任务信息");
        }
        if (StringUtil.isBlank(task.getSchedulerName())) {
            throw new SystemException("没有指定JOB任务对应的调度器名称");
        }
        if (StringUtil.isBlank(task.getJobName())) {
            throw new SystemException("没有指定JOB任务名称");
        }
        if (StringUtil.isBlank(task.getJobGroup())) {
            throw new SystemException("没有指定JOB任务分组");
        }
        if (StringUtil.isBlank(task.getImplClassName())) {
            throw new SystemException("没有指定JOB任务业务实现类");
        }
        if (StringUtil.isBlank(task.getCronExpression())) {
            throw new SystemException("没有指定JOB任务调度周期表达式");
        }
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        Scheduler scheduler = getScheduler(task.getSchedulerName());
        try {
            boolean exists = scheduler.checkExists(jobKey);
            if (exists) {
                scheduler.deleteJob(jobKey);
            }
            JobDetail jobDetail = JobBuilder
                    .newJob(getTaskInstance(task.getImplClassName()).getClass())
                    .withDescription(task.getDesc())
                    .withIdentity(task.getJobName(), task.getJobGroup()).build();
            jobDetail.getJobDataMap().putAll(task.getEnvVars());
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task
                    .getCronExpression());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(task.getJobName(), task.getJobGroup())
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error("任务添加或修改失败", e);
            throw new SystemException(e);
        }

    }

    /**
     * 删除指定任务
     * 
     * @param task
     * @throws Exception
     * @author zhangchao
     * @ApiDocMethod
     */
    public static void deleteTask(TaskData task) {
        if (task == null) {
            throw new SystemException("没有任务信息");
        }
        if (StringUtil.isBlank(task.getSchedulerName())) {
            throw new SystemException("没有指定系统名称");
        }
        if (StringUtil.isBlank(task.getJobName())) {
            throw new SystemException("没有指定JOB任务名称");
        }
        if (StringUtil.isBlank(task.getJobGroup())) {
            throw new SystemException("没有指定JOB任务分组");
        }
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        Scheduler scheduler = getScheduler(task.getSchedulerName());
        try {
            boolean exists = scheduler.checkExists(jobKey);
            if (exists) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            LOG.error("任务删除失败", e);
            throw new SystemException(e);
        }

    }

    /**
     * 暂停任务
     * 
     * @param task
     * @throws Exception
     * @author zhangchao
     */
    public static void pauseTask(TaskData task) {
        if (task == null) {
            throw new SystemException("没有任务信息");
        }
        if (StringUtil.isBlank(task.getSchedulerName())) {
            throw new SystemException("没有指定系统名称");
        }
        if (StringUtil.isBlank(task.getJobName())) {
            throw new SystemException("没有指定JOB任务名称");
        }
        if (StringUtil.isBlank(task.getJobGroup())) {
            throw new SystemException("没有指定JOB任务分组");
        }
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        Scheduler scheduler = getScheduler(task.getSchedulerName());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("任务暂停失败", e);
            throw new SystemException(e);
        }
    }

    /**
     * 暂停所有任务
     * 
     * @param schedulerName
     * @throws Exception
     * @author zhangchao
     */
    public static void pauseAll(String schedulerName) {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("没有指定调度器名称");
        }
        Scheduler scheduler = getScheduler(schedulerName);
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            LOG.error("任务暂停失败", e);
            throw new SystemException(e);
        }
    }

    /**
     * 暂停任务
     * 
     * @param task
     * @throws Exception
     * @author zhangchao
     */
    public static void resumeJob(TaskData task) {
        if (task == null) {
            throw new SystemException("没有任务信息");
        }
        if (StringUtil.isBlank(task.getSchedulerName())) {
            throw new SystemException("没有指定调度器名称");
        }
        if (StringUtil.isBlank(task.getJobName())) {
            throw new SystemException("没有指定JOB任务名称");
        }
        if (StringUtil.isBlank(task.getJobGroup())) {
            throw new SystemException("没有指定JOB任务分组");
        }
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        Scheduler scheduler = getScheduler(task.getSchedulerName());
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("任务恢复失败", e);
            throw new SystemException(e);
        }
    }

    /**
     * 恢复所有任务
     * 
     * @param schedulerName
     * @throws Exception
     * @author zhangchao
     */
    public static void resumeAll(String schedulerName) {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("没有指定调度器名称");
        }
        Scheduler scheduler = getScheduler(schedulerName);
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            LOG.error("任务恢复失败", e);
            throw new SystemException(e);
        }
    }

    private static ITask getTaskInstance(String className) {
        ITask task;
        try {
            task = (ITask) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            throw new SystemException("获取任务类[" + className + "]实例出错:" + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new SystemException("获取任务类[" + className + "]实例出错:" + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new SystemException("获取任务类[" + className + "]实例出错:" + e.getMessage(), e);
        }
        return task;
    }

    public static Properties getDtsConf() throws Exception {
        Properties p = new Properties();
        p.load(DTSSchedulerFactory.class.getResourceAsStream(DTS_QUARTZ_CONF_FILE));
        return p;
    }

}
