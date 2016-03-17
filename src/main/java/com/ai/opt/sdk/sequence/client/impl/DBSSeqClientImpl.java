package com.ai.opt.sdk.sequence.client.impl;

import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.helper.OptConfHelper;
import com.ai.opt.sdk.model.PaasConfInfo;
import com.ai.opt.sdk.sequence.client.ISeqClient;
import com.ai.opt.sdk.tools.PaaSServiceTool;
import com.ai.paas.ipaas.util.StringUtil;

public class DBSSeqClientImpl implements ISeqClient {

//    private ISequenceClient sequenceService;

    public DBSSeqClientImpl() {
//        this.sequenceService = getClient();
    }

    @Override
    public Long nextValue(String sequenceName) {
        return null;
//        return sequenceService.nextValue(sequenceName);
    }

//    private ISequenceClient getClient() {
//        String dbsServiceId = PaaSServiceTool.getDBSId();
//        if (StringUtil.isBlank(dbsServiceId)) {
//            throw new RunnerSDKException("cant get dbs seq because dbs service id is null");
//        }
//        String dbsPassword = PaaSServiceTool.getDBSPwd(dbsServiceId);
//        if (StringUtil.isBlank(dbsPassword)) {
//            throw new RunnerSDKException("cant get dbs seq because dbs service password is null");
//        }
//        PaasAuthInfo authInfo = RunnerConfHelper.getInstance().getPaasAuthInfo();
//        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
//                authInfo.getUserName(), dbsPassword, dbsServiceId);
//        return SequenceFactory.getClient(authDescriptor);
//    }

}
