package com.ai.opt.sdk.components.excel.factory;

import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.client.impl.JxlExcelHelper;

public class ExcelFactory {
	public static AbstractExcelHelper getJxlExcelHelper(){
		return JxlExcelHelper.getInstance();
	}
}
