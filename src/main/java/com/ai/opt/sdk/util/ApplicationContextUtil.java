package com.ai.opt.sdk.util;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationContextUtil  <br>
 * Description: 获取应用程序中的spring context<br>
 * Date: 2016年6月28日 <br>
 * Copyright (c) 2016 AI <br>
 * 
 * @author gucl
 */
public class ApplicationContextUtil {
    //约定 spring context的路径
    private static final String PATH = "classpath:context/core-context.xml";

    private static AbstractApplicationContext appContext;

    private static ApplicationContextUtil instance;

    private static ApplicationContextUtil getInstance() {
        if (instance == null) {
            synchronized (ApplicationContextUtil.class) {
                if (instance == null) {
                    instance = new ApplicationContextUtil();
                }
            }
        }
        return instance;
    }
    /**
     * 用于外部接口传入spring context
     * @param ctx
     * @author gucl
     */
    public synchronized static void loadApplicationContext(AbstractApplicationContext ctx){
    	appContext=ctx;
    	instance = new ApplicationContextUtil();
    }

    private synchronized static void initApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { PATH });
        context.start();
        appContext = context;
    }

    public static <T> T getService(String beanId, Class<T> clazz) {
        return ApplicationContextUtil.getInstance().getServiceId(beanId, clazz);
    }

    public static <T> T getService(Class<T> clazz) {
        return ApplicationContextUtil.getInstance().getServiceId(clazz);
    }

    public static <T> T getService(String beanId) {
        return ApplicationContextUtil.getInstance().getServiceId(beanId);
    }

    private <T> T getServiceId(String beanId, Class<T> clazz) {
        if (appContext == null) {
            synchronized (this) {
                if (appContext == null) {
                    initApplicationContext();
                }
            }
        }
        Object t = (T) appContext.getBean(beanId, clazz);
        if (t == null) {
            synchronized (appContext) {
                appContext = null;
                initApplicationContext();
            }
        }
        return (T) appContext.getBean(beanId, clazz);
    }

    private <T> T getServiceId(Class<T> clazz) {
        if (appContext == null) {
            synchronized (this) {
                if (appContext == null) {
                    initApplicationContext();
                }
            }
        }
        Object t = (T) appContext.getBean(clazz);
        if (t == null) {
            synchronized (appContext) {
                appContext = null;
                initApplicationContext();
            }
        }
        return (T) appContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> T getServiceId(String beanId) {
        if (appContext == null) {
            synchronized (this) {
                if (appContext == null) {
                    initApplicationContext();
                }
            }
        }
        Object t = (T) appContext.getBean(beanId);
        if (t == null) {
            synchronized (appContext) {
                appContext = null;
                initApplicationContext();
            }
        }
        return (T) appContext.getBean(beanId);
    }
    public static void closeAppContext() {
        if(null != appContext) {
            appContext.close();
        }
    }

}
