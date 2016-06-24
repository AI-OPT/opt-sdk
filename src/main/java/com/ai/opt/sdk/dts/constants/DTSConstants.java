package com.ai.opt.sdk.dts.constants;


public class DTSConstants {

    private DTSConstants() {

    }
    /**
     * 调度器启动时的参数名称
     */
    public static final String OPT_SCHEDULER_NAME="opt.scheduler.name";

    
    
    
    
    // (用户web页面操作)调度器配置信息
    // [{"schedulerName":"opt-dts-test","systemName":"SLP产品dts测试","zkAddress":"zookeeper://10.1.245.9:29181"}]
    public static final String OP_DTS_SCHEDULER_DEF_PATH = "/com/ai/opt/op/dtsweb/scheduler-def";

    // (用户web页面操作)调度器与其dubbo服务分组的映射关系 {"opt-dts-test":"opt.slp.xxx"}
    public static final String OP_DTS_MANAGERSV_GROUP_PATH = "/com/ai/opt/op/dtsweb/dtsmanagersv-group";

}
