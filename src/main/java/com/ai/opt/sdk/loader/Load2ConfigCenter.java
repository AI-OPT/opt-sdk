package com.ai.opt.sdk.loader;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.paas.ipaas.util.StringUtil;
import com.ai.opt.sdk.configcenter.builder.IConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.builder.impl.PaaSConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;

/**
 * 将配置文件加载到配置中心<br>
 * Date: 2015年7月28日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * 
 * @author zhangchao
 */
public final class Load2ConfigCenter {

    private static final Log LOG = LogFactory.getLog(Load2ConfigCenter.class);

    private Load2ConfigCenter() {

    }

    public static void main(String[] args) {
        LOG.debug("开始将配置服务信息加载到配置中心");
        if (args == null || args.length == 0) {
            LOG.error("请输入配置文件路径");
            System.exit(-1);
        }
        if (args.length > 1) {
            LOG.error("只有一个参数，实际有[" + args.length + "]个");
            System.exit(-1);
        }
        String configPath = args[0];
        if (StringUtil.isBlank(configPath)) {
            LOG.error("请传入配置的路径，采用-DconfigPath");
            System.exit(-1);
        }
        Properties p = loadFiles(configPath);
        read(p);
        LOG.debug("配置加载完成");
    }

    private static Properties loadFiles(String configDir) {
        Properties p = new Properties();

        File configFileDir = new File(configDir);
        if (!configFileDir.exists()) {
            System.exit(-1);
        }

        File[] configfiles = configFileDir.listFiles(new FileFilter() {
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
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
                p.load(is);
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
        return p;
    }

    private static void read(Properties p) {
        Iterator<Map.Entry<Object, Object>> it = p.entrySet().iterator();
        IConfigCenterBuilder builder = new PaaSConfigCenterBuilder();
        IConfigCenterClient client = builder.getConfigCenterClient();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String path = entry.getKey().toString();
            String value = entry.getValue().toString();
            LOG.debug("获取到的路径=" + path);
            LOG.debug("获取的值=" + value);
            if (client.exists(path)) {
                client.modify(path, value);
            } else {
                client.add(path, value);
            }
        }
    }
}
