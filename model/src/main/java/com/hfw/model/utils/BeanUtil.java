package com.hfw.model.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * bean工具类
 * @author farkle
 * @date 2022-04-06
 */
public class BeanUtil {
	/**
	 * 从一个bean 转换到另一个bean（相同属性赋值）
	 * @param bean 要转换的bean对象
	 * @param convertClass 转换的目标类型
	 * @return 目标bean对象
	 */
	public static <T> T convert(Object bean, Class<T> convertClass) throws Exception {
		T t = convertClass.getConstructor().newInstance();
		Class<?> clazz = bean.getClass();
		Field[] fields = convertClass.getDeclaredFields();
		if(fields==null || fields.length<=0) {
			return null;
		}
		for(Field field : fields) {
			String fieldName = field.getName();
			Field convertField = clazz.getDeclaredField(fieldName);
			convertField.setAccessible(true);
			Object value = convertField.get(bean);
			if(value == null) {
				continue;
			}
			field.setAccessible(true);
			field.set(t, value);
		}
		return t;
	}

	/**
	 * targe 非空属性拷贝
	 * @param source
	 * @param target
	 */
	public static <T> void copyNotNullProperties(T source, T target) throws IllegalAccessException {
		Class<?> clazz = source.getClass();
		if(clazz != target.getClass()){
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		if(fields==null || fields.length<=0) {
			return ;
		}
		for(Field field : fields) {
			field.setAccessible(true);
			Object sourceValue = field.get(source);
			Object targetValue = field.get(target);
			if(sourceValue!=null && targetValue==null){
				field.set(target, sourceValue);
			}
		}
	}

	/**
	 * 从一个List<bean> 转换到另一个List<bean>（相同属性赋值）
	 * @param list 要转换的List<bean>
	 * @param convertClass 转换的目标类型
	 * @return 目标List<bean>
	 */
	public static <T> List<T> convert(List<?> list, Class<T> convertClass) throws Exception {
		if(list==null) {
			return null;
		}
		if(list.size()<=0) {
			return new ArrayList<T>();
		}
		List<T> tList = new ArrayList<T>();
		for(Object obj : list) {
			tList.add(convert(obj, convertClass));
		}
		return tList;
	}

	/**
	 * 实体类对象转换代码生成
	 * @param beanclass
	 * @param ofClass
	 */
	public static void genBeanCopy(Class<?> beanclass, Class<?> ofClass){
		String className = beanclass.getSimpleName();
		String beanName = StrUtil.lowerCase(className);
		String ofClassName = ofClass.getSimpleName();
		String ofBeanName = StrUtil.lowerCase(ofClassName);
		System.out.println(StrUtil.replace("public static ${0} of(${1} ${2}){",className,ofClassName,ofBeanName));
		System.out.println(StrUtil.replace("${0} ${1} = new ${0}();",className,beanName));
		Field[] fields = beanclass.getDeclaredFields();
		if(fields!=null && fields.length>0) {
			for(Field field : fields) {
				try {
					String fieldName = field.getName();
					Field toField = ofClass.getDeclaredField(fieldName);
					toField.setAccessible(true);
					fieldName = StrUtil.upperCase(fieldName);
					System.out.println(StrUtil.replace("${0}.set${1}(${2}.get${1}());",beanName,fieldName,ofBeanName));
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
		System.out.println(StrUtil.replace("return ${0};", beanName));
		System.out.println("}");
	}

}
