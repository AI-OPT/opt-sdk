package com.ai.opt.sdk.dts.service.interfaces;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.dts.service.param.TaskCond;
import com.ai.opt.sdk.dts.service.param.TaskData;


@Path("/dtsmng")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IDTSManagerSV {

    /**
     * 增加或修改任务信息
     * 
	 * @param task
	 * @throws BusinessException
	 * @throws SystemException
	 * @author gucl
	 * @RestRelativeURL dtsmng/addOrUpdateTask
	 */
	@POST
	@Path("/addOrUpdateTask")
    void addOrUpdateTask(TaskData task) throws BusinessException,SystemException;

    /**
     * 删除指定任务
	 * @param task
	 * @throws BusinessException
	 * @throws SystemException
	 * @author gucl
	 * @RestRelativeURL dtsmng/deleteTask
	 */
	@POST
	@Path("/deleteTask")
    void deleteTask(TaskData task) throws BusinessException,SystemException;

    /**
     * 获取所有任务
     * @param schedulerName
     * @return
     * @author gucl
     * @RestRelativeURL dtsmng/getAllTasks
     */
	@POST
	@Path("/getAllTasks")
    List<TaskData> getAllTasks(String schedulerName);

    /**
     * 获取任务明细
     * @param taskCond
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author gucl
     * @RestRelativeURL dtsmng/getTaskData
     */
    @POST
	@Path("/getTaskData")
    TaskData getTaskData(TaskCond taskCond)
            throws BusinessException,SystemException;

}
