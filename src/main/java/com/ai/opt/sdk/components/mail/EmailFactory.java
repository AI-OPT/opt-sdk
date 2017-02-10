package com.ai.opt.sdk.components.mail;

/**
 * Email工厂
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class EmailFactory {

    private EmailFactory() {
        // 私有构造函数，不运行此类被外部实例化
    }

    /**
     * 发送邮件
     * @param tomails
     * @param ccmails
     * @param subject
     * @param htmlcontext
     * @throws Exception
     * @author
     */
    public static void SendEmail(String[] tomails, String[] ccmails, String subject,
    		String htmlcontext) throws Exception {
    	SendEmailThread t=new SendEmailThread(tomails, ccmails, subject, htmlcontext);
    	t.run(); 
    }
    
    
    

}
