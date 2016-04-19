package com.ai.opt.sdk.excel.client;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Excel工具抽象类
 * 
 * @author gucl
 * @param <T> 操作数据类型
 */
public abstract class AbstractExcelHelper {
	/**
	 * 对象序列化版本号名称
	 */
	public static final String UID = "serialVersionUID";

	/**
	 * 将指定excel文件中的数据转换成数据列表
	 * @param file 目标excel文件
	 * @param clazz 数据类型
	 * @param sheetNo 工作表编号
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public <T> List<T> readExcel(File file,Class<T> clazz, int sheetNo, boolean hasTitle)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		return readExcel(file,clazz, fieldNames, sheetNo, hasTitle);
	}

	/**
	 * 将指定excel文件中的数据转换成数据列表
	 * @param file 目标excel文件
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public <T> List<T> readExcel(File file,Class<T> clazz, String[] fieldNames,
			boolean hasTitle) throws Exception {
		return readExcel(file,clazz, fieldNames, 0, hasTitle);
	}

	/**
	 * 抽象方法：将指定excel文件中的数据转换成数据列表，由子类实现
	 * @param file 目标excel文件
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param sheetNo 工作表编号
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public abstract <T> List<T> readExcel(File file,Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception;
	
	/**
	 * 将指定excel文件中的数据转换成数据列表
	 * @param file 目标excel文件
	 * @param clazz 数据类型
	 * @param sheetNo 工作表编号
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public <T> List<T> readExcel(String filepath,Class<T> clazz, int sheetNo, boolean hasTitle)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		return readExcel(new File(filepath),clazz, fieldNames, sheetNo, hasTitle);
	}

	/**
	 * 将指定excel文件中的数据转换成数据列表
	 * @param file 目标excel文件
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public <T> List<T> readExcel(String filepath,Class<T> clazz, String[] fieldNames,
			boolean hasTitle) throws Exception {
		return readExcel(new File(filepath),clazz, fieldNames, 0, hasTitle);
	}

	/**
	 * 将指定excel文件中的数据转换成数据列表
	 * @param file 目标excel文件
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param sheetNo 工作表编号
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public <T> List<T> readExcel(String filepath,Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception{
		return readExcel(new File(filepath),clazz, fieldNames, sheetNo, hasTitle);
	}

	/**
	 * 写入数据到指定excel文件中
	 * @param file 目标excel文件
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @throws Exception
	 */
	public <T> void writeExcel(File file,String sheetName,Class<T> clazz, List<T> dataModels)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		writeExcel(file,sheetName,clazz, dataModels, fieldNames, fieldNames);
	}

	/**
	 * 写入数据到指定excel文件中
	 * @param file 目标excel文件
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @throws Exception
	 */
	public <T> void writeExcel(File file,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames) throws Exception {
		writeExcel(file,sheetName,clazz, dataModels, fieldNames, fieldNames);
	}

	/**
	 * 抽象方法：写入数据到指定excel文件中，由子类实现
	 * @param file 目标excel文件
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param titles 标题列表
	 * @throws Exception
	 */
	public abstract <T> void writeExcel(File file,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles) throws Exception;
	
	/**
	 * 写入数据到指定excel文件中
	 * @param filepath 目标excel文件路径
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @throws Exception
	 */
	public <T> void writeExcel(String filepath,String sheetName,Class<T> clazz, List<T> dataModels)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		writeExcel(new File(filepath),sheetName,clazz, dataModels, fieldNames, fieldNames);
	}

	/**
	 * 写入数据到指定excel文件中
	 * @param filepath 目标excel文件路径
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @throws Exception
	 */
	public <T> void writeExcel(String filepath,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames) throws Exception {
		writeExcel(new File(filepath),sheetName,clazz, dataModels, fieldNames, fieldNames);
	}

	/**
	 * 抽象方法：写入数据到指定excel文件中，由子类实现
	 * @param filepath 目标excel文件路径
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param titles 标题列表
	 * @throws Exception
	 */
	public <T> void writeExcel(String filepath,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles) throws Exception{
		writeExcel(new File(filepath),sheetName,clazz, dataModels,fieldNames,titles);
			
	}
	
	
	/**
	 * 写入数据到指定excel输出流中
	 * @param file 目标excel文件
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @throws Exception
	 */
	public <T> void writeExcel(OutputStream os,String sheetName,Class<T> clazz, List<T> dataModels)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		writeExcel(os,sheetName,clazz, dataModels, fieldNames, fieldNames);
	}

	/**
	 * 写入数据到指定excel输出流中
	 * @param file 目标excel文件
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @throws Exception
	 */
	public <T> void writeExcel(OutputStream os,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames) throws Exception {
		writeExcel(os,sheetName,clazz, dataModels, fieldNames, fieldNames);
	}

	/**
	 * 抽象方法：写入数据到指定excel输出流中，由子类实现
	 * @param file 目标excel文件
	 * @param sheetName excel sheet页的名称，为空时，默认取当前时间戳yyyyMMddHHmmssSS
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param titles 标题列表
	 * @throws Exception
	 */
	public abstract <T> void writeExcel(OutputStream os,String sheetName,Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles) throws Exception;
	
	/**
	 * 判断属性是否为日期类型
	 * @param clazz 数据类型
	 * @param fieldName 属性名
	 * @return 如果为日期类型返回true，否则返回false
	 */
	protected <T> boolean isDateType(Class<T> clazz, String fieldName) {
		boolean flag = false;
		try {
			Field field = clazz.getDeclaredField(fieldName);
			Object typeObj = field.getType().newInstance();
			flag = typeObj instanceof Date;
		} catch (Exception e) {
			// 把异常吞掉直接返回false
		}
		return flag;
	}
	/**
	 * 判断属性是否为数字类型（int,long,float,double等）
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @author gucl
	 * @ApiDocMethod
	 * @ApiCode
	 */
	protected <T> boolean isDigitalType(Class<T> clazz, String fieldName) {
		boolean flag = false;
		try {
			Field field = clazz.getDeclaredField(fieldName);
			String fieldType=field.getType().toString();
			flag = fieldType.equals("short")||fieldType.equals("int") 
					|| fieldType.equals("long")||fieldType.equals("float")
					||fieldType.equals("double");
			Object typeObj = field.getType().newInstance();
			flag = typeObj instanceof Integer || typeObj instanceof Long 
					|| typeObj instanceof Float || typeObj instanceof Double 
					|| typeObj instanceof BigDecimal || typeObj instanceof BigInteger;
		} catch (Exception e) {
			// 把异常吞掉直接返回false
		}
		return flag;
	}

	/**
	 * 根据类型将指定参数转换成对应的类型
	 * @param value 指定参数
	 * @param type 指定类型
	 * @return 返回类型转换后的对象
	 */
	protected <T> Object parseValueWithType(String value, Class<?> type) {
		Object result = null;
		try { // 根据属性的类型将内容转换成对应的类型
			if (Boolean.TYPE == type) {
				result = Boolean.parseBoolean(value);
			} else if (Byte.TYPE == type) {
				result = Byte.parseByte(value);
			} else if (Short.TYPE == type) {
				result = Short.parseShort(value);
			} else if (Integer.TYPE == type) {
				result = Integer.parseInt(value);
			} else if (Long.TYPE == type) {
				result = Long.parseLong(value);
			} else if (Float.TYPE == type) {
				result = Float.parseFloat(value);
			} else if (Double.TYPE == type) {
				result = Double.parseDouble(value);
			} else {
				result = (Object) value;
			}
		} catch (Exception e) {
			// 把异常吞掉直接返回null
		}
		return result;
	}
/*
	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee(1000, "Jones", 40, "Manager", 2975));
		employees.add(new Employee(1001, "Blake", 40, "Manager", 2850));
		employees.add(new Employee(1002, "Clark", 40, "Manager", 2450));
		employees.add(new Employee(1003, "Scott", 30, "Analyst", 3000));
		employees.add(new Employee(1004, "King", 50, "President", 5000));
		String[] titles = new String[]{"工号", "姓名", "年龄", "职称", "薪资（美元）", "入职时间"};
		String[] fieldNames = new String[]{"id", "name", "age", "job",
				"salery", "addtime"};
		try {
			File file1 = new File("E:\\JXL2003.xls");
			AbstractExcelHelper eh1 = JxlExcelHelper.getInstance();
			eh1.writeExcel(file1,Employee.class, employees);
			eh1.writeExcel(file1,Employee.class, employees, fieldNames, titles);
			List<Employee> list1 = eh1.readExcel(file1,Employee.class, fieldNames,
					true);
			System.out.println("-----------------JXL2003.xls-----------------");
			for (Employee user : list1) {
				System.out.println(user);
			}
			File file2 = new File("E:\\POI2003.xls");
			AbstractExcelHelper eh2 = HssfExcelHelper.getInstance();
			eh2.writeExcel(file2,Employee.class, employees);
			eh2.writeExcel(file2,Employee.class, employees, fieldNames, titles);
			List<Employee> list2 = eh2.readExcel(file2,Employee.class, fieldNames,
					true);
			System.out.println("-----------------POI2003.xls-----------------");
			for (Employee user : list2) {
				System.out.println(user);
			}
			File file3 = new File("E:\\POI2007.xlsx");
			AbstractExcelHelper eh3 = XssfExcelHelper.getInstance();
			eh3.writeExcel(file3,Employee.class, employees);
			eh3.writeExcel(file3,Employee.class, employees, fieldNames, titles);
			List<Employee> list3 = eh3.readExcel(file3,Employee.class, fieldNames,
					true);
			System.out.println("-----------------POI2007.xls-----------------");
			for (Employee user : list3) {
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
}
