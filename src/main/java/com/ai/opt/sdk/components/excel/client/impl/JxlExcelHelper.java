package com.ai.opt.sdk.components.excel.client.impl;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.util.ExcelDateUtil;
import com.ai.opt.sdk.components.excel.util.ExcelStringUtil;
import com.ai.opt.sdk.components.excel.util.ReflectUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 基于JXL实现的Excel 2003工具类
 * 
 * @author gucl
 * 
 */
public class JxlExcelHelper extends AbstractExcelHelper {

	private static JxlExcelHelper instance = null; // 文件单例对象


	/**
	 * 私有化构造方法
	 * 
	 * @param file
	 *            文件对象
	 */
	private JxlExcelHelper() {
		super();
	}
	

	/**
	 * 获取单例对象并进行初始化
	 * 
	 * @return 返回初始化后的单例对象
	 */
	public static JxlExcelHelper getInstance() {
		if (instance == null) {
			// 当单例对象为null时进入同步代码块
			synchronized (JxlExcelHelper.class) {
				// 再次判断单例对象是否为null，防止多线程访问时多次生成对象
				if (instance == null) {
					instance = new JxlExcelHelper();
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
		Workbook workbook = Workbook.getWorkbook(file);
		try {
			Sheet sheet = workbook.getSheet(sheetNo);
			int start = hasTitle ? 1 : 0; // 如果有标题则从第二行开始
			for (int i = start; i < sheet.getRows(); i++) {
				// 生成实例并通过反射调用setter方法
				T target = clazz.newInstance();
				for (int j = 0; j < fieldNames.length; j++) {
					String fieldName = fieldNames[j];
					if (fieldName == null || UID.equals(fieldName)) {
						continue; // 过滤serialVersionUID属性
					}
					// 获取excel单元格的内容
					Cell cell = sheet.getCell(j, i);
					if (cell == null) {
						continue;
					}
					String content = cell.getContents();
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
					}
					else {
						Field field = clazz.getDeclaredField(fieldName);
						ReflectUtil.invokeSetter(target, fieldName,
								parseValueWithType(content, field.getType()));
					}
				}
				dataModels.add(target);
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return dataModels;
	}

	@Override
	public <T> void writeExcel(File file,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles) throws Exception {
		WritableWorkbook workbook = null;
		try {
			// 检测文件是否存在，如果存在则修改文件，否则创建文件
			if (file.exists()) {
				Workbook book = Workbook.getWorkbook(file);
				workbook = Workbook.createWorkbook(file, book);
			} else {
				workbook = Workbook.createWorkbook(file);
			}
			// 根据当前工作表数量创建相应编号的工作表
			int sheetNo = workbook.getNumberOfSheets() + 1;
			if(ExcelStringUtil.isBlank(sheetName)){
				sheetName = ExcelDateUtil.format(new Date(), "yyyyMMddHHmmssSS");
			}
			WritableSheet sheet = workbook.createSheet(sheetName, sheetNo);
			
			//标题样式
			WritableCellFormat headFormat = buildHeadCellFormat();
			//正文样式
			WritableCellFormat bodyFormat = buildBodyCellFormat();
			//正文数字样式
			WritableCellFormat bodyDigitalFormat = buildBodyDigitalFormat();
			
			// 添加表格标题
			for (int i = 0; i < titles.length; i++) {
				// 设置字体加粗
				WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
						WritableFont.BOLD);
				WritableCellFormat format = new WritableCellFormat(font);
				// 设置自动换行
				format.setWrap(true);
				Label label = new Label(i, 0, titles[i], headFormat);
				sheet.addCell(label);
				// 设置单元格宽度
				sheet.setColumnView(i, titles[i].length() + 15);
				
				/*CellView cellView = new CellView();  
			    cellView.setAutosize(true); //设置自动大小
			    cellView.setSize(18);
			    sheet.setColumnView(i, cellView);*/
			}
			// 添加表格内容
			for (int i = 0; i < dataModels.size(); i++) {
				// 遍历属性列表
				for (int j = 0; j < fieldNames.length; j++) {
					T target = dataModels.get(i);
					// 通过反射获取属性的值域
					String fieldName = fieldNames[j];
					if (fieldName == null || UID.equals(fieldName)) {
						continue; // 过滤serialVersionUID属性
					}
					Object result = ReflectUtil.invokeGetter(target, fieldName);
					Label label =null;
					if(isDigitalType(clazz, fieldName)){
						label=new Label(j, i + 1,
								ExcelStringUtil.toString(result),bodyDigitalFormat);
					}
					else{
						label = new Label(j, i + 1,
								ExcelStringUtil.toString(result),bodyFormat);
					}
					
					// 如果是日期类型则进行格式化处理
					if (isDateType(clazz, fieldName)) {
						label.setString(ExcelDateUtil.format((Date) result));
					}
					// 如果是日期类型则进行格式化处理
					if (isTimestampType(clazz, fieldName)) {
						label.setString(ExcelDateUtil.format((Timestamp) result));
					}
					sheet.addCell(label);
				}
			}
			
		} finally {
			if (workbook != null) {
				workbook.write();
				workbook.close();
			}
		}
	}


	private WritableCellFormat buildBodyDigitalFormat() throws WriteException {
		//正文数字字体
		WritableFont bodyDigitalwfont =  new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);  
		//正文数字样式
		WritableCellFormat bodyDigitalFormat = new WritableCellFormat(bodyDigitalwfont);
		bodyDigitalFormat.setAlignment(Alignment.RIGHT);
		bodyDigitalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		bodyDigitalFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.DARK_BLUE);
		bodyDigitalFormat.setBackground(Colour.WHITE);
		return bodyDigitalFormat;
	}


	private WritableCellFormat buildBodyCellFormat() throws WriteException {
		//正文字体
		WritableFont bodywfont =  new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);  
		//正文样式
		WritableCellFormat bodyFormat = new WritableCellFormat(bodywfont);
		bodyFormat.setAlignment(Alignment.CENTRE);
		bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.DARK_BLUE);
		bodyFormat.setBackground(Colour.WHITE);
		return bodyFormat;
	}


	private WritableCellFormat buildHeadCellFormat() throws WriteException {
		//标题字体
		WritableFont headwfont =  new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);  
		//标题样式
		WritableCellFormat headFormat = new WritableCellFormat(headwfont);
		headFormat.setAlignment(Alignment.CENTRE);
		headFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		headFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		headFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.DARK_BLUE);
		headFormat.setBackground(Colour.ICE_BLUE);
		return headFormat;
	}


	@Override
	public <T> void writeExcel(OutputStream os,String sheetName, Class<T> clazz, List<T> dataModels, String[] fieldNames,
			String[] titles) throws Exception {
		WritableWorkbook workbook = null;
		try {
			// 检测文件是否存在，如果存在则修改文件，否则创建文件
			workbook = Workbook.createWorkbook(os);
			// 根据当前工作表数量创建相应编号的工作表
			int sheetNo = workbook.getNumberOfSheets() + 1;
			if(ExcelStringUtil.isBlank(sheetName)){
				sheetName = ExcelDateUtil.format(new Date(), "yyyyMMddHHmmssSS");
			}
			WritableSheet sheet = workbook.createSheet(sheetName, sheetNo);
			//sheet.getSettings().setShowGridLines(true);
			
			//标题样式
			WritableCellFormat headFormat = buildHeadCellFormat();
			//正文样式
			WritableCellFormat bodyFormat = buildBodyCellFormat();
			//正文数字样式
			WritableCellFormat bodyDigitalFormat = buildBodyDigitalFormat();
			
			// 添加表格标题
			for (int i = 0; i < titles.length; i++) {
				// 设置字体加粗
				WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
						WritableFont.BOLD);
				WritableCellFormat format = new WritableCellFormat(font);
				// 设置自动换行
				format.setWrap(true);
				Label label = new Label(i, 0, titles[i], headFormat);
				sheet.addCell(label);
				// 设置单元格宽度
				sheet.setColumnView(i, titles[i].length() + 15);
				
				/*CellView cellView = new CellView();  
			    cellView.setAutosize(true); //设置自动大小
			    cellView.setSize(18);
			    sheet.setColumnView(i, cellView);*/
			}
			// 添加表格内容
			for (int i = 0; i < dataModels.size(); i++) {
				// 遍历属性列表
				for (int j = 0; j < fieldNames.length; j++) {
					T target = dataModels.get(i);
					// 通过反射获取属性的值域
					String fieldName = fieldNames[j];
					if (fieldName == null || UID.equals(fieldName)) {
						continue; // 过滤serialVersionUID属性
					}
					Object result = ReflectUtil.invokeGetter(target, fieldName);
					Label label =null;
					if(isDigitalType(clazz, fieldName)){
						label=new Label(j, i + 1,
								ExcelStringUtil.toString(result),bodyDigitalFormat);
					}
					else{
						label = new Label(j, i + 1,
								ExcelStringUtil.toString(result),bodyFormat);
					}
					
					// 如果是日期类型则进行格式化处理
					if (isDateType(clazz, fieldName)) {
						label.setString(ExcelDateUtil.format((Date) result));
					}
					// 如果是日期类型则进行格式化处理
					if (isTimestampType(clazz, fieldName)) {
						label.setString(ExcelDateUtil.format((Timestamp) result));
					}
					sheet.addCell(label);
				}
			}
		} finally {
			if (workbook != null) {
				workbook.write();
				workbook.close();
				//关闭输出流
				if(os!=null){
					os.flush();
					os.close();
				}
			}
		}
		
	}


}
