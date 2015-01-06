package com.koolyun.koolwait.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

/**
 * Json转换器
 * 
 * @author Edwin
 *
 */
public class JsonConverter {

	/**
	 * 将Json数据转换为对应类的对象
	 * 
	 * @param Json数据
	 * @param targetClass
	 *            要转换的对象
	 * @return
	 */
	public static Object fromJson(String json, Class<?> targetClass) {
		Object o = null;

		if (json == null)
			return null;

		try {
			o = new Gson().fromJson(json, targetClass);
		} catch (JsonSyntaxException e) {
			MyLog.e(e.getMessage());
			return null;
		}

		return o;
	}

	/**
	 * 将Object对象转换为对应类的对象
	 * 
	 * @param object
	 *            Object 对象
	 * @param targetClass
	 *            要转换的对象
	 * @return
	 */
	public static Object fromJson(Object object, Class<?> targetClass) {
		Object o = null;

		if (object == null)
			return null;

		try {
			Gson gson = new Gson();
			JsonElement je = gson.toJsonTree(object);
			o = gson.fromJson(je, targetClass);
		} catch (JsonSyntaxException e) {
			MyLog.e(e.getMessage());
			return null;
		}

		return o;
	}
}
