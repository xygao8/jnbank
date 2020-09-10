package com.iflytek.bank.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

public class JsonUtil {
	
	/**
	 * jsonString转bean
	 */
	public static <T> T jsonStr2Bean(String jsonStr,Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clazz);
	}
	
	/**
	 * bean转jsonString
	 */
	public static String bean2JsonStr(Object obj) {
		return JSON.toJSONString(obj);
	}
}
