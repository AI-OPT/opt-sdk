package com.ai.opt.sdk.dts.util;

import java.util.List;

import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.dts.constants.DTSConstants;
import com.ai.opt.sdk.dts.factory.DTSSchedulerFactory;
import com.ai.opt.sdk.dts.service.interfaces.IDTSManagerSV;
import com.ai.opt.sdk.dts.service.param.TaskData;
import com.ai.opt.sdk.dts.vo.SchedulerDef;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DTSUtil {

    private DTSUtil() {

    }

    public static void addOrUpdateTask(TaskData task) {
        if (task == null) {
            throw new SystemException("任务信息不能为空");
        }
        if (StringUtil.isBlank(task.getSchedulerName())) {
            throw new SystemException("调度器名称不能为空");
        }
        String group = getIDTSManagerGroup(task.getSchedulerName());
        String registryURL = getZKAddress(task.getSchedulerName());
        DTSUtil.getIDTSManagerSV(registryURL, group).addOrUpdateTask(task);
    }

    public static void deleteTask(TaskData task) {
        if (task == null) {
            throw new SystemException("任务信息不能为空");
        }
        if (StringUtil.isBlank(task.getSchedulerName())) {
            throw new SystemException("调度器名称不能为空");
        }
        String group = getIDTSManagerGroup(task.getSchedulerName());
        String registryURL = getZKAddress(task.getSchedulerName());
        DTSUtil.getIDTSManagerSV(registryURL, group).deleteTask(task);
    }

    public static List<TaskData> getAllTasks(String schedulerName) {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("调度器名称不能为空");
        }
        String group = getIDTSManagerGroup(schedulerName);
        String registryURL = getZKAddress(schedulerName);
        List<TaskData> list = DTSUtil.getIDTSManagerSV(registryURL, group).getAllTasks(
                schedulerName);
        return list;
    }

    public static TaskData getTaskData(String schedulerName, String jobName, String jobGroup) {
        if (StringUtil.isBlank(schedulerName)) {
            throw new SystemException("调度器名称不能为空");
        }
        String group = getIDTSManagerGroup(schedulerName);
        String registryURL = getZKAddress(schedulerName);
        TaskData data = DTSUtil.getIDTSManagerSV(registryURL, group).getTaskData(schedulerName,
                jobName, jobGroup);
        if (data != null) {
            data.setEnvVarsJSON(JSON.toJSONString(data.getEnvVars()));
        }
        return data;
    }

    public static List<SchedulerDef> getDTSSchedulerDefs() {
        String conf="";
		try {
			conf = CCSClientFactory.getDefaultConfigClient().get(
			        DTSConstants.OP_DTS_SCHEDULER_DEF_PATH);
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        String[] colors = new String[] { "bg-aqua", "bg-green", "bg-yellow", "bg-red", "bg-purple",
                "bg-blue", "bg-light-blue", "bg-navy", "bg-teal", "bg-olive", "bg-lime",
                "bg-orange", "bg-maroon" };
        List<SchedulerDef> list = JSON.parseArray(conf, SchedulerDef.class);
        int i = 0;
        if (!CollectionUtil.isEmpty(list)) {
            for (SchedulerDef d : list) {
                int taskCount = DTSSchedulerFactory.getTaskCount(d.getSchedulerName());
                d.setTaskCount(taskCount);
                if (i < 13) {
                    d.setColor(colors[i]);
                    i++;
                } else {
                    d.setColor(colors[3]);
                    i = 0;
                }
            }
        }
        return list;
    }

    public static void pauseTask(TaskData task) {
        DTSSchedulerFactory.pauseTask(task);
    }

    public static void pauseAll(String schedulerName) {
        DTSSchedulerFactory.pauseAll(schedulerName);
    }

    public static void resumeJob(TaskData task) {
        DTSSchedulerFactory.resumeJob(task);
    }

    public static void resumeAll(String schedulerName) {
        DTSSchedulerFactory.resumeAll(schedulerName);
    }

    private static String getZKAddress(String schedulerName) {
        List<SchedulerDef> list = getDTSSchedulerDefs();
        String zkAddress = null;
        if (!CollectionUtil.isEmpty(list)) {
            for (SchedulerDef d : list) {
                if (d.getSchedulerName().equals(schedulerName)) {
                    zkAddress = d.getZkAddress();
                    break;
                }
            }
        }
        if (StringUtil.isBlank(zkAddress)) {
            throw new SystemException("无法获取调度器[" + schedulerName + "]配置信息中服务注册的ZK地址");
        }
        return zkAddress;
    }

    private static String getIDTSManagerGroup(String schedulerName) {
        String conf="";
		try {
			conf = CCSClientFactory.getDefaultConfigClient().get(
			        DTSConstants.OP_DTS_MANAGERSV_GROUP_PATH);
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        JSONObject data = JSONObject.parseObject(conf);
        String group = data.getString(schedulerName);
        if (StringUtil.isBlank(group)) {
            throw new SystemException("获取不到调度器[" + schedulerName + "]配置好的dubbo服务IDTSManagerSV对应的分组");
        }
        return group;

    }

    private static IDTSManagerSV getIDTSManagerSV(String registryURL, String group) {
        if (StringUtil.isBlank(registryURL)) {
            throw new SystemException("注册中心地址为空");
        }
        if (StringUtil.isBlank(group)) {
            throw new SystemException("IDTSManagerSV服务的分组为空");
        }
        ReferenceConfig<IDTSManagerSV> reference = new ReferenceConfig<IDTSManagerSV>();
        ApplicationConfig application = new ApplicationConfig();
        application.setName("runner-op");
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(registryURL);
        registry.setTimeout(100000);
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(IDTSManagerSV.class);
        if (!StringUtil.isBlank(group)) {
            reference.setGroup(group);
        }
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        if (cache.get(reference) == null) {
            throw new SystemException("从注册中心获取不到分组为[" + group + "]的服务");
        }
        return cache.get(reference);
    }
}
