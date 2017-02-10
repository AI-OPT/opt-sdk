package com.ai.opt.sdk.components.mail;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ai.opt.base.exception.SystemException;

/**
 * Email模版
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class EmailTemplateReader {

    public static String read(String path) {
        String str = reader(path);
        return str;
    }

    /**
     * 读取模版
     * @param path
     * @return
     * @author
     */
    private static String reader(String path) {
        SAXReader reader = new SAXReader();
        String str = null;
        try {
            InputStream is = EmailTemplateReader.class.getClassLoader().getResourceAsStream(path);
            Document d = reader.read(is);
            Element e = d.getRootElement();
            Element htmle = e.element("html");
            str = htmle.asXML();
        } catch (DocumentException e) {
            throw new SystemException("解析Email模板出错", e);
        }
        return str;

    }
}
