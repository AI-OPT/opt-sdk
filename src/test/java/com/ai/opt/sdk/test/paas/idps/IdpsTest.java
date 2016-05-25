package com.ai.opt.sdk.test.paas.idps;

import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.util.ImageByteUtil;
import com.ai.paas.ipaas.image.IImageClient;

public class IdpsTest {
	/**
	 * 测试需要的paas-conf配置信息
	 *  paas.auth.url=http://10.1.245.4:19811/service-portal-uac-web/service/auth
		paas.auth.pid=B1F464FC22E745D79EE67B2691112795
		paas.ccs.serviceid=CCS005
		paas.ccs.servicepassword=1q2w3e4r
	 * 
	 * @author gucl
	 * @ApiDocMethod
	 * @ApiCode
	 */
	@Ignore
	@Test
	public void testAddImg(){
		//应用场景
		String idpsns="slp-mall-web-idps";
		//获取imageClient
		IImageClient im = IDPSClientFactory.getImageClient(idpsns);
		
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
		System.out.println(im.getImageUrl(idpsId, ".png","100x80"));
		

	}
}
