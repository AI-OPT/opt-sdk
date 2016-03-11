package com.ai.opt.sdk.util;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.runner.base.exception.CallerException;

public final class DubboExceptAssembler {

    private DubboExceptAssembler() {

    }

    public static CallerException assemble(BusinessException ex) {
        return new CallerException(ex.getErrorCode(), ex.getErrorMessage(), ex);
    }

    public static CallerException assemble(SystemException ex) {
        return new CallerException(ex.getErrorCode(), ex.getErrorMessage(), ex);
    }

    public static CallerException assemble(Exception ex) {
        if (ex instanceof BusinessException) {
            return assemble((BusinessException) ex);
        } else if (ex instanceof SystemException) {
            return assemble((SystemException) ex);
        } else if (ex instanceof CallerException) {
            return (CallerException) ex;
        }
        return new CallerException(ExceptCodeConstants.Special.SYSTEM_ERROR, StringUtil.isBlank(ex
                .getMessage()) ? "系统异常，请联系管理员" : ex.getMessage(), ex);
    }

    public static CallerException assemble(Throwable ex) {
        if (ex instanceof BusinessException) {
            return assemble((BusinessException) ex);
        } else if (ex instanceof SystemException) {
            return assemble((SystemException) ex);
        } else if (ex instanceof CallerException) {
            return (CallerException) ex;
        }
        return new CallerException(ExceptCodeConstants.Special.SYSTEM_ERROR, StringUtil.isBlank(ex
                .getMessage()) ? "系统异常，请联系管理员" : ex.getMessage(), (Exception) ex);
    }

}
