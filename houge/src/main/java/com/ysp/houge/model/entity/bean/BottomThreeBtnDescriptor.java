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

public class BottomThreeBtnDescriptor {
	public String labelOne;
	public String labelTwo;
	public String labelCancel;
	public int btnOneColorId;
	public int btnTwoColorId;
	/**
	 * @param labelOne
	 * @param labelTwo
	 */
	public BottomThreeBtnDescriptor(String labelOne, String labelTwo) {
		super();
		this.labelOne = labelOne;
		this.labelTwo = labelTwo;
	}

	/**
	 * @param labelOne
	 * @param labelTwo
	 * @param labelCancel
	 */
	public BottomThreeBtnDescriptor(String labelOne, String labelTwo,
			String labelCancel) {
		super();
		this.labelOne = labelOne;
		this.labelTwo = labelTwo;
		this.labelCancel = labelCancel;
	}

	public BottomThreeBtnDescriptor(String labelOne, String labelTwo,
			int btnOneColorId, int btnTwoColorId) {
		super();
		this.labelOne = labelOne;
		this.labelTwo = labelTwo;
		this.btnOneColorId = btnOneColorId;
		this.btnTwoColorId = btnTwoColorId;
	}

	public enum ClickType {
		ClickOne, ClickTwo, Cancel
	}

}
