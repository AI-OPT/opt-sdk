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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.util.ExcelDateUtil;
import com.ai.opt.sdk.components.excel.util.ExcelStringUtil;
import com.ai.opt.sdk.components.excel.util.ReflectUtil;

/**
 * 基于POI实现的Excel 2003工具类
 * 
 * @author gucl
 * 
 */
public class HssfExcelHelper extends AbstractExcelHelper {

	private static HssfExcelHelper instance = null; // 单例对象

	/**
	 * 私有化构造方法
	 * 
	 * @param file
	 *            文件对象
	 */
	private HssfExcelHelper() {
		super();
	}

	/**
	 * 获取单例对象并进行初始化
	 * 
	 * @return 返回初始化后的单例对象
	 */
	public static HssfExcelHelper getInstance() {
		if (instance == null) {
			// 当单例对象为null时进入同步代码块
			synchronized (HssfExcelHelper.class) {
				// 再次判断单例对象是否为null，防止多线程访问时多次生成对象
				if (instance == null) {
					instance = new HssfExcelHelper();
				}
			}
		} else {
			
		}
		return instance;
	}	

	@Override
	public <T> List<T> readExcel(File file,Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception {
		List<T> dataModels = new ArrayList<T>();
		// 获取excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = workbook.getSheetAt(sheetNo);
		int start = sheet.getFirstRowNum() + (hasTitle ? 1 : 0); // 如果有标题则从第二行开始
		for (int i = start; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
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
				HSSFCell cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				String content = cell.getStringCellValue();
				// 如果属性是日期类型则将内容转换成日期对象
				if (isDateType(clazz, fieldName)) {
					// 如果属性是日期类型则将内容转换成日期对象
					ReflectUtil.invokeSetter(target, fieldName,
							ExcelDateUtil.parse(content));
				}
				else if(isTimestampType(clazz, fieldName)){
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
		HSSFWorkbook workbook = null;
		// 检测文件是否存在，如果存在则修改文件，否则创建文件
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			workbook = new HSSFWorkbook(fis);
		} else {
			workbook = new HSSFWorkbook();
		}
		// 根据当前工作表数量创建相应编号的工作表
		if(ExcelStringUtil.isBlank(sheetName)){
			sheetName = ExcelDateUtil.format(new Date(), "yyyyMMddHHmmssSS");
		}
		HSSFSheet sheet = workbook.createSheet(sheetName);
		HSSFRow headRow = sheet.createRow(0);
		// 添加表格标题
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = headRow.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(titles[i]);
			// 设置字体加粗
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			cellStyle.setFont(font);
			// 设置自动换行
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			// 设置单元格宽度
			sheet.setColumnWidth(i, titles[i].length() * 1000);
		}
		// 添加表格内容
		for (int i = 0; i < dataModels.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			// 遍历属性列表
			for (int j = 0; j < fieldNames.length; j++) {
				// 通过反射获取属性的值域
				String fieldName = fieldNames[j];
				if (fieldName == null || UID.equals(fieldName)) {
					continue; // 过滤serialVersionUID属性
				}
				Object result = ReflectUtil.invokeGetter(dataModels.get(i),
						fieldName);
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(ExcelStringUtil.toString(result));
				// 如果是日期类型则进行格式化处理
				if (isDateType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Date) result));
				}
				// 如果是日期类型则进行格式化处理
				if (isTimestampType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Timestamp) result));
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

	@Override
	public <T> void writeExcel(OutputStream os, String sheetName,Class<T> clazz, List<T> dataModels, String[] fieldNames,
			String[] titles) throws Exception {
		HSSFWorkbook workbook = null;
		workbook = new HSSFWorkbook();
		// 根据当前工作表数量创建相应编号的工作表
		if(ExcelStringUtil.isBlank(sheetName)){
			sheetName = ExcelDateUtil.format(new Date(), "yyyyMMddHHmmssSS");
		}
		HSSFSheet sheet = workbook.createSheet(sheetName);
		HSSFRow headRow = sheet.createRow(0);
		// 添加表格标题
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = headRow.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(titles[i]);
			// 设置字体加粗
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			cellStyle.setFont(font);
			// 设置自动换行
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			// 设置单元格宽度
			sheet.setColumnWidth(i, titles[i].length() * 1000);
		}
		// 添加表格内容
		for (int i = 0; i < dataModels.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			// 遍历属性列表
			for (int j = 0; j < fieldNames.length; j++) {
				// 通过反射获取属性的值域
				String fieldName = fieldNames[j];
				if (fieldName == null || UID.equals(fieldName)) {
					continue; // 过滤serialVersionUID属性
				}
				Object result = ReflectUtil.invokeGetter(dataModels.get(i),
						fieldName);
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(ExcelStringUtil.toString(result));
				// 如果是日期类型则进行格式化处理
				if (isDateType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Date) result));
				}
				// 如果是日期类型则进行格式化处理
				if (isTimestampType(clazz, fieldName)) {
					cell.setCellValue(ExcelDateUtil.format((Timestamp) result));
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
