package com.ai.opt.sdk.configcenter.client.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.PaaSConstant;
import com.ai.paas.ipaas.ccs.constants.AddMode;
import com.ai.paas.ipaas.ccs.zookeeper.ConfigWatcher;
import com.ai.paas.ipaas.ccs.zookeeper.MutexLock;
import com.ai.paas.ipaas.ccs.zookeeper.ZKClient;
import com.ai.paas.ipaas.ccs.zookeeper.impl.ZKPool;
import com.ai.paas.ipaas.ccs.zookeeper.impl.ZKPoolFactory;

public class ConfigCenterClient implements IConfigCenterClient {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigCenterClient.class);
	//无授权信息
	private static final String NO_AUTHINFO=":";
	// 应用程序标识
	private String appname;
	// Zookeeper授权信息
	private String authInfo;
	// Zookeeper用户
	private String zkUser="";
	// Zookeeper用户
	private String zkPassword="";
	// Zookeeper客户端连接池
	private ZKPool zkPool;
	// Zookeeper地址
	private String zkAddr;
	// Zookeeper授权方式
	private String zkAuthSchema="digest";

	public ConfigCenterClient(String appname, String zkAddr)  {
		try {
			this.appname = appname;
			this.authInfo = NO_AUTHINFO;
			this.zkAddr = zkAddr;
			this.zkPool = ZKPoolFactory.getZKPool(zkAddr, 2000,new String[0]);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new OptSDKException(e.getMessage(), e);
		}
	}
	public ConfigCenterClient(String appname, String zkAddr, int timeout)  {
		try {
			this.appname = appname;
			this.authInfo = NO_AUTHINFO;
			this.zkAddr = zkAddr;
			this.zkPool = ZKPoolFactory.getZKPool(zkAddr, timeout,new String[0]);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new OptSDKException(e.getMessage(), e);
		}
	}
	public ConfigCenterClient(String appname, String zkAddr, String zkUser,
			String zkPassword, int timeout)  {
		try {
			this.appname = appname;
			this.authInfo = (zkUser + ":" + zkPassword);
			this.zkAddr = zkAddr;
			this.zkPool = ZKPoolFactory.getZKPool(zkAddr, this.zkUser, this.zkPassword, timeout);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new OptSDKException(e.getMessage(), e);
		}
	}
	/**
	 * 组装节点真实路径
	 * @param path
	 * @return
	 * @author gucl
	 */
	private String appendAppname(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append(PaaSConstant.UNIX_SEPERATOR + this.appname);
		if(!StringUtil.isBlank(path)&&!path.startsWith(PaaSConstant.UNIX_SEPERATOR)){
			sb.append(PaaSConstant.UNIX_SEPERATOR);
		}
		sb.append(path);
		LOG.debug("Real path=" + sb.toString());
		return sb.toString();
	}

	@Override
	public String get(String path)  {
		try {
			if (!exists(path)) {
				throw new OptSDKException("节点[" + path + "]不存在");
			}
			return get(path, null);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new OptSDKException("获取节点数据失败", e);
		}
	}

	@Override
	public void add(String path, String config)  {
		byte[] data = null;
		if (!StringUtil.isBlank(config)) {
			try {
				data = config.getBytes(PaaSConstant.CHARSET_UTF8);
				add(path, data, AddMode.PERSISTENT);
			} catch (UnsupportedEncodingException | OptSDKException e) {
				LOG.error("添加节点[" + path + "]数据["+config+"]失败",e);
				throw new OptSDKException("添加节点[" + path + "]数据["+config+"]失败", e);
			}
		}

	}

	@Override
	public void modify(String path, String config)  {
		try {
			if (!exists(path)) {
				throw new OptSDKException("节点[" + path + "]不存在");
			}
			byte[] data = null;
			if (!StringUtil.isBlank(config)) {
				try {
					data = config.getBytes(PaaSConstant.CHARSET_UTF8);
				} catch (UnsupportedEncodingException e) {
					throw new OptSDKException(e.getMessage(), e);
				}
				modify(path, data);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new OptSDKException("修改节点[" + path + "]配置失败", e);
		}
	}

	@Override
	public void modify(String path, byte[] value)  {
		if (!exists(path)) {
			throw new OptSDKException("节点[" + path + "]不存在");
		}
		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			client.setNodeData(appendAppname(path), value);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("无访问节点[" + path + "]权限");
			}
			throw new OptSDKException("修改节点[" + path + "]失败", e);
		}
	}

	@Override
	public boolean exists(String path)  {
		return exists(path, null);
	}

	@Override
	public boolean exists(String path, ConfigWatcher configWatcher)  {
		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			if (null != configWatcher) {
				return client.exists(appendAppname(path), configWatcher);
			}
			return client.exists(appendAppname(path));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("无访问节点[" + path + "]权限");
			}
			throw new OptSDKException("节点[" + path + "]不存在", e);
		}
	}

	@Override
	public String get(String path, ConfigWatcher watcher)  {
		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			return client.getNodeData(appendAppname(path), watcher);
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("无访问节点[" + path + "]权限");
			}
			throw new OptSDKException("节点[" + path + "]不存在", e);
		}
	}

	private ZKClient getZkClientFromPool() throws Exception {
		ZKClient zkClient = this.zkPool.getZkClient(this.zkAddr, this.zkUser);

		if (zkClient == null) {
			throw new OptSDKException("获取zkClient失败");
		}
		return zkClient;
	}

	@Override
	public void add(String path, byte[] bytes, AddMode mode)  {
		if (exists(path)) {
			throw new OptSDKException("节点[" + path + "]已存在，不能重复添加");
		}

		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			client.createNode(appendAppname(path), createWritableACL(),bytes,AddMode.convertMode(mode.getFlag()));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("无访问节点[" + path + "]权限");
			}
			throw new OptSDKException("添加节点[" + path + "]失败", e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ACL> createWritableACL() throws NoSuchAlgorithmException {
		//如果授权信息里没有用户名和密码，则访问控制列表面向所有人
		if(NO_AUTHINFO.equalsIgnoreCase(this.authInfo.trim())){
			return ZooDefs.Ids.OPEN_ACL_UNSAFE;
		}
		else{
			//访问控制列表为当前的authInfo信息
			List<ACL> acls = new ArrayList();
			Id id1 = new Id(this.zkAuthSchema, DigestAuthenticationProvider.generateDigest(this.authInfo));
			ACL userACL = new ACL(31, id1);
			acls.add(userACL);
			return acls;
			
		}
	}

	@Override
	public MutexLock getMutexLock(String path)  {
		if (!validatePath(path))
			throw new OptSDKException("path[" + path + "]不合法,必须以'/'开头，且不能以'/'结尾");
		ZKClient zkClient = null;
		try {
			zkClient = getZkClientFromPool();
			return new MutexLock(zkClient.getInterProcessLock(appendAppname(path)));
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("授权失败", e);
			}
			throw new OptSDKException("获取分布式锁失败", e);
		}
	}

	@Override
	public byte[] readBytes(String path)  {
		return readBytes(path, null);
	}

	@Override
	public byte[] readBytes(String path, ConfigWatcher configWatcher)  {
		if (!exists(path)) {
			throw new OptSDKException("节点[" + path + "]不存在");
		}

		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			return client.getNodeBytes(appendAppname(path), configWatcher);
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("无访问节点[" + path + "]权限",e);
			}
			throw new OptSDKException("读取节点[" + path + "]数据失败", e);
		}
	}

	@Override
	public void add(String path, String config, AddMode AddMode)  {
		byte[] bytes = null;
		if (!StringUtil.isBlank(config)) {
			try {
				bytes = config.getBytes(PaaSConstant.CHARSET_UTF8);
			} catch (UnsupportedEncodingException e) {
				throw new OptSDKException("字符串转byte数组错误", e);
			}
		}

		add(path, bytes, AddMode);

	}

	@Override
	public void add(String path, byte[] bytes)  {
		add(path, bytes, AddMode.PERSISTENT);
	}

	@Override
	public List<String> listSubPath(String path)  {
		return listSubPath(path, null);
	}

	@Override
	public List<String> listSubPath(String path, ConfigWatcher configWatcher)  {

		if (!validatePath(path)) {
			throw new OptSDKException("path[" + path + "]不合法,必须以'/'开头，且不能以'/'结尾");
		}
		ZKClient zkClient = null;
		try {
			zkClient = getZkClientFromPool();
			return zkClient.getChildren(appendAppname(path), configWatcher);
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("无访问节点[" + path + "]权限", e);
			}
			throw new OptSDKException("列出path[" + path + "]的子结点失败", e);
		}
	}

	@Override
	public void remove(String path)  {
		if (!validatePath(path))
			throw new OptSDKException("path[" + path + "]不合法,必须以'/'开头，且不能以'/'结尾");

		ZKClient zkClient = null;
		try {
			zkClient = getZkClientFromPool();
			zkClient.deleteNode(appendAppname(path));
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new OptSDKException("无访问节点[" + path + "]权限", e);
			}
			throw new OptSDKException("删除节点[" + path + "]失败", e);
		}

	}

	/**
	 * 验证路径是否合法
	 * 
	 * @param path
	 * @return
	 * @author gucl
	 */
	public boolean validatePath(String path) {
		if ((!path.startsWith("/")) && (path.endsWith("/"))) {
			return false;
		}
		return true;
	}


}
