package com.ai.opt.sdk.dubbo.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Title: MVNO-CRM <br>
 * Description: 客户消费端获取服务端服务实例<br>
 * Date: 2014年3月27日 <br>
 * Copyright (c) 2014 AILK <br>
 * 
 * @author zhangchao
 */
public class DubboConsumerFactory {

    private static final String PATH = "dubbo/consumer/dubbo-consumer.xml";

    private static ApplicationContext appContext;

    private static DubboConsumerFactory instance;

    /**
     * 获取实例
     * @return
     * @author
     */
    private static DubboConsumerFactory getInstance() {
        if (instance == null) {
            synchronized (DubboConsumerFactory.class) {
                if (instance == null) {
                    instance = new DubboConsumerFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化上下文
     * 
     * @author
     */
    private synchronized static void initApplicationContext() {
        //DubboPropUtil.setDubboConsumerProperties();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { PATH });
        context.start();
        appContext = context;
    }

    /**
     * 获取服务
     * @param beanId
     * @param clazz
     * @return
     * @author
     */
    public static <T> T getService(String beanId, Class<T> clazz) {
        return DubboConsumerFactory.getInstance().getServiceId(beanId, clazz);
    }

    /**
     * 获取服务
     * @param clazz
     * @return
     * @author
     */
    public static <T> T getService(Class<T> clazz) {
        return DubboConsumerFactory.getInstance().getServiceId(clazz);
    }

    /**
     * 获取服务
     * @param beanId
     * @return
     * @author
     */
    public static <T> T getService(String beanId) {
        return DubboConsumerFactory.getInstance().getServiceId(beanId);
    }

    /**
     * 获取服务Id
     * @param beanId
     * @param clazz
     * @return
     * @author
     */
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

    /**
     * 获取服务Id
     * @param clazz
     * @return
     * @author
     */
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

    /**
     * 获取服务Id
     * @param beanId
     * @return
     * @author
     */
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

}
