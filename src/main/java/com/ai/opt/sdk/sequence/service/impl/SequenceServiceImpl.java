package com.ai.opt.sdk.sequence.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;


import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.sequence.dao.ISequenceDao;
import com.ai.opt.sdk.sequence.dao.impl.SequenceDaoImpl;
import com.ai.opt.sdk.sequence.datasource.SeqDataSourceLoaderFactory;
import com.ai.opt.sdk.sequence.model.Sequence;
import com.ai.opt.sdk.sequence.model.SequenceCache;
import com.ai.opt.sdk.sequence.service.ISequenceService;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;

public class SequenceServiceImpl implements ISequenceService {
    public static final Logger LOG = LoggerFactory.getLogger(SequenceServiceImpl.class);

    private final Lock lock = new ReentrantLock();

    private DataSource db;

    private boolean isInit = false;

    private static final Map<String, Sequence> SEQMAP = new ConcurrentHashMap<String, Sequence>();

    private ISequenceDao sequenceDao;

    private void init() {
        process();
    }

    private void process() {
        initDb();
        sequenceDao = new SequenceDaoImpl(db);
        initSequence(null);
    }

    private void initDb() {
        db = SeqDataSourceLoaderFactory.getSeqDsLoader().getDs();
        if (db == null) {
            throw new OptSDKException("SEQ datasource init error");
        }
    }

    private void initSequence(String sequName) {
        if (StringUtil.isBlank(sequName)) {
            List<Sequence> list = sequenceDao.queryAllSequence();
            if (CollectionUtil.isEmpty(list)) {
                LOG.warn("not yet create sequence!");
                return;
            }
            for (Sequence sequence : list) {
                SEQMAP.put(sequence.getSequenceName(), sequence);
            }
        } else {
            Sequence sequence = sequenceDao.querySequenceByName(sequName);
            if (sequence == null) {
                LOG.warn("sequence name is :" + sequName + "not exists!");
                return;
            }
            SEQMAP.put(sequName, sequence);
        }
    }

    private Sequence getSequence(String sequenceName) {
        Sequence sequence = SEQMAP.get(sequenceName);
        if (sequence == null) {
            initSequence(sequenceName);
        }
        sequence = SEQMAP.get(sequenceName);
        return sequence;
    }

    private Long nextValue(Sequence sequence) {
        LOG.debug("start to fetch nextValue... ");
        if (sequence == null) {
            throw new OptSDKException("sequence object can't is null");
        }
        String sequenceName = sequence.getSequenceName();
        LOG.debug("sequenceName=" + sequenceName);
        SequenceCache sequenceCache = sequence.getSequence();
        LOG.debug("sequenceCache=" + JSONObject.fromObject(sequenceCache).toString());
        if (sequenceCache == null) {
            LOG.debug("sequenceCache为空，准备获取新的sequenceCache");
            sequenceCache = sequenceDao.getSequenceCache(sequenceName);
        }
        if (sequenceCache == null) {
            return null;
        }
        sequence.setSequence(sequenceCache);
        LOG.debug("新获取的sequenceCache为：" + JSONObject.fromObject(sequenceCache).toString());
        long value = sequenceCache.nextValue();
        LOG.debug("从sequenceCache中获取seq值value：" + value);
        if (value == -1) {
            LOG.debug("value=-1，说明sequenceCache溢出");
            try {
                lock.lock();
                value = sequenceCache.nextValue();
                LOG.debug("再试一次从sequenceCache中获取seq值value：" + value);
                if (value == -1) {
                    sequenceCache = sequenceDao.getSequenceCache(sequenceName);
                    sequence.setSequence(sequenceCache);
                    LOG.debug("value仍然为-1，需需重新获取sequenceCache，重新获取的sequenceCache为："
                            + JSONObject.fromObject(sequenceCache).toString());
                }
            } finally {
                lock.unlock();
            }

            if (sequenceCache == null) {
                return null;
            }
            if (value == -1) {
                value = sequenceCache.nextValue();
            }
        }
        if (value == -1) {
            return null;
        }
        LOG.debug("end to fetch nextValue... 最终的value为：" + value);
        return value;
    }

    @Override
    public Long nextValue(String sequenceName) {
        try {
            lock.lock();
            if (!isInit) {
                init();
                isInit = true;
            }
        } finally {
            lock.unlock();
        }

        if (StringUtil.isBlank(sequenceName)) {
            throw new OptSDKException("the sequence name is blank!");
        }
        Sequence sequence = this.getSequence(sequenceName);
        if (sequence == null) {
            throw new OptSDKException("not exists sequence name is :" + sequenceName);
        }

        Long nextValue = this.nextValue(sequence);

        return nextValue;
    }

    @Override
    public void modifySequence(String sequenceName, long nextVal) {
        if (StringUtil.isBlank(sequenceName)) {
            throw new OptSDKException("the sequence name is blank!");
        }
        if (nextVal < 0) {
            throw new OptSDKException("the sequence value must more than zero!");
        }
        sequenceDao.modifySequence(sequenceName, nextVal);
    }

}
