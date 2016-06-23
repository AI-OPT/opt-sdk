package com.ai.opt.sdk.test.paas.dts;

import com.ai.opt.sdk.dts.constants.DTSConstants;
import com.ai.opt.sdk.dts.main.DTSMain;

public class DTSTest {

	/*paas.auth.url=http://10.1.245.4:19811/service-portal-uac-web/service/auth
		paas.auth.pid=B1F464FC22E745D79EE67B2691112795
		paas.ccs.serviceid=CCS007
		paas.ccs.servicepassword=1q2w3e4r*/
	
    private static final String PATH = "classpath:context/core-context.xml";

    public static void main(String[] args) {
        System.setProperty(DTSConstants.OPT_SCHEDULER_NAME, "opt-dts-test");
        DTSMain.main(args);
        System.out.println("OK");
    }

}
