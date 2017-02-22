package com.ai.opt.sdk.components.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.CryptUtils;
import com.ai.opt.sdk.util.StringUtil;

/**
 * Email工具类
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class EmailUtil {

    private static Properties prop = new Properties();
    
    private EmailUtil() {
        // 私有构造函数，不运行此类被外部实例化
    }

    static {
        InputStream is = EmailUtil.class.getClassLoader().getResourceAsStream(
                "email/email-conf.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            throw new SystemException("加载email配置出错", e);
        }
    }

    /**
     * 获取html Email
     * @return
     * @throws EmailException
     * @author
     */
    private static HtmlEmail getHtmlEmail() throws EmailException {
        String hostName = prop.getProperty("email.hostname");
        String user = prop.getProperty("email.from.user");
        String password = CryptUtils.decrypt(prop.getProperty("email.from.password"));
        String mail = prop.getProperty("email.from.mail");
        String name = prop.getProperty("email.from.name");
        String smtpportStr=prop.getProperty("email.smtpport");
        int smtpport=25;
        if(!StringUtil.isBlank(smtpportStr)){
        	smtpport=Integer.parseInt(smtpportStr);
        }
        HtmlEmail email = new HtmlEmail();
        email.setHostName(hostName);
        email.setAuthentication(user, password);
        email.setFrom(mail, name);
        email.setCharset("utf-8");
        email.setSmtpPort(smtpport);
        return email;
    }

    /**
     * 发送html Email
     * @param tomails
     * @param ccmails
     * @param subject
     * @param htmlcontext
     * @throws Exception
     * @author
     */
    public static void SendHtmlEmail(String[] tomails, String[] ccmails, String subject,
            String htmlcontext) throws Exception {
        HtmlEmail email = getHtmlEmail();
        email.addTo(tomails);
        if (!CollectionUtil.isEmpty(ccmails)) {
            email.addCc(ccmails);
        }
        email.setSubject(subject);
        email.setHtmlMsg(htmlcontext);
        email.send();
    }

}
