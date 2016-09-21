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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * This class is used for ...
 * 
 * @author tyn
 * @version 1.0, 2014-10-18 下午4:09:20
 */

@SuppressLint("SimpleDateFormat")
public class DateUtils {
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年
	private static final Set<String> dateFormatSet = new HashSet<String>();
	static {
		dateFormatSet.add(YYYY_MM_DD_HH_MM);
		dateFormatSet.add(YYYY_MM_DD);
		dateFormatSet.add(YYYY_MM_DD_HH_MM_SS);
	}

	public static String getTimeByMilliseconds(Long milliseconds,
			String formatType) {
		if (milliseconds == null || TextUtils.isEmpty(formatType)
				|| !dateFormatSet.contains(formatType))
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milliseconds);
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		return dateFormat.format(cal.getTime());
	}

	public static String getTimeByMilliseconds(Date date, String formatType) {
		if (date == null || TextUtils.isEmpty(formatType)
				|| !dateFormatSet.contains(formatType))
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		return dateFormat.format(date);
	}

	public static Date getDateByString(String dateStr, String formatType) {
		if (TextUtils.isEmpty(dateStr) || !dateFormatSet.contains(formatType))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回文字描述的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText(String time) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (time == null) {
				return "";
			}
			Date date = sdf.parse(time);
			if (date == null) {
				return null;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			long diff = new Date().getTime() - date.getTime();
			long r = 0;
			if (diff > year) {
				r = (diff / year);
				return (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH);
				// return r + "年前";
			}
			if (diff > month) {
				r = (diff / month);
				return (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH);
				// return r + "月前";
			}
			if (diff > day) {
				r = (diff / day);
				return (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH);
				// return r + "天前";
			}
			if (diff > hour) {
				r = (diff / hour);
				return r + "小时前";
			}
			if (diff > minute) {
				r = (diff / minute);
				return r + "分钟前";
			}
			return "刚刚";
		} catch (ParseException e) {
			return "";
		}
	}

	/**
	 * @描述:根据时间格式返回当前时间的字符串
	 * @方法名: getNowByFormat
	 * @param formatType
	 * @return
	 * @返回类型 String
	 * @创建人 welian_gyc
	 * @创建时间 2015年8月18日下午2:28:36
	 * @修改人 welian_gyc
	 * @修改时间 2015年8月18日下午2:28:36
	 * @修改备注
	 * @since
	 * @throws
	 */
	public static String getNowByFormat(String formatType) {
		if (TextUtils.isEmpty(formatType)
				|| !dateFormatSet.contains(formatType))
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		return dateFormat.format(new Date());
	}
}
