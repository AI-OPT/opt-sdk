package com.ai.opt.sdk.test.paas.elasticjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JobStartTest {
	private static final Logger LOG = LoggerFactory
			.getLogger(JobStartTest.class.getName());
	private static final String ElasticJob_CONTEXT = "classpath:elasticjob/elasticjob-context.xml";

	public static void main(String[] args) {
		LOG.error("开始启动 ElasticJob 服务---------------------------");
		// 从配置中心加载ElasticJob的核心配置
		try {
			//ElasticJobPropUtil.setElasticJobProviderProperties();
		} catch (Exception ex) {
			LOG.error("从配置中心加载ElasticJob配置出现异常,尝试读取本地配置", ex);
		}
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { ElasticJob_CONTEXT });
		//context.registerShutdownHook();
		//context.start();
		//对外暴露context
		LOG.error(" ElasticJob 服务启动完毕---------------------------");
		//若有消息队列，则启动消费监听
		synchronized (JobStartTest.class) {
			while (true) {
				try {
					JobStartTest.class.wait();
					
				} catch (Exception e) {
					LOG.error("ElasticJob 系统错误，具体信息为：" + e.getMessage(), e);
				}
			}
		}
	}
	
}
