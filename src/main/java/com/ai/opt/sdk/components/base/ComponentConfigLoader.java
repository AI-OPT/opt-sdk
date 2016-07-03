package com.ai.opt.sdk.components.base;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.opt.sdk.util.StringUtil;
import com.alibaba.fastjson.JSON;

/**
 * 平台技术组件配置加载器<br>
 * Date: 2016年5月5日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author gucl
 */
public final class ComponentConfigLoader {
	private static final Logger LOG = LoggerFactory.getLogger(ComponentConfigLoader.class);

    private static ComponentConfigLoader INSTANCE = null;

    private static Properties prop;

    private ComponentConfigLoader() {
        // 禁止实例化
    }

    /**
     * 获取配置加载器单例实例，确保多线程并发情况下高效读取，避免INSTANCE引用指向不同的实例对象
     * 
     * @return
     * @author zhangchao
     */
    public static ComponentConfigLoader getInstance() {
    	LOG.debug("【getInstance INSTANCE  开始。。。】");
        if (INSTANCE == null || prop==null) {
            // 多线程并发获取实例时候，避免等线程锁造成性能低下，因此在创建实例时候进行同步处理
        	LOG.debug("【INSTANCE和prop均为空，ComponentConfigLoader需进行实例化】");
            synchronized (ComponentConfigLoader.class) {
                if (INSTANCE == null) {
                	LOG.debug("【线程锁synchronized内，INSTANCE仍为空，ComponentConfigLoader需进行实例化】");
                    INSTANCE = new ComponentConfigLoader();
                    LOG.debug("【ComponentConfigLoader实例化结束】");
                    INSTANCE.loadProp();
                    LOG.debug("【INSTANCE加载prop结束】");
                }
            }
        }
        LOG.debug("【getInstance INSTANCE  结束。。。】"+JSON.toJSONString(INSTANCE));
        return INSTANCE;

    }
    public static ComponentConfigLoader loadPaaSConf(Properties p) {
    	LOG.debug("【loadPaaSConf INSTANCE  开始。。。】");
    	  if (INSTANCE == null || prop==null) {
              // 多线程并发获取实例时候，避免等线程锁造成性能低下，因此在创建实例时候进行同步处理
    		  LOG.debug("【loadPaaSConf INSTANCE和prop均为空，ComponentConfigLoader需进行实例化】");
              synchronized (ComponentConfigLoader.class) {
                  if (INSTANCE == null) {
                	  LOG.debug("【loadPaaSConf 线程锁synchronized内，INSTANCE仍为空，ComponentConfigLoader需进行实例化】");
                      INSTANCE = new ComponentConfigLoader();
                      LOG.debug("【loadPaaSConf ComponentConfigLoader实例化结束】");
                      INSTANCE.loadProp(p);
                      LOG.debug("【loadPaaSConf INSTANCE加载prop结束】");
                  }
              }
          }
    	  LOG.debug("【loadPaaSConf INSTANCE  结束。。。】:"+JSON.toJSONString(INSTANCE));
          return INSTANCE;
    	
    }

    private void loadProp(Properties p) {
    	prop=p;
    }
    /**
     * 加载配置文件
     * 
     * @author zhangchao
     */
    private void loadProp() {
        InputStream is = ComponentConfigLoader.class.getClassLoader().getResourceAsStream(SDKConstants.PAAS_CONFIG_FILE);
        LOG.debug("【加载的属性文件流】:"+JSON.toJSONString(is));
        try {
            prop = new Properties();
            prop.load(is);
            LOG.debug("【加载的属性文件prop】:"+JSON.toJSONString(prop));
        } catch (IOException e) {
            throw new SDKException("loding paas config file failed", e);
        }
    }

    public PaasConf getPaasAuthInfo() {
        String authUrl = prop.getProperty("paas.auth.url");
        String pid = prop.getProperty("paas.auth.pid");
        String ccsServiceId = prop.getProperty("paas.ccs.serviceid");
        String ccsServicePwd = prop.getProperty("paas.ccs.servicepassword");
        String paasSdkMode = prop.getProperty("paas.sdk.mode");
        String ccsAppName = prop.getProperty("ccs.appname");
        String zkAddress = prop.getProperty("ccs.zk_address");
        
        /*if (StringUtil.isBlank(authUrl)) {
            throw new SDKException("paas auth url is null");
        }
        if (StringUtil.isBlank(pid)) {
            throw new SDKException("paas auth pid is null");
        }
        if (StringUtil.isBlank(ccsServiceId)) {
            throw new SDKException("paas ccs serviceid is null");
        }
        if (StringUtil.isBlank(ccsServicePwd)) {
            throw new SDKException("paas ccs service password is null");
        }*/
        PaasConf pc = new PaasConf();
        pc.setAuthUrl(authUrl);
        pc.setPid(pid);
        pc.setCcsServiceId(ccsServiceId);
        pc.setCcsPassword(ccsServicePwd);
        pc.setPaasSdkMode(paasSdkMode);
        pc.setCcsAppName(ccsAppName);
        pc.setCcsZkAddress(zkAddress);
        return pc;
    }

}
