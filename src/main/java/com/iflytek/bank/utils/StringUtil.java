package com.iflytek.bank.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName： StringUtil
 * @Author: dhSu
 * @Description:字符串工具类
 * @Date:Created in 2018年10月27日
 */

public class StringUtil {
	/**
	 * 判断字符串是否为空 包括是否为null，是否为空字符串，过滤完空格后是否为空字符串
	 * @param str 字符串
	 * @return 是否为空
	 */
	public static boolean isNullOrEmpry(final String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 匹配正则表达式
	 * @param regx
	 * @param input
	 * @return
	 */
	public static boolean matchPattern(final String regx,String input) {
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()){
			return true;
		}else{
			return false;
		}
	}
}
