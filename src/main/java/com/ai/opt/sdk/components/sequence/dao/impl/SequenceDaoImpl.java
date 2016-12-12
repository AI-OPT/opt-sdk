package com.ai.opt.sdk.components.sequence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.sequence.dao.ISequenceDao;
import com.ai.opt.sdk.components.sequence.model.Sequence;
import com.ai.opt.sdk.components.sequence.model.SequenceCache;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.util.StringUtil;

import net.sf.json.JSONObject;

public class SequenceDaoImpl implements ISequenceDao {

    private static final Logger LOG = LoggerFactory.getLogger(SequenceDaoImpl.class);

    private static final String sqlUpdate = "update sys_sequences set LAST_NUMBER=? where SEQUENCE_NAME = ? and LAST_NUMBER=?";

    private static final String queryAllSequenceSql = "select SEQUENCE_NAME,LAST_NUMBER from sys_sequences";

    private static final String sqlSelect = "select SEQUENCE_NAME,LAST_NUMBER,JVM_STEP_BY,INCREMENT_BY from sys_sequences where SEQUENCE_NAME = ?";

    private DataSource db;

    public SequenceDaoImpl(DataSource db) {
        this.db = db;
    }

    @Override
    public List<Sequence> queryAllSequence() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Sequence> sequenceList = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(queryAllSequenceSql);
            rs = ps.executeQuery();
            if (rs != null) {
                String sequenceName = null;
                Long sequenceValue = null;
                Sequence sequence = null;
                sequenceList = new ArrayList<Sequence>();
                while (rs.next()) {
                    sequenceName = rs.getString("SEQUENCE_NAME");
                    sequenceValue = rs.getLong("LAST_NUMBER");
                    sequence = new Sequence();
                    sequence.setValue(sequenceValue);
                    sequence.setSequenceName(sequenceName);
                    sequenceList.add(sequence);
                }
            }
        } catch (SQLException e) {
            LOG.error("query all sequence  failed", e);
            throw new SDKException("query all sequence  failed", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error("ResultSet close error", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOG.error("Statement close error", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("connection close error", e);
                }
            }
        }
        return sequenceList;
    }

    @Override
    public SequenceCache getSequenceCache(String sequenceName) {
        LOG.debug("从数据库中获取sequenceCache");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            for(int i=0;i<10;i++){
            	ps = conn.prepareStatement(sqlSelect);
            	ps.setString(1, sequenceName);
            	rs = ps.executeQuery();
            	if (rs != null && rs.next()) {
            		long currVal = rs.getLong("LAST_NUMBER");
            		long jvmStepBy = rs.getLong("JVM_STEP_BY");
            		rs.close();
            		ps.close();
            		ps = conn.prepareStatement(sqlUpdate);
            		ps.setLong(1, currVal + jvmStepBy);
            		ps.setString(2, sequenceName);
            		ps.setLong(3, currVal);
            		int ret = ps.executeUpdate();
            		conn.commit();
            		if (ret == 1) {
            			SequenceCache cache = new SequenceCache(currVal + 1, currVal + jvmStepBy);
            			
            			LOG.debug("get sequence cache:" + JSONObject.fromObject(cache).toString());
            			return cache;
            		}
            	}
            	
            }
            
        } catch (SQLException e) {
            LOG.error("get sequence cache failed", e);
            throw new SDKException("get sequence cache failed", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error("ResultSet close error", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOG.error("Statement close error", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("connection close error", e);
                }
            }
        }
        return null;
    }

    @Override
    public Sequence querySequenceByName(String sequenceName) {
        if (StringUtil.isBlank(sequenceName)) {
            return null;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Sequence sequence = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sqlSelect);
            ps.setString(1, sequenceName);
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                String name = rs.getString("SEQUENCE_NAME");
                long currVal = rs.getLong("LAST_NUMBER");
                sequence = new Sequence();
                sequence.setSequenceName(name);
                sequence.setValue(currVal);
            }
        } catch (SQLException e) {
            LOG.error("get sequence cache failed", e);
            throw new SDKException("get sequence cache failed", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error("ResultSet close error", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOG.error("Statement close error", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("connection close error", e);
                }
            }
        }
        return sequence;
    }

    @Override
    public void modifySequence(String sequenceName, long nextval) {

        Sequence sequence = this.querySequenceByName(sequenceName);
        if (sequence != null) {
            throw new SDKException("create sequence,the sequence name is " + sequenceName
                    + "have exists!");
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sqlUpdate);
            ps.setString(1, sequenceName);
            ps.setLong(2, nextval);
            int count = ps.executeUpdate();
            conn.commit();
            if (count != 1) {
                throw new SDKException("modify sequence failed, update data more than one!");
            }
        } catch (Exception e) {
            LOG.error("modify sequence failed,the detail message is ：" + e.getMessage());
            throw new SDKException("modify sequence failed!", e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOG.error("Statement close error", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("connection close error", e);
                }
            }
        }

    }

    public DataSource getDb() {
        return db;
    }

    public void setDb(DataSource db) {
        this.db = db;
    }

}
