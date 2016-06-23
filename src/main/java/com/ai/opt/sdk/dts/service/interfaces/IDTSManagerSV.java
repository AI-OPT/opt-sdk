package com.ai.opt.sdk.dts.service.interfaces;


import java.util.List;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.dts.service.param.TaskData;

public interface IDTSManagerSV {

    /**
     * 增加或修改任务信息
     * 
     * @param task
     * @throws BusinessException,SystemException
     * @author zhangchao
     */
    void addOrUpdateTask(TaskData task) throws BusinessException,SystemException;

    /**
     * 删除指定任务
     * 
     * @param task
     * @throws Exception
     * @author zhangchao
     */
    void deleteTask(TaskData task) throws BusinessException,SystemException;

    /**
     * 获取所有任务
     * @param schedulerName
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    List<TaskData> getAllTasks(String schedulerName);

    /**
     * 获取任务明细
     * 
     * @param schedulerName
     * @param jobName
     * @param jobGroup
     * @return
     * @throws BusinessException,SystemException
     * @author zhangchao
     * @ApiDocMethod
     */
    TaskData getTaskData(String schedulerName, String jobName, String jobGroup)
            throws BusinessException,SystemException;

}
