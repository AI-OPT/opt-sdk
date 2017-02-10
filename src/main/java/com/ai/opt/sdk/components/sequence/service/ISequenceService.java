package com.ai.opt.sdk.components.sequence.service;

/**
 * sequence服务
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public interface ISequenceService {

	/**
	 * nextValue
	 * @param sequenceName
	 * @return
	 * @author
	 */
    Long nextValue(String sequenceName);

    /**
     * 修改sequence
     * @param sequenceName
     * @param nextVal
     * @author
     */
    void modifySequence(String sequenceName, long nextVal);

}
