package com.ai.opt.sdk.test.paas.idps;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.image.ImageClientFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;

public class IdpsSunhjTest {
	private static final String AUTH_ADDR = "http://10.1.245.4:19811/service-portal-uac-web/service/auth";
	private static AuthDescriptor ad = null;
	private static IImageClient im = null;

	static {
		ad = new AuthDescriptor(AUTH_ADDR, "4BCEDD88820D4E5295568DA835C3FB6F",
				"123456", "IDPS005");
		try {
			im = ImageClientFactory.getClient(ad);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deTest() {

		// System.out.println(im.getUpImageUrl());
		byte[] buffer = null;
		try {
			File file = new File(
					"e:\\qie.jpg");
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String id = im.upLoadImage(buffer, "me.png");

		System.out.println(id);
	}

}
