package com.ai.opt.sdk.appserver;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.opt.sdk.components.ses.base.AbstractSES;

public final class SESServiceStart {

    private static final Logger LOG = LogManager.getLogger(SESServiceStart.class);

    private static final String PATH = "classpath:context/core-context.xml";
    private SESServiceStart(){}
    public static void main(String[] args) {
        LOG.info("开始刷新SES索引......");
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { PATH });
        context.start();
        Map<String, AbstractSES> seses = context
                .getBeansOfType(AbstractSES.class);
        for (AbstractSES ses : seses.values()) {
            try {
                ses.write();
            } catch (Exception ex) {
                LOG.error("SES索引写入失败",ex);
            }

        }
        // 刷新缓存结束，需要提示
        LOG.info("SES索引刷新结束,请通过日志查看是否刷新成功.....");
    }
}
