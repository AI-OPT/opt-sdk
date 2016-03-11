package com.ai.opt.sdk.constants;

/**
 * Title: MVNO-CRM <br>
 * Description:异常编码定义 <br>
 * Date: 2014年2月27日 <br>
 * Copyright (c) 2014 AILK <br>
 * 
 * @author zhangchao
 */
public final class ExceptCodeConstants {
	private ExceptCodeConstants(){}
    /**
     * Title: MVNO-CRM <br>
     * Description: 特定异常<br>
     * Date: 2014年2月27日 <br>
     * Copyright (c) 2014 AILK <br>
     * 
     * @author zhangchao
     */
    public static final class Special {
    	private Special(){}
        // 处理成功[业务处理成功]
        public static final String SUCCESS = "000000";

        // 系统级异常[其它系统级异常，未知异常]
        public static final String SYSTEM_ERROR = "999999";

        /**
         * 传入的{0}参数为空
         */
        public static final String PARAM_IS_NULL = "888888";

        // 未查询到记录[查询无记录]
        public static final String NO_RESULT = "000001";

        /**
         * 参数类型不正确{0}
         */
        public static final String PARAM_TYPE_NOT_RIGHT = "000002";

        // 未配置系统参数或未刷新缓存
        public static final String NO_DATA_OR_CACAE_ERROR = "000003";
    }     
    
}
