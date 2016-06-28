package com.ai.opt.sdk.appserver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.opt.sdk.components.mds.base.AbstractMdsConsumer;
import com.ai.opt.sdk.util.ApplicationContextUtil;

public final class DubboServiceStart {

	private static final Logger LOG = LoggerFactory
			.getLogger(DubboServiceStart.class.getName());

	private static final String DUBBO_CONTEXT = "classpath:dubbo/provider/dubbo-provider.xml";

	private DubboServiceStart() {
	}

	private static void startDubbo() {
		LOG.error("开始启动 Dubbo 服务---------------------------");
		// 从配置中心加载DUBBO的核心配置
		try {
			//DubboPropUtil.setDubboProviderProperties();
		} catch (Exception ex) {
			LOG.error("从配置中心加载DUBBO配置出现异常,尝试读取本地配置", ex);
		}

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { DUBBO_CONTEXT });
		context.registerShutdownHook();
		context.start();
		//对外暴露context
		ApplicationContextUtil.loadApplicationContext(context);
		LOG.error(" Dubbo 服务启动完毕---------------------------");
		//若有消息队列，则启动消费监听
		startMdsConsumer(context);
		synchronized (DubboServiceStart.class) {
			while (true) {
				try {
					DubboServiceStart.class.wait();

				} catch (Exception e) {
					LOG.error("Dubbo 系统错误，具体信息为：" + e.getMessage(), e);
				}
			}
		}
	}

	public static void main(String[] args) {
		startDubbo();
	}
	
	
	private static void startMdsConsumer(ApplicationContext context){
		 Map<String, AbstractMdsConsumer> mdses = context
	                .getBeansOfType(AbstractMdsConsumer.class);
		 if(mdses!=null&&mdses.size()>0){
			 LOG.error("开始启动MDS消息消费服务。。。。");
			 for (AbstractMdsConsumer mds : mdses.values()) {
				 try {
					 mds.startMdsConsumer();
				 } catch (Exception ex) {
					 LOG.error("启动MDS消息消费服务失败",ex);
				 }
				 
			 }
			 
			 LOG.error("启动MDS消息消费服务完毕。。。。");
		 }
       
	}
}
