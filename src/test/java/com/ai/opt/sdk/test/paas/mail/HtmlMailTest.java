package com.ai.opt.sdk.test.paas.mail;

import org.junit.Test;

import com.ai.opt.sdk.components.mail.EmailFactory;
import com.ai.opt.sdk.components.mail.EmailTemplateUtil;

public class HtmlMailTest {

	public static final String BIND_EMAIL = "email/template/yc-register_zh_cn-mail.xml";

	@Test
	public void testSendBindHtmlmail() {
		String[] tomails = new String[] { "gucl@asiainfo.com" };
		String[] ccmails = new String[] { "guchuanlong@126.com" };
		String subject = "译云注册邮件测试";
		String[] data = new String[] { "译粉001", "http://www.asiainfo.com.cn" };
		String htmlcontext = EmailTemplateUtil.buildHtmlTextFromTemplate(BIND_EMAIL, data);
		
		try {
			EmailFactory.SendEmail(tomails, ccmails, subject, htmlcontext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
