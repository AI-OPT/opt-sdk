package com.ai.opt.sdk.excel.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * @author gucl
 * 
 */
public class ReflectUtil {

	/**
	 * 反射调用指定构造方法创建对象
	 * 
	 * @param clazz
	 *            对象类型
	 * @param argTypes
	 *            参数类型
	 * @param args
	 *            构造参数
	 * @return 返回构造后的对象
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 */
	public static <T> T invokeConstructor(Class<T> clazz, Class<?>[] argTypes,
			Object[] args) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<T> constructor = clazz.getConstructor(argTypes);
		return constructor.newInstance(args);
	}

	/**
	 * 反射调用指定对象属性的getter方法
	 * 
	 * @param <T>
	 *            泛型
	 * @param target
	 *            指定对象
	 * @param fieldName
	 *            属性名
	 * @return 返回调用后的值
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * 
	 */
	public static <T> Object invokeGetter(T target, String fieldName)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		// 如果属性名为xxx，则方法名为getXxx
		String methodName = "get" + ExcelStringUtil.firstCharUpperCase(fieldName);
		Method method = target.getClass().getMethod(methodName);
		return method.invoke(target);
	}

	/**
	 * 反射调用指定对象属性的setter方法
	 * 
	 * @param <T>
	 *            泛型
	 * @param target
	 *            指定对象
	 * @param fieldName
	 *            属性名
	 * @param argTypes
	 *            参数类型
	 * @param args
	 *            参数列表
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * 
	 */
	public static <T> void invokeSetter(T target, String fieldName, Object args)
			throws NoSuchFieldException, SecurityException,
			NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// 如果属性名为xxx，则方法名为setXxx
		String methodName = "set" + ExcelStringUtil.firstCharUpperCase(fieldName);
		Class<?> clazz = target.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		Method method = clazz.getMethod(methodName, field.getType());
		method.invoke(target, args);
	}

	/*public static void main(String[] args) {
		try {
			Class<Employee> clazz = Employee.class;
			Employee user = ReflectUtil.invokeConstructor(clazz,
					new Class<?>[]{long.class, String.class, int.class,
							String.class, double.class}, new Object[]{1001,
							"Linux", 30, "123", 20.55});
			System.out.println(user);
			ReflectUtil.invokeSetter(user, "salery", 2055);
			System.out.println(user);
			Object ret = ReflectUtil.invokeGetter(user, "salery");
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
