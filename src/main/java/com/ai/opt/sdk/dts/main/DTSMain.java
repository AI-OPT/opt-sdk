package com.ai.opt.sdk.dts.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.dts.factory.DTSSchedulerFactory;
import com.ai.paas.ipaas.util.StringUtil;

public class DTSMain {

    private static final Logger LOG = LoggerFactory.getLogger(DTSMain.class);

    private DTSMain() {

    }

    public static void main(String[] args) {
        LOG.debug("开始启动dts调度任务..");
        String systemName = System.getProperty("opt.system.name");
        if (StringUtil.isBlank(systemName)) {
            LOG.error("无法从启动参数获取系统名称，请检查参数[-Dopt.system.name=XX]");
            System.exit(-1);
        }

        try {
            DTSSchedulerFactory.start(systemName);
            LOG.error("系统[" + systemName + "]的DTS实例启动没有报错，请耐心观察后续日志是否成功");
        } catch (Exception ex) {
            LOG.error("系统[" + systemName + "]的DTS实例启动失败", ex);
            System.exit(-1);
        }
        while (true) {
            try {
                Thread.currentThread();
                Thread.sleep(10000L);
            } catch (Exception e) {
                LOG.error("DTS系统错误，具体信息为：" + e.getMessage(), e);
            }
        }

    }

}
