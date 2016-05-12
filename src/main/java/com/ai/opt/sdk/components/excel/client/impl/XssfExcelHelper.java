package com.ai.opt.sdk.components.excel.client.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.util.ExcelDateUtil;
import com.ai.opt.sdk.components.excel.util.ExcelStringUtil;
import com.ai.opt.sdk.components.excel.util.ReflectUtil;

/**
 * 基于POI实现的Excel 2007工具类
 * 
 * @author gucl
 * 
 */
public class XssfExcelHelper extends AbstractExcelHelper {

	private static XssfExcelHelper instance = null; // 单例对象

	/**
	 * 私有化构造方法
	 * 
	 */
	private XssfExcelHelper() {
		super();
	}

	
	/**
	 * 获取单例对象并进行初始化
	 * 
	 * @param file
	 *            文件对象
	 * @return 返回初始化后的单例对象
	 */
	public static XssfExcelHelper getInstance() {
		if (instance == null) {
			// 当单例对象为null时进入同步代码块
			synchronized (XssfExcelHelper.class) {
				// 再次判断单例对象是否为null，防止多线程访问时多次生成对象
				if (instance == null) {
					instance = new XssfExcelHelper();
				}
			}
		} 
		return instance;
	}

	@Override
	public <T> List<T> readExcel(File file,Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception {
		List<T> dataModels = new ArrayList<T>();
		// 获取excel工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = workbook.getSheetAt(sheetNo);
		int start = sheet.getFirstRowNum() + (hasTitle ? 1 : 0); // 如果有标题则从第二行开始
		for (int i = start; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			// 生成实例并通过反射调用setter方法
			T target = clazz.newInstance();
			for (int j = 0; j < fieldNames.length; j++) {
				String fieldName = fieldNames[j];
				if (fieldName == null || UID.equals(fieldName)) {
					continue; // 过滤serialVersionUID属性
				}
				// 获取excel单元格的内容
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				String content = getCellContent(cell);
				// 如果属性是日期类型则将内容转换成日期对象
				if (isDateType(clazz, fieldName)) {
					// 如果属性是日期类型则将内容转换成日期对象
					ReflectUtil.invokeSetter(target, fieldName,
							ExcelDateUtil.parse(content));
				}else if(isTimestampType(clazz, fieldName)){
					// 如果属性是日期类型则将内容转换成日期对象
					ReflectUtil.invokeSetter(target, fieldName,
							ExcelDateUtil.parseTimestamp(content));
				}else {
					Field field = clazz.getDeclaredField(fieldName);
					ReflectUtil.invokeSetter(target, fieldName,
							parseValueWithType(content, field.getType()));
				}
			}
			dataModels.add(target);
		}
		workbook.close();
		return dataModels;
	}

	@Override
	public <T> void writeExcel(File file,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles) throws Exception {
		XSSFWorkbook workbook = null;
		// 检测文件是否存在，如果存在则修改文件，否则创建文件
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
		} else {
			workbook = new XSSFWorkbook();
		}
		// 根据当前工作表数量创建相应编号的工作表
		if(ExcelStringUtil.isBlank(sheetName)){
			sheetName = ExcelDateUtil.format(new Date(), "yyyyMMddHHmmssSS");
		}
		XSSFSheet sheet = workbook.createSheet(sheetName);
		XSSFRow headRow = sheet.createRow(0);
		
		//标题单元格样式
		XSSFCellStyle headCellStyle = buildHeadCellStyle(workbook);
		//正文单元格样式
		XSSFCellStyle bodyCellStyle = buildBodyCellStyle(workbook);
		//正文数字单元格样式
		XSSFCellStyle bodyDigitalCellStyle = buildBodyDigitalCellStyle(workbook);
		
		
		// 添加表格标题
		for (int i = 0; i < titles.length; i++) {
			XSSFCell cell = headRow.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(titles[i]);
			//设置样式
			cell.setCellStyle(headCellStyle);
			// 设置单元格宽度(15个字节宽度)
			sheet.setDefaultColumnWidth(15);
			//sheet.setColumnWidth(i, titles[i].length() * 1000);
		}
		// 添加表格内容
		for (int i = 0; i < dataModels.size(); i++) {
			T target = dataModels.get(i);
			XSSFRow row = sheet.createRow(i + 1);
			// 遍历属性列表
			for (int j = 0; j < fieldNames.length; j++) {
				// 通过反射获取属性的值域
				String fieldName = fieldNames[j];
				if (fieldName == null || UID.equals(fieldName)) {
					continue; // 过滤serialVersionUID属性
				}
				Object result = ReflectUtil.invokeGetter(target, fieldName);
				XSSFCell cell = row.createCell(j);
				cell.setCellStyle(bodyCellStyle);
				//数值类型 居右
				if(isDigitalType(clazz, fieldName)){
					cell.setCellStyle(bodyDigitalCellStyle);
				}
				// 如果是日期类型则进行格式化处理
				if (isDateType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Date) result));
				}
				// 如果是日期类型则进行格式化处理
				else if (isTimestampType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Timestamp) result));
				}
				//其他类型  均作为字符串处理
				else{
					cell.setCellValue(ExcelStringUtil.toString(result));
				}
			}
		}
		// 将数据写到磁盘上
		FileOutputStream fos = new FileOutputStream(file);
		try {
			workbook.write(new FileOutputStream(file));
			workbook.close();
		} finally {
			if (fos != null) {
				fos.close(); // 不管是否有异常发生都关闭文件输出流
			}
		}
	}


	private XSSFCellStyle buildBodyDigitalCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle bodyDigitalCellStyle = workbook.createCellStyle();
		XSSFFont bodyDigitalFont = workbook.createFont();
		bodyDigitalFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		bodyDigitalFont.setFontHeightInPoints((short)10);
		bodyDigitalCellStyle.setFont(bodyDigitalFont);
		bodyDigitalCellStyle.setWrapText(true);// 设置自动换行
		//bodyDigitalCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		//bodyDigitalCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		bodyDigitalCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);  //底边框
		bodyDigitalCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);  //左边框
		bodyDigitalCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);  //右边框
		bodyDigitalCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);  //顶边框
		bodyDigitalCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); //居中
		return bodyDigitalCellStyle;
	}


	private XSSFCellStyle buildBodyCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle bodyCellStyle = workbook.createCellStyle();
		XSSFFont bodyFont = workbook.createFont();
		bodyFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		bodyFont.setFontHeightInPoints((short)10);
		bodyCellStyle.setFont(bodyFont);
		bodyCellStyle.setWrapText(true);// 设置自动换行
		//bodyCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		//bodyCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		bodyCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);  //底边框
		bodyCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);  //左边框
		bodyCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);  //右边框
		bodyCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);  //顶边框
		bodyCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); //居中
		return bodyCellStyle;
	}


	private XSSFCellStyle buildHeadCellStyle(XSSFWorkbook workbook) {
		//标题单元格样式
		XSSFCellStyle headCellStyle = workbook.createCellStyle();
		XSSFFont headFont = workbook.createFont();
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setFontHeightInPoints((short)10);
		headCellStyle.setFont(headFont);
		headCellStyle.setWrapText(true);// 设置自动换行
		headCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);  //底边框
		headCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);  //左边框
		headCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);  //右边框
		headCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);  //顶边框
		headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); //居中
		return headCellStyle;
	}

	@Override
	protected <T> Object parseValueWithType(String value, Class<?> type) {
		// 由于Excel2007的numeric类型只返回double型，所以对于类型为整型的属性，要提前对numeric字符串进行转换
		if (Byte.TYPE == type || Short.TYPE == type || Short.TYPE == type
				|| Long.TYPE == type) {
			value = String.valueOf((long) Double.parseDouble(value));
		}
		return super.parseValueWithType(value, type);
	}

	/**
	 * 获取单元格的内容
	 * 
	 * @param cell
	 *            单元格
	 * @return 返回单元格内容
	 */
	private String getCellContent(XSSFCell cell) {
		StringBuffer buffer = new StringBuffer();
		switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC : // 数字
				buffer.append(cell.getNumericCellValue());
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN : // 布尔
				buffer.append(cell.getBooleanCellValue());
				break;
			case XSSFCell.CELL_TYPE_FORMULA : // 公式
				buffer.append(cell.getCellFormula());
				break;
			case XSSFCell.CELL_TYPE_STRING : // 字符串
				buffer.append(cell.getStringCellValue());
				break;
			case XSSFCell.CELL_TYPE_BLANK : // 空值
			case XSSFCell.CELL_TYPE_ERROR : // 故障
			default :
				break;
		}
		return buffer.toString();
	}

	@Override
	public <T> void writeExcel(OutputStream os, String sheetName,Class<T> clazz, List<T> dataModels, String[] fieldNames,
			String[] titles) throws Exception {
		XSSFWorkbook workbook = null;
		// 检测文件是否存在，如果存在则修改文件，否则创建文件
		workbook = new XSSFWorkbook();
		// 根据当前工作表数量创建相应编号的工作表
		if(ExcelStringUtil.isBlank(sheetName)){
			sheetName = ExcelDateUtil.format(new Date(), "yyyyMMddHHmmssSS");
		}
		XSSFSheet sheet = workbook.createSheet(sheetName);
		XSSFRow headRow = sheet.createRow(0);
		
		//标题单元格样式
		XSSFCellStyle headCellStyle = buildHeadCellStyle(workbook);
		//正文单元格样式
		XSSFCellStyle bodyCellStyle = buildBodyCellStyle(workbook);
		//正文数字单元格样式
		XSSFCellStyle bodyDigitalCellStyle = buildBodyDigitalCellStyle(workbook);		
				
			
		// 添加表格标题
		for (int i = 0; i < titles.length; i++) {
			XSSFCell cell = headRow.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(titles[i]);
			//设置样式
			cell.setCellStyle(headCellStyle);
			// 设置单元格宽度(15个字节宽度)
			sheet.setDefaultColumnWidth(15);
			//sheet.setColumnWidth(i, titles[i].length() * 1000);
		}
		// 添加表格内容
		for (int i = 0; i < dataModels.size(); i++) {
			T target = dataModels.get(i);
			XSSFRow row = sheet.createRow(i + 1);
			// 遍历属性列表
			for (int j = 0; j < fieldNames.length; j++) {
				// 通过反射获取属性的值域
				String fieldName = fieldNames[j];
				if (fieldName == null || UID.equals(fieldName)) {
					continue; // 过滤serialVersionUID属性
				}
				Object result = ReflectUtil.invokeGetter(target, fieldName);
				XSSFCell cell = row.createCell(j);
				cell.setCellStyle(bodyCellStyle);
				//数值类型 居右
				if(isDigitalType(clazz, fieldName)){
					cell.setCellStyle(bodyDigitalCellStyle);
				}
				// 如果是日期类型则进行格式化处理
				if (isDateType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Date) result));
				}
				// 如果是日期类型则进行格式化处理
				else if (isTimestampType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Timestamp) result));
				}
				//其他类型  均作为字符串处理
				else{
					cell.setCellValue(ExcelStringUtil.toString(result));
				}
			}
		}
		// 将数据写到磁盘上
		try {
			workbook.write(os);
			workbook.close();
		} finally {
			if (os != null) {
				os.close(); // 不管是否有异常发生都关闭文件输出流
			}
		}
		
	}
}
