package com.ai.opt.sdk.dubbo.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

@Activate(group = { Constants.PROVIDER })
public class DubboRequestTrackFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(DubboRequestTrackFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) {
    	String protocol = invoker.getUrl().getProtocol();
        String reqSV = invoker.getInterface().getName();
        String reqMethod = invocation.getMethodName();
        Object[] requestParams = invocation.getArguments();
        List<Class> paramClazz=null; 
        if(requestParams!=null){
        	paramClazz=new ArrayList<Class>();
        	for(Object param: requestParams){
        		paramClazz.add(param.getClass());
        	}        	
        }
        Class returnClazz=null;
        try {
			Method method=invoker.getInterface().getMethod(reqMethod, paramClazz==null?null:paramClazz.toArray(new Class[paramClazz.size()]));
			returnClazz=method.getReturnType();
        } catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        // 交易序列
        String tradeSeq = UUIDUtil.genId32();
        // 打印请求参数明细
        if (CollectionUtil.isEmpty(requestParams)) {
            if (LOG.isInfoEnabled()) {
                LOG.info("TRADE_SEQ:{},请求接口:{},请求方法:{},请求参数:{}", tradeSeq, reqSV, reqMethod, "");
            }
        } else {
            if (LOG.isInfoEnabled()) {
                LOG.info("TRADE_SEQ:{},请求接口:{},请求方法:{},请求参数:{}", tradeSeq, reqSV, reqMethod,
                        JSON.toJSONString(requestParams));
            }
        }
        // 执行结果
        Result result = null;
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("TRADE_SEQ:{},执行调用服务{}类中的{}方法", tradeSeq, reqSV, reqMethod);
            }
            result = invoker.invoke(invocation);
            if (result.hasException()) {
                Throwable e = result.getException();
                if (LOG.isErrorEnabled()) {
                    LOG.error("TRADE_SEQ:{},调用服务{}类中的{}方法发生异常，原因:{}", tradeSeq, reqSV, reqMethod,
                            result.getException().getMessage(), result.getException());
                }
                
                //rest的情况下，不抛出异常
                if (null != protocol && protocol.equalsIgnoreCase("rest")){
                	
                	BaseResponse response = new BaseResponse();
                    response.setResponseHeader(new ResponseHeader(false, "999999", e.getMessage(),e.getStackTrace()));
                    RpcResult r = new RpcResult();
                    Gson gson=new Gson();
                    r.setValue(gson.fromJson(gson.toJson(response), returnClazz));
                    return r;                	
                }
                //dubbo情况下，抛出异常
                else{
                	if (e instanceof BusinessException) {
                        BaseResponse response = new BaseResponse();
                        response.setResponseHeader(new ResponseHeader(false, ((BusinessException) e)
                                .getErrorCode(), ((BusinessException) e).getErrorMessage()));
                        RpcResult r = new RpcResult();
                        r.setValue(response);
                        return r;
                    } else if (e instanceof SystemException) {
                        throw (SystemException) e;
                    } else {
                        throw new SystemException(e.getMessage(),e);
                    }
                }
                
                
            }
            if (LOG.isInfoEnabled()) {
                LOG.info("TRADE_SEQ:{},调用服务{}类中的{}方法的结果:{}", tradeSeq, reqSV, reqMethod,
                        JSON.toJSONString(result.getValue()));
            }
            return result;
        } catch (Throwable ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("TRADE_SEQ:{},执行{}类中的{}方法发生异常:{}", tradeSeq, reqSV, reqMethod, ex);
            }
            if (null != protocol && protocol.equalsIgnoreCase("rest")){
            	
            	BaseResponse response = new BaseResponse();
                response.setResponseHeader(new ResponseHeader(false, "999999", ex.getMessage(),ex.getStackTrace()));
                RpcResult r = new RpcResult();
                Gson gson=new Gson();
                r.setValue(gson.fromJson(gson.toJson(response), returnClazz));
                return r;                	
            }
            RpcResult r = new RpcResult();
         
            r.setException(ex);
            return r;
        }

    }

}
