package com.ai.opt.sdk.components.sequence.dao;

import java.util.List;

import com.ai.opt.sdk.components.sequence.model.Sequence;
import com.ai.opt.sdk.components.sequence.model.SequenceCache;

/**
 * ISequenceDao
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public interface ISequenceDao {

	/**
	 * 获取sequence缓存
	 * @param sequenceName
	 * @return
	 * @author
	 */
    SequenceCache getSequenceCache(String sequenceName);

    /**
     * 获取所有的sequence
     * @return
     * @author
     */
    List<Sequence> queryAllSequence();

    /**
     * 通过名字获取sequence
     * @param sequenceName
     * @return
     * @author
     */
    Sequence querySequenceByName(String sequenceName);

    /**
     * 修改sequence
     * @param sequenceName
     * @param nextVal
     * @author
     */
    void modifySequence(String sequenceName, long nextVal);

}
