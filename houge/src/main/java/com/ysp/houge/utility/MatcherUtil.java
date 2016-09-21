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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * This class is used for 校验工具类
 * 
 * @author tyn
 * @version 1.0, 2015-3-4 下午1:41:09
 */

public class MatcherUtil {
	/**
	 * 检测电话号码格式是否正确
	 * 
	 * @return
	 */
	public static boolean checkTelephoneNumberVilid(String telephoneNumber) {
		if (TextUtils.isEmpty(telephoneNumber)) {
			return false;
		}
		if (telephoneNumber.length() < 11) {
			return false;
		}
		if (!telephoneNumber.startsWith("1")) {
			return false;
		}
		if (telephoneNumber.startsWith("10")
				|| telephoneNumber.startsWith("11")
				|| telephoneNumber.startsWith("12")
				|| telephoneNumber.startsWith("19")) {
			return false;
		}
		return true;
	}

	/**
	 * 检测邮箱格式是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmailVilid(String email) {
		if (TextUtils.isEmpty(email)) {
			return false;
		}
		boolean tag = true;
		final String pattern1 = "^(\\w+|\\w+\\.?\\w+)@\\w+\\.(\\w{2,3}|\\w{2,3}\\.\\w{2,3})$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		try {
			tag = mat.matches();
		} catch (Exception exception) {
			tag = false;
		}

		return tag;
	}
}
