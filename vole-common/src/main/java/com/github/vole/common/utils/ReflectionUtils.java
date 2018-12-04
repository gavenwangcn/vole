package com.github.vole.common.utils;

import java.lang.reflect.*;

/**
 * 反射工具类
 *
 */
@SuppressWarnings("unchecked")
public final class ReflectionUtils {

	public static <E> E getInstanceByName(String className) throws Exception {
		Class<?> clazz = Class.forName(className);
		return getInstance(clazz, null);
	}

	public static <E> E getInstanceByName(String className, Object[] params) throws Exception {
		Class<?> clazz = Class.forName(className);
		return getInstance(clazz, params);
	}

	public static <E> E getInstance(Class<?> clazz, Object[] params) throws Exception {
		Constructor<?> c = null;
		if (params == null || params.length == 0) {
			c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			return (E) c.newInstance();
		}
		Class<?>[] paramTypes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			paramTypes[i] = params[i].getClass();
		}
		c = clazz.getDeclaredConstructor(paramTypes);
		c.setAccessible(true);
		return (E) c.newInstance(params);
	}

	/*********************** annotation *************************/

	/************************* field ****************************/
	public static <E> E getFieldValue(Object target, String fieldName) throws Exception {
		Field field = ((target instanceof Class) ? (Class<?>) target : target.getClass()).getDeclaredField(fieldName);
		field.setAccessible(true);
		return (E) field.get(target);
	}

	public static void setFieldValue(Object target, String fieldName, Object value) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}

	/************************* method *************************/
	public static <T> T invokeNoParam(Object target, String methodName) throws Exception {
		if (target != null) {
			Method method = target.getClass().getDeclaredMethod(methodName);
			method.setAccessible(true);
			return (T) method.invoke(target);
		}
		return null;
	}

	/**
	 * 获取泛型Class
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getSuperClassGenericType(Class<?> clazz) {
		Type genType = clazz.getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<?>) params[0];
	}

}
