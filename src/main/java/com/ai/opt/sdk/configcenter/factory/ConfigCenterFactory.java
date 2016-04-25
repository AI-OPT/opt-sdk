package com.ai.opt.sdk.configcenter.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.configcenter.client.impl.ConfigCenterClient;
import com.ai.opt.sdk.helper.OptConfHelper;

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
	/**
	 * 根据appname和zkAddr获取配置中心客户端（用于配置中心的web页面化）
	 * @param appname
	 * @param zkAddr
	 * @return
	 * @author gucl
	 */
	public static IConfigCenterClient getConfigCenterClient(String appname,String zkAddr) {
		if (!baseMap.containsKey(appname)) {
			IConfigCenterClient client = null;
			client = new ConfigCenterClient(appname, zkAddr);
			baseMap.put(appname, client);
		}
		return baseMap.get(appname);
	}

}
