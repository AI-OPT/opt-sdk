package com.ai.opt.sdk.dubbo.extension;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.RPCSystemException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.alibaba.fastjson.JSON;

public class DubboRestExceptionMapper implements ExceptionMapper<Exception> {

    private static final String HTTP = "HTTP";

    @Override
    public Response toResponse(Exception ex) {
        DubboRestResponse error = null;
        if (ex instanceof RPCSystemException) {
            RPCSystemException e = (RPCSystemException) ex;
            error = new DubboRestResponse(e.getErrorCode(), e.getMessage());
            return Response.status(Response.Status.OK).entity(error).type("text/plain").build();
        } else if (ex instanceof BusinessException) {
            BusinessException e = (BusinessException) ex;
            error = new DubboRestResponse(e.getErrorCode(), e.getMessage());
            return Response.status(Response.Status.OK).entity(error).type("text/plain").build();
        } else if (ex instanceof SystemException) {
            SystemException e = (SystemException) ex;
            error = new DubboRestResponse(e.getErrorCode(), e.getMessage());
            return Response.status(Response.Status.OK).entity(error).type("text/plain").build();
        } else if (ex instanceof ForbiddenException) {
            error = new DubboRestResponse(HTTP + "." + Response.Status.FORBIDDEN.getStatusCode(),
                    ex.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(JSON.toJSONString(error))
                    .type("text/plain").build();
        } else if (ex instanceof BadRequestException) {
            error = new DubboRestResponse(HTTP + "." + Response.Status.BAD_REQUEST.getStatusCode(),
                    ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(JSON.toJSONString(error))
                    .type("text/plain").build();
        } else if (ex instanceof InternalServerErrorException) {
            error = new DubboRestResponse(HTTP + "."
                    + Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JSON.toJSONString(error)).type("text/plain").build();
        } else if (ex instanceof NotAuthorizedException) {
            error = new DubboRestResponse(
                    HTTP + "." + Response.Status.UNAUTHORIZED.getStatusCode(), ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(error).type("text/plain")
                    .build();
        } else if (ex instanceof NotAcceptableException) {
            error = new DubboRestResponse(HTTP + "."
                    + Response.Status.NOT_ACCEPTABLE.getStatusCode(), ex.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(JSON.toJSONString(error))
                    .type("text/plain").build();
        } else if (ex instanceof NotFoundException) {
            // 返回实体为JSON文本，考虑DUBBOX框架，如果是对象，则客户端获取不到
            error = new DubboRestResponse(HTTP + "." + Response.Status.NOT_FOUND.getStatusCode(),
                    ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(JSON.toJSONString(error))
                    .type("text/plain").build();
        } else if (ex instanceof NullPointerException) {
            error = new DubboRestResponse(ExceptCodeConstants.Special.SYSTEM_ERROR, "服务出现空指针异常");
            return Response.status(Response.Status.OK).entity(error).type("text/plain").build();
        } else {
            error = new DubboRestResponse(ExceptCodeConstants.Special.SYSTEM_ERROR,
                    "出现异常，请联系服务提供者处理。异常摘要:" + ex.getMessage());
            return Response.status(Response.Status.OK).entity(error).type("text/plain").build();
        }
    }
}
