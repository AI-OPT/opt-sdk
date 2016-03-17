package com.ai.opt.sdk.util;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.RPCSystemException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;

public final class DubboExceptAssembler {

    private DubboExceptAssembler() {

    }

    public static RPCSystemException assemble(BusinessException ex) {
        return new RPCSystemException(ex.getErrorCode(), ex.getErrorMessage(), ex);
    }

    public static RPCSystemException assemble(SystemException ex) {
        return new RPCSystemException(ex.getErrorCode(), ex.getErrorMessage(), ex);
    }

    public static RPCSystemException assemble(Exception ex) {
        if (ex instanceof BusinessException) {
            return assemble((BusinessException) ex);
        } else if (ex instanceof SystemException) {
            return assemble((SystemException) ex);
        } else if (ex instanceof RPCSystemException) {
            return (RPCSystemException) ex;
        }
        return new RPCSystemException(ExceptCodeConstants.Special.SYSTEM_ERROR, StringUtil.isBlank(ex
                .getMessage()) ? "系统异常，请联系管理员" : ex.getMessage(), ex);
    }

    public static RPCSystemException assemble(Throwable ex) {
        if (ex instanceof BusinessException) {
            return assemble((BusinessException) ex);
        } else if (ex instanceof SystemException) {
            return assemble((SystemException) ex);
        } else if (ex instanceof RPCSystemException) {
            return (RPCSystemException) ex;
        }
        return new RPCSystemException(ExceptCodeConstants.Special.SYSTEM_ERROR, StringUtil.isBlank(ex
                .getMessage()) ? "系统异常，请联系管理员" : ex.getMessage(), (Exception) ex);
    }

}
