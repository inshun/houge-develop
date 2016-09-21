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
 * This class is used for ...
 * 
 * @author tyn
 * @version 1.0, 2014-9-5 ����10:37:17
 */

public class BottomTwoBtnDescriptor {

	/** ��һ����ť��ʾ���� */
	private String labelOne;
	/** ȡ��ť��ʾ���� */
	private String labelCancel;

	private int btnOneColorId;

	public BottomTwoBtnDescriptor(String labelOne) {
		super();
		this.labelOne = labelOne;
	}

	public BottomTwoBtnDescriptor(String labelOne, String labelCancel) {
		super();
		this.labelOne = labelOne;
		this.labelCancel = labelCancel;
	}

	public BottomTwoBtnDescriptor(String labelOne, String labelCancel,
			int btnOneColorId) {
		super();
		this.labelOne = labelOne;
		this.labelCancel = labelCancel;
		this.btnOneColorId = btnOneColorId;
	}

	public String getLabelOne() {
		return labelOne;
	}

	public void setLabelOne(String labelOne) {
		this.labelOne = labelOne;
	}

	public String getLabelCancel() {
		return labelCancel;
	}

	public void setLabelCancel(String labelCancel) {
		this.labelCancel = labelCancel;
	}

	public int getBtnOneColorId() {
		return btnOneColorId;
	}

	public void setBtnOneColorId(int btnOneColorId) {
		this.btnOneColorId = btnOneColorId;
	}

}
