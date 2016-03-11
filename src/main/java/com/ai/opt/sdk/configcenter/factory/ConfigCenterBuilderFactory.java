package com.ai.opt.sdk.configcenter.factory;

import java.util.HashMap;
import java.util.Map;

import com.ai.opt.sdk.configcenter.builder.IConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.builder.impl.PaaSConfigCenterBuilder;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.helper.OptConfHelper;

public final class ConfigCenterBuilderFactory {
    
    private ConfigCenterBuilderFactory(){
        
    }

    // IPAAS的CCS提供配置服务
    private static final String IPAAS_CCS_TYPE = "ipaas_ccs";

    private static Map<String, IConfigCenterBuilder> baseMap = new HashMap<String, IConfigCenterBuilder>();

    public static IConfigCenterBuilder getConfigCenterBuilder() {
        String ccType = OptConfHelper.getInstance().getConfigCenterType();
        if (!baseMap.containsKey(ccType)) {
            IConfigCenterBuilder builder = getInstance(ccType);
            baseMap.put(ccType, builder);
        }
        return baseMap.get(ccType);
    }

    private static IConfigCenterBuilder getInstance(String ccType) {
        IConfigCenterBuilder builder = null;
        if (IPAAS_CCS_TYPE.equals(ccType)) {
            builder = new PaaSConfigCenterBuilder();
        } else {
            throw new OptSDKException("not supported configcenter type[" + ccType + "]");
        }
        return builder;
    }

}
