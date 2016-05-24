package com.ai.opt.sdk.test.paas.idps;

import org.junit.Test;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.util.ImageByteUtil;
import com.ai.paas.ipaas.image.IImageClient;

public class IdpsTest {
	private static IImageClient im = null;
	static {
		try {
			//应用场景
			String idpsns="slp-mall-web-idps";
			//获取imageClient
			im = IDPSClientFactory.getImageClient(idpsns);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testAddImg(){
		//待上传的图片路径
		String filepath="E:\\qie.jpg";
		//将路径转换为byte[]
		byte[] buff=ImageByteUtil.image2byte(filepath);
		//上传图片，获取上传后的ID
		String idpsId=im.upLoadImage(buff, "qie.jpg");
		System.out.println("idpsId="+idpsId);
		//获取上传图片的URL
		System.out.println(im.getImageUrl(idpsId, ".jpg"));
		//获取上传图片指定尺寸的URL
		System.out.println(im.getImageUrl(idpsId, ".jpg","100x80"));
		

	}
}
