package com.ai.opt.sdk.configcenter.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.configcenter.client.impl.ConfigCenterClient;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.helper.OptConfHelper;
import com.ai.paas.ipaas.ccs.constants.ConfigException;

public final class ConfigCenterFactory {

	private ConfigCenterFactory() {

	}

	private static Map<String, IConfigCenterClient> baseMap = new ConcurrentHashMap<String, IConfigCenterClient>();

	public static IConfigCenterClient getConfigCenterClient() {
		String appname = OptConfHelper.getInstance().getPaasConfInfo().getCcsAppName();
		String zkAddr = OptConfHelper.getInstance().getPaasConfInfo().getCcsZkAddress();
		if (!baseMap.containsKey(appname)) {
			IConfigCenterClient client = null;
			client = new ConfigCenterClient(appname, zkAddr);
			baseMap.put(appname, client);
		}
		return baseMap.get(appname);
	}

}
