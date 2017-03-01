package com.ai.opt.sdk.components.mail;

import java.text.MessageFormat;

/**
 * html邮件模板工具类
 *
 * Date: 2016年3月25日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author gucl
 */
public class EmailTemplateUtil {

    private EmailTemplateUtil() {

    }
    /**
     * 生产html邮件内容
     * @param templateClassPath  邮件模板classpath路径，如"email/template/upgrade-notify-public.xml"
     * @param data 数据对象
     * @return  邮件内容
     * @author gucl
     * @ApiDocMethod
     * @ApiCode
     */
    public static String buildHtmlTextFromTemplate(String templateClassPath,Object[] data) {
        String tmp = EmailTemplateReader.read(templateClassPath);
        String html = MessageFormat.format(tmp, data);
        return html;
    }
    

}
