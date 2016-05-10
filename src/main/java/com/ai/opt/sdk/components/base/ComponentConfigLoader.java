package com.ai.opt.sdk.components.base;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.opt.sdk.util.StringUtil;

/**
 * 平台技术组件配置加载器<br>
 * Date: 2016年5月5日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author gucl
 */
public final class ComponentConfigLoader {

    private static ComponentConfigLoader INSTANCE = null;

    private Properties prop;

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
        if (INSTANCE == null) {
            // 多线程并发获取实例时候，避免等线程锁造成性能低下，因此在创建实例时候进行同步处理
            synchronized (ComponentConfigLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ComponentConfigLoader();
                    INSTANCE.loadProp();
                }
            }
        }
        return INSTANCE;

    }
    public static ComponentConfigLoader loadPaaSConf(Properties p) {
    	  if (INSTANCE == null) {
              // 多线程并发获取实例时候，避免等线程锁造成性能低下，因此在创建实例时候进行同步处理
              synchronized (ComponentConfigLoader.class) {
                  if (INSTANCE == null) {
                      INSTANCE = new ComponentConfigLoader();
                      INSTANCE.loadProp(p);
                  }
              }
          }
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
        try {
            prop = new Properties();
            prop.load(is);
        } catch (IOException e) {
            throw new SDKException("loding paas config file failed", e);
        }
    }

    public PaasConf getPaasAuthInfo() {
        String authUrl = prop.getProperty("paas.auth.url");
        String pid = prop.getProperty("paas.auth.pid");
        String ccsServiceId = prop.getProperty("paas.ccs.serviceid");
        String ccsServicePwd = prop.getProperty("paas.ccs.servicepassword");
        
        if (StringUtil.isBlank(authUrl)) {
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
        }
        PaasConf pc = new PaasConf();
        pc.setAuthUrl(authUrl);
        pc.setPid(pid);
        pc.setCcsServiceId(ccsServiceId);
        pc.setCcsPassword(ccsServicePwd);
        return pc;
    }

}
