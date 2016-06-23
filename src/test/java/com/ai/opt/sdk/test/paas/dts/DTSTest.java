package com.ai.opt.sdk.test.paas.dts;

import com.ai.opt.sdk.dts.main.DTSMain;

public class DTSTest {

    private static final String PATH = "classpath:context/core-context.xml";

    public static void main(String[] args) {
        System.setProperty("opt.system.name", "opt-dts-test-aaa");
        DTSMain.main(args);
        System.out.println("OK");
    }

}
