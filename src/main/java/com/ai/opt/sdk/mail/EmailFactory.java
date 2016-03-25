package com.ai.opt.sdk.mail;

public class EmailFactory {

    private EmailFactory() {
        // 私有构造函数，不运行此类被外部实例化
    }

    public static void SendEmail(String[] tomails, String[] ccmails, String subject,
    		String htmlcontext) throws Exception {
    	SendEmailThread t=new SendEmailThread(tomails, ccmails, subject, htmlcontext);
    	t.run(); 
    }
    
    
    

}
