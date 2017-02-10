package com.ai.opt.sdk.dts.vo;

/**
 * 调度器对象
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class SchedulerDef {

    /**
     * 调度器名称
     */
    private String schedulerName;

    /**
     * 归属系统
     */
    private String systemName;

    /**
     * 服务部署的ZK注册中心
     */
    private String zkAddress;

    /**
     * 任务数量
     */
    private int taskCount;

    /**
     * 显示颜色
     */
    private String color;

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
