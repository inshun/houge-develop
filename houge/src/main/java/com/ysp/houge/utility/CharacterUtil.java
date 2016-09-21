package com.ysp.houge.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterUtil {
	/**
	 * @描述:判断字符串是否都为数字
	 * @方法名: isNumeric
	 * @param str
	 * @return
	 * @返回类型 boolean
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午8:19:51
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午8:19:51
	 * @修改备注
	 * @since
	 * @throws
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
