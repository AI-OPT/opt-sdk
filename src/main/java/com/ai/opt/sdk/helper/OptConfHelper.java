package com.ai.opt.sdk.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.model.PaasAuthInfo;
import com.ai.opt.sdk.util.StringUtil;

public final class OptConfHelper {

    private static OptConfHelper instance = null;

    private static Properties prop = new Properties();

    private OptConfHelper() {
        // 私有构造函数，不运行此类被外部实例化
    }

    public static OptConfHelper getInstance() {
        if (instance == null) {
            loadProp();
            instance = new OptConfHelper();
        }
        return instance;
    }

    private static void loadProp() {
        InputStream is = OptConfHelper.class.getClassLoader().getResourceAsStream(
                SDKConstants.PAAS_CONFIG_FILE);
        try {
            prop.load(is);
        } catch (IOException e) {
            throw new OptSDKException("loading paas config file failed", e);
        }
    }

    public PaasAuthInfo getPaasAuthInfo() {
        String ccsZkAddress = prop.getProperty("ccs.zk_address");
        String ccsUserName = prop.getProperty("ccs.userName");
        String ccsPassword = prop.getProperty("ccs.password");

        String mcsMaxtotal = prop.getProperty("mcs.maxtotal");
        String mcsMaxIdle = prop.getProperty("mcs.maxIdle");
        String mcsMinIdle = prop.getProperty("mcs.minIdle");
        String mcsTestOnBorrow = prop.getProperty("mcs.testOnBorrow");

        if (StringUtil.isBlank(ccsZkAddress)) {
            throw new OptSDKException("paas ccs zk_address is null");
        }
        if (StringUtil.isBlank(ccsUserName)) {
            throw new OptSDKException("paas ccs userName is null");
        }
        if (StringUtil.isBlank(ccsPassword)) {
            throw new OptSDKException("paas ccs password is null");
        }
        if (StringUtil.isBlank(mcsMaxtotal)) {
            throw new OptSDKException("paas mcs maxtotal is null");
        }
        if (StringUtil.isBlank(mcsMaxIdle)) {
            throw new OptSDKException("paas mcs maxIdle is null");
        }
        if (StringUtil.isBlank(mcsMinIdle)) {
            throw new OptSDKException("paas mcs minIdle is null");
        }
        if (StringUtil.isBlank(mcsTestOnBorrow)) {
            throw new OptSDKException("paas mcs testOnBorrow is null");
        }
        
        PaasAuthInfo paasAuthInfo = new PaasAuthInfo();
        paasAuthInfo.setCcsZkAddress(ccsZkAddress);
        paasAuthInfo.setCcsUserName(ccsUserName);
        paasAuthInfo.setCcsPassword(ccsPassword);

        paasAuthInfo.setMcsMaxIdle((mcsMaxIdle));
        paasAuthInfo.setMcsMaxtotal((mcsMaxtotal));
        paasAuthInfo.setMcsMinIdle((mcsMinIdle));
        paasAuthInfo.setMcsTestOnBorrow(mcsTestOnBorrow);

        return paasAuthInfo;
    }

    public String getCacheType() {
        String type = prop.getProperty("opt.cache.type");
        if (StringUtil.isBlank(type)) {
            throw new OptSDKException("cache type is null");
        }
        return type;
    }

    public String getConfigCenterType() {
        String type = prop.getProperty("opt.configcenter.type");
        if (StringUtil.isBlank(type)) {
            throw new OptSDKException("config center type is null");
        }
        return type;
    }

    public String getSequenceType() {
        String type = prop.getProperty("opt.sequence.type");
        if (StringUtil.isBlank(type)) {
            throw new OptSDKException("sequence type is null");
        }
        return type;
    }

    public String getDocStorageType() {
        String type = prop.getProperty("opt.doc.storage.type");
        if (StringUtil.isBlank(type)) {
            throw new OptSDKException("doc storage type is null");
        }
        return type;
    }

    public String getOwnerZKHost() {
        String host = prop.getProperty("opt.owner.zkHost");
        if (StringUtil.isBlank(host)) {
            throw new OptSDKException("opt.owner.zkHost is null");
        }
        return host;
    }

    public String getOwnerZKAuthSchema() {
        String zkAuthSchema = prop.getProperty("opt.owner.zkAuthSchema");
        if (StringUtil.isBlank(zkAuthSchema)) {
            throw new OptSDKException("opt.owner.zkAuthSchema is null");
        }
        return zkAuthSchema;
    }

    public String getOwnerZKUser() {
        String zkUser = prop.getProperty("opt.owner.zkUser");
        if (StringUtil.isBlank(zkUser)) {
            throw new OptSDKException("opt.owner.zkUser is null");
        }
        return zkUser;
    }

    public String getOwnerZKPassword() {
        String zkPassword = prop.getProperty("opt.owner.zkPassword");
        if (StringUtil.isBlank(zkPassword)) {
            throw new OptSDKException("opt.owner.zkPassword is null");
        }
        return zkPassword;
    }

    public int getOwnerZKTimeout() {
        String zkTimeout = prop.getProperty("opt.owner.zkTimeout");
        if (StringUtil.isBlank(zkTimeout)) {
            throw new OptSDKException("opt.owner.zkTimeout is null");
        }
        return Integer.parseInt(zkTimeout);
    }

    public String getPropValue(String key) {
        if (StringUtil.isBlank(key)) {
            throw new OptSDKException("cannt get value because key is null");
        }
        return prop.containsKey(key) ? prop.getProperty(key) : null;
    }

    public Properties assembleCcsProperties(PaasAuthInfo authInfo) {
        Properties ccsProperties = new Properties();
        ccsProperties.put("ccs.zk_address", authInfo.getCcsZkAddress());
        ccsProperties.put("ccs.userName", authInfo.getCcsUserName());
        ccsProperties.put("ccs.password", authInfo.getCcsPassword());
        return ccsProperties;
    }

    public Properties assembleMcsProperties(PaasAuthInfo authInfo, String mcsHost,
            String mcsPassword) {
        Properties mcsProperties = new Properties();
        mcsProperties.put("mcs.maxtotal", authInfo.getMcsMaxtotal());
        mcsProperties.put("mcs.maxIdle", authInfo.getMcsMaxIdle());
        mcsProperties.put("mcs.minIdle", authInfo.getMcsMinIdle());
        mcsProperties.put("mcs.testOnBorrow", authInfo.getMcsTestOnBorrow());
        mcsProperties.put("mcs.host", mcsHost);
        mcsProperties.put("mcs.password", mcsPassword);
        return mcsProperties;
    }

}
