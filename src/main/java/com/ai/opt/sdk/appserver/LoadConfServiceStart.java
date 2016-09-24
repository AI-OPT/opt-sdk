package com.ai.opt.sdk.appserver;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;

/**
 * 加载各模块配置文件到zk
 *
 * Date: 2016年7月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author gucl
 */
public class LoadConfServiceStart {
	private static final Logger LOG = LoggerFactory.getLogger(LoadConfServiceStart.class);

    private LoadConfServiceStart() {

    }

    /**
     * 加载各模块配置文件到CCS.<br>
     * 约定：将所有的配置文件(properties)放到同一目录下，每个配置文件的名字对应paas-conf.properties中的ccs.appname<br>
     * 如：        
     * config目录下有如下属性文件<br>
     * 		 aiopt-baas-bmc.properties<br>
     * 		 aiopt-baas-amc.properties<br>
     * 	  	 aiopt-baas-dshm.properties<br>
     * 		 aiopt-baas-rtm.properties<br>
     * main 方法必须有且仅有一个参数 ，参数值为属性文件所在的文件夹目录 <br>
     * @param args  属性文件所在的文件夹目录 ,如  e:\config<br>
     * @author gucl
     */
    public static void main(String[] args) {
        LOG.error("开始将属性文件(properties)加载到配置中心");
        if (args == null || args.length == 0) {
            LOG.error("请输入配置文件路径");
            return;
        	//System.exit(-1);
        }
        if (args.length > 1) {
            LOG.error("只允许有一个参数，实际有[" + args.length + "]个");
            return;
        	//System.exit(-1);
        }
        String configPath = args[0];
        if (StringUtil.isBlank(configPath)) {
            LOG.error("请传入配置的路径，采用-DconfigPath");
            return;
        	//System.exit(-1);
        }
        loadFiles(configPath);
        LOG.error("完成属性文件(properties)加载到配置中心");
    }

    private static Map<String,Properties> loadFiles(String configPath) {
    	Map<String,Properties> propList=new HashMap<String,Properties>();

        File configPathDir = new File(configPath);
        if (!configPathDir.exists()) {
        	LOG.error("传入的配置路径【"+configPath+"】不存在，请重新检查configPath");
            return null;
        	//System.exit(-1);
        }

        File[] configfiles = configPathDir.listFiles(new FileFilter() {
            private String extension = "properties";

            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return false;
                }
                String name = file.getName();

                int index = name.lastIndexOf('.');
                if (index == -1) {
                    return false;
                }

                if (index == name.length() - 1) {
                    return false;
                }
                return this.extension.equals(name.substring(index + 1));
            }
        });

        for (File file : configfiles) {
        	Properties p = new Properties();
        	String fileName = file.getName();
        	int index = fileName.lastIndexOf('.');
        	String ccsAppName=fileName.substring(0,index);
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
                p.load(is);
                propList.put(ccsAppName, p);
                loadProp2ccs(ccsAppName,p);
            } catch (FileNotFoundException e) {
                LOG.error("FileNotFoundException", e);
            } catch (IOException e) {
                LOG.error("IOException", e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        LOG.error("IOException", e);
                    }
                }
            }
        }
        return propList;
    }

    private static void loadProp2ccs(String appName,Properties p) {
    	LOG.error("开始加载【{}】模块的配置信息到zk",appName);
        Iterator<Map.Entry<Object, Object>> it = p.entrySet().iterator();
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
		String zkAddr = authInfo.getCcsZkAddress();
        IConfigClient client = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String path = entry.getKey().toString();
            String value = entry.getValue().toString();
            LOG.error("设置【{}】模块的路径及值信息【{}={}】" ,appName, path, value);
            try {
				if (client.exists(path)) {
				    client.modify(path, value);
				} else {
				    client.add(path, value);
				}
			} catch (ConfigException e) {
				LOG.error("加载配置失败！！！ 加载【{}】的配置信息到zk失败，具体原因：{}",appName,e.getMessage(), e);
			}
        }// end while
        LOG.error("完成加载【{}】模块的配置信息到zk",appName);
        
    }
}
