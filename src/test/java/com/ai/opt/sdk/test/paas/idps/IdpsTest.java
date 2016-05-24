package com.ai.opt.sdk.test.paas.idps;

import org.junit.Test;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.paas.ipaas.image.IImageClient;

public class IdpsTest {
	private static IImageClient im = null;
	static {
		try {
			String idpsns="slp-mall-web-idps";
			im = IDPSClientFactory.getImageClient(idpsns);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testAddImg(){
		String filepath="E:\\qie.jpg";
		byte[] buff=ImageByteUtil.image2byte(filepath);
		
		String idpsId=im.upLoadImage(buff, "qie.jpg");
		System.out.println("idpsId="+idpsId);
		System.out.println(im.getImageUrl(idpsId, ".jpg"));
		System.out.println(im.getImageUrl(idpsId, ".jpg","100x80"));
		

	}
}
