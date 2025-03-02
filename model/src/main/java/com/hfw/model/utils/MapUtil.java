package com.hfw.model.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map工具类
 * @author farkle
 * @date 2022-04-06
 */
public class MapUtil {

	/**
	 * 从map中获取key的值，不区分大小写
	 * @param map
	 * @param key
	 * @return
	 */
	public static Object getValue(Map<String,Object> map,String key){
		Object value = map.get(key);
		if(value==null){
			value = map.get(key.toUpperCase());
		}
		if(value==null){
			value = map.get(key.toLowerCase());
		}
		return value;
	}

	/**
	 * key只要包含就返回
	 * @param map
	 * @param key
	 * @return
	 */
	public static Object containsGet(Map<String,?> map, String key){
		if(key==null){
			return null;
		}
		for(String k : map.keySet()){
			if(k==null){
				continue;
			}
			if(k.contains(key) || key.contains(k)){
				return map.get(k);
			}
		}
		return null;
	}

	/**
	 * 将map转化为bean
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static Object map2bean(Map<String,Object> map, Class<?> clazz) throws Exception {
		if(map == null) {
			return null;
		}
		Object result = clazz.getConstructor().newInstance();
		Set<String> keySet = map.keySet();
		for(String key : keySet) {
			Object value = map.get(key);
			if(value == null) {
				continue ;
			}
			try {
				Field field = clazz.getDeclaredField(key);
				field.setAccessible(true);
				field.set(result, value);
			}catch (Exception e){
			}
		}
		return result;
	}
	/**
	 * 将bean转化为map
	 * @param bean
	 * @return
	 */
	public static Map<String,Object> bean2map(Object bean) throws IllegalAccessException {
		if(bean == null) {
			return null;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = bean.getClass().getDeclaredFields();
		if(fields == null) {
			return map;
		}
		for (Field field : fields) {
			field.setAccessible(true);
			String key = field.getName();
			Object value = field.get(bean);
			if(value != null) {
				map.put(key, value);
			}
		}
		return map;
	}

}
