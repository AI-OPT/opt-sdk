package com.ai.opt.sdk.dts.constants;


public class DTSConstants {

    private DTSConstants() {

    }

    // 调度器配置信息
    // [{"schedulerName":"runner-bis","systemName":"BIS产品","zkAddress":"zookeeper://10.1.228.222:19181"}]
    public static final String OP_DTS_SCHEDULER_DEF_PATH = "/com/ai/runner/op/dts/scheduler-def";

    // 调度器与其dubbo服务分组的映射关系 {"runner-bis":"runner.bis.xxx"}
    public static final String OP_DTS_MANAGERSV_GROUP_PATH = "/com/ai/runner/op/dts/dtsmanagersv-group";

}
