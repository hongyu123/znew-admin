package com.hfw.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map工具类
 * @author zyh
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
		if(map.containsKey(key)){
			return map.get(key);
		}
		key = key.toUpperCase();
		if(map.containsKey(key)){
			return map.get(key);
		}
		key = key.toLowerCase();
		if(map.containsKey(key)){
			return map.get(key);
		}
		return null;
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
	public static Object map2bean(Map<String,Object> map, Class<?> clazz) {
		if(map == null) {
			return null;
		}
		Object result = null;
		try {
			result = clazz.newInstance();
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
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 将bean转化为map
	 * @param bean
	 * @return
	 */
	public static Map<String,Object> bean2map(Object bean){
		if(bean == null) {
			return null;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = bean.getClass().getDeclaredFields();
		if(fields == null) {
			return map;
		}
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				String key = field.getName();
				Object value = field.get(bean);
				if(value != null) {
					map.put(key, value);
				}
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

}
