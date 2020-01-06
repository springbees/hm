package com.jacklinsir.hm.common.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * 字符串转化工具类
 *
 */
public class StrUtil {

	/**
	 * 字符串转为驼峰
	 * 
	 * @param str
	 * @return
	 */
	public static String str2hump(String str) {
		StringBuffer buffer = new StringBuffer();
		if (str != null && str.length() > 0) {
			if (str.contains("_")) {
				String[] chars = str.split("_");
				int size = chars.length;
				if (size > 0) {
					List<String> list = Lists.newArrayList();
					for (String s : chars) {
						if (s != null && s.trim().length() > 0) {
							list.add(s);
						}
					}

					size = list.size();
					if (size > 0) {
						buffer.append(list.get(0));
						for (int i = 1; i < size; i++) {
							String s = list.get(i);
							buffer.append(s.substring(0, 1).toUpperCase());
							if (s.length() > 1) {
								buffer.append(s.substring(1));
							}
						}
					}
				}
			} else {
				buffer.append(str);
			}
		}

		return buffer.toString();
	}

	public static HashMap<String, Object> convertToMap(Object obj)
			throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			boolean accessFlag = fields[i].isAccessible();
			fields[i].setAccessible(true);

			Object o = fields[i].get(obj);
			if (o != null)
				map.put(varName, o.toString());

			fields[i].setAccessible(accessFlag);
		}

		return map;
	}

}
