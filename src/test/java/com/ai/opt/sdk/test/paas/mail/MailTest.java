package com.ai.opt.sdk.test.paas.mail;

import org.junit.Test;

import com.ai.opt.sdk.mail.EmailFactory;
import com.ai.opt.sdk.mail.EmailTemplateUtil;
import com.ai.opt.sdk.util.DateUtil;

public class MailTest {

	public static final String UPGRADE_NOTIFY_PUBLIC = "email/template/upgrade-notify-public.xml";

	@Test
	public void testSend() {
		String[] tomails = new String[] { "gucl@asiainfo.com" };
		String[] ccmails = new String[] { "guchuanlong@126.com" };
		String subject = "亚信云计费邮件测试";
		String[] data = new String[] { "EmailFactory亚信云计费邮件测试亚信云计费邮件测试亚信云计费邮件测试"+DateUtil.getCurrentTime(), "EmailFactoryasdfdsafaf1232434"+DateUtil.getCurrentTime() };
		String htmlcontext = EmailTemplateUtil.buildHtmlTextFromTemplate(UPGRADE_NOTIFY_PUBLIC, data);

		try {
			EmailFactory.SendEmail(tomails, ccmails, subject, htmlcontext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
