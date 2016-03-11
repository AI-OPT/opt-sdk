package com.ai.opt.sdk.web.model;

import java.io.Serializable;
import java.util.List;

public class PagerResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Pager pager;

    private List<T> result;

    // 扩展数据对象
    private Object extended;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Object getExtended() {
        return extended;
    }

    public void setExtended(Object extended) {
        this.extended = extended;
    }

}
