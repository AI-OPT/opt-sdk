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
     * @param tomails 接受方邮件
     * @param ccmails 抄送方邮件
     * @param subject 特定说明,如:运营管理平台密码重置通知
     * @param htmlcontext 邮件内容
     * @throws Exception
     * @author
     */
    public static void SendEmail(String[] tomails, String[] ccmails, String subject,
    		String htmlcontext) throws Exception {
    	SendEmailThread t=new SendEmailThread(tomails, ccmails, subject, htmlcontext);
    	t.run(); 
    }
    
    
    

}
