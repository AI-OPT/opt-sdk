package com.ai.opt.sdk.test.paas.mail;

import org.junit.Test;

import com.ai.opt.sdk.components.mail.EmailFactory;
import com.ai.opt.sdk.components.mail.EmailTemplateUtil;

public class MailTest {

	public static final String BIND_EMAIL = "email/template/uac-register-binemail.xml";

	@Test
	public void testSendBindEmail() {
		String[] tomails = new String[] { "zhouxh7@asiainfo.com" };
		String[] ccmails = new String[] { "meteor_soft@sina.cn" };
		String subject = "亚信云计费邮件测试";
		String[] data = new String[] { "云计费nickname", "587434" ,"30"};
		String htmlcontext = EmailTemplateUtil.buildHtmlTextFromTemplate(BIND_EMAIL, data);
		
		try {
			EmailFactory.SendEmail(tomails, ccmails, subject, htmlcontext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
