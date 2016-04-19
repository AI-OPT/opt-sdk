package com.ai.opt.sdk.excel.factory;

import com.ai.opt.sdk.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.excel.client.impl.JxlExcelHelper;

public class ExcelFactory {
	public static AbstractExcelHelper getJxlExcelHelper(){
		return JxlExcelHelper.getInstance();
	}
}
