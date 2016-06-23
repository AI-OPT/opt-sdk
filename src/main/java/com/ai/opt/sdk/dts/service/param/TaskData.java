package com.ai.opt.sdk.dts.service.param;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 归属调度器名称
	 */
	private String schedulerName;

	/**
	 * 任务名称
	 */
	private String jobName;

	/**
	 * 任务分组
	 */
	private String jobGroup;

	/**
	 * 任务状态
	 */
	private String jobStatus;

	/**
	 * 调度此任务的触发器状态
	 */
	private String triggerState;

	/**
	 * 调度此任务的触发器状态
	 */
	private String triggerStateName;

	/**
	 * 显示颜色
	 */
	private String color;

	private String labelColor;

	/**
	 * 任务运行周期
	 */
	private String cronExpression;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 实现类
	 */
	private String implClassName;

	/**
	 * 环境变量
	 */
	private Map<String, Object> envVars = new HashMap<String, Object>();

	private String envVarsJSON;

	/**
	 * 上次触发执行时间
	 */
	private Date preFireTime;

	/**
	 * 下次触发执行时间
	 */
	private Date nextFireTime;

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	public Date getPreFireTime() {
		return preFireTime;
	}

	public void setPreFireTime(Date preFireTime) {
		this.preFireTime = preFireTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImplClassName() {
		return implClassName;
	}

	public void setImplClassName(String implClassName) {
		this.implClassName = implClassName;
	}

	public Map<String, Object> getEnvVars() {
		return envVars;
	}

	public void setEnvVars(Map<String, Object> envVars) {
		this.envVars = envVars;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTriggerStateName() {
		return triggerStateName;
	}

	public void setTriggerStateName(String triggerStateName) {
		this.triggerStateName = triggerStateName;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public String getEnvVarsJSON() {
		return envVarsJSON;
	}

	public void setEnvVarsJSON(String envVarsJSON) {
		this.envVarsJSON = envVarsJSON;
	}
	
	

}
