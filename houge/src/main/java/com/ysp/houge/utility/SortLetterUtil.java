/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.utility;

import android.text.TextUtils;

/**
 * This class is used for 获取排序字母工具类
 * 
 * @author tyn
 * @version 1.0, 2014-12-29 下午6:05:11
 */

public class SortLetterUtil {
	/**
	 * 获取字符串的字母，如果没有则返回"＃"
	 * 
	 * @param name
	 * @return
	 */
	public static String getSortLetter(String name) {
		String string;
		if (!TextUtils.isEmpty(name)) {
			// 汉字转换成拼音
			String pinyin = PinYin.getPinYin(name);
			// 正则表达式，判断首字母是否是英文字母
			if (TextUtils.isEmpty(pinyin)) {
				string = "#";
			} else if (Character.isDigit(pinyin.charAt(0))) {
				string = "#";
			} else {
				string = pinyin;
				char header = pinyin.toLowerCase().charAt(0);
				if (header < 'a' || header > 'z') {
					string = "#";
				}
			}
		} else {
			string = "#";
		}
		return string;
	}
}
