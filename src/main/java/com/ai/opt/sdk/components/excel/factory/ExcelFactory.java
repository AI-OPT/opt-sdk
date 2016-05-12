package com.ai.opt.sdk.components.excel.factory;

import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.client.impl.HssfExcelHelper;
import com.ai.opt.sdk.components.excel.client.impl.JxlExcelHelper;
import com.ai.opt.sdk.components.excel.client.impl.XssfExcelHelper;

public class ExcelFactory {
	/**
	 * Excel2003操作工具类--JXL
	 * @return
	 * @author gucl
	 */
	public static AbstractExcelHelper getJxlExcelHelper(){
		return JxlExcelHelper.getInstance();
	}
	/**
	 * Excel2003操作工具类--POI
	 * @return
	 * @author gucl
	 */
	public static AbstractExcelHelper getHssfExcelHelper(){
		return HssfExcelHelper.getInstance();
	}
	/**
	 * Excel2007操作工具类--POI
	 * @return
	 * @author gucl
	 */
	public static AbstractExcelHelper getXssfExcelHelper(){
		return XssfExcelHelper.getInstance();
	}
}
