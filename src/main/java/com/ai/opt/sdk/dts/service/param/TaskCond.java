package com.ai.opt.sdk.dts.service.param;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskCond implements Serializable {

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
	
	public TaskCond() {
		super();
	}
	
	public TaskCond(String schedulerName, String jobName, String jobGroup) {
		super();
		this.schedulerName = schedulerName;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
	}




	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
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

	

}
