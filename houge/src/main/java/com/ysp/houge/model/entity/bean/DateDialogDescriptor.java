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
package com.ysp.houge.model.entity.bean;

/**
 * This class is used for 日期对话框初始化描述类
 * 
 * @author tyn
 * @version 1.0, 2014-11-28 下午5:41:42
 */

public class DateDialogDescriptor {

	/** 默认的显示的年份 */
	public int defaultDisplayYear;
	/** 默认的显示的月份 */
	public int defaultDisplayMonth;
	/** 是否显示至今按钮 */
	public boolean isShowNowOn;

	public DateDialogDescriptor(int defaultDisplayYear,
			int defaultDisplayMonth, boolean isShowNowOn) {
		this.defaultDisplayYear = defaultDisplayYear;
		this.defaultDisplayMonth = defaultDisplayMonth;
		this.isShowNowOn = isShowNowOn;
	}

}
