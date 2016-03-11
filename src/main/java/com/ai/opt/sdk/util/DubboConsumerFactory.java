package com.ai.opt.sdk.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.opt.sdk.helper.DubboPropUtil;

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

    private synchronized static void initApplicationContext() {
        DubboPropUtil.setDubboConsumerProperties();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { PATH });
        context.start();
        appContext = context;
    }

    public static <T> T getService(String beanId, Class<T> clazz) {
        return DubboConsumerFactory.getInstance().getServiceId(beanId, clazz);
    }

    public static <T> T getService(Class<T> clazz) {
        return DubboConsumerFactory.getInstance().getServiceId(clazz);
    }

    public static <T> T getService(String beanId) {
        return DubboConsumerFactory.getInstance().getServiceId(beanId);
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

}
