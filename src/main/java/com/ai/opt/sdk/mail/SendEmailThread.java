package com.ai.opt.sdk.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEmailThread implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(SendEmailThread.class);

    private String[] tomails;

    private String[] ccmails;
    
    private String subject;
    private String htmlcontext;

    public SendEmailThread(String[] tomails, String[] ccmails, String subject,
    		String htmlcontext) {
        this.tomails = tomails;
        this.ccmails = ccmails;
        this.subject = subject;
        this.htmlcontext = htmlcontext;
    }

    @Override
    public void run() {
        // 发送邮件
    	try {
			EmailUtil.SendHtmlEmail(tomails, ccmails, subject, htmlcontext);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			e.printStackTrace();
		}
    }


}
