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
 * This class is used for 确定取消对话框页面描述实体类
 * 
 * @author tyn
 * @version 1.0, 2014-10-9 上午10:34:07
 */

public class YesOrNoDialogEntity {
	/***/
	private String titleOne;
	/***/
	private String titleTwo;
	/***/
	private String btnCancelLabel;
	/***/
	private String btnOkLabel;

	public YesOrNoDialogEntity(String titleOne, String titleTwo,
			String btnCancelLabel, String btnOkLabel) {
		super();
		this.titleOne = titleOne;
		this.titleTwo = titleTwo;
		this.btnCancelLabel = btnCancelLabel;
		this.btnOkLabel = btnOkLabel;
	}

	/**
	 * @return the titleOne
	 */
	public String getTitleOne() {
		return titleOne;
	}

	/**
	 * @param titleOne
	 *            the titleOne to set
	 */
	public void setTitleOne(String titleOne) {
		this.titleOne = titleOne;
	}

	/**
	 * @return the titleTwo
	 */
	public String getTitleTwo() {
		return titleTwo;
	}

	/**
	 * @param titleTwo
	 *            the titleTwo to set
	 */
	public void setTitleTwo(String titleTwo) {
		this.titleTwo = titleTwo;
	}

	/**
	 * @return the btnCancelLabel
	 */
	public String getBtnCancelLabel() {
		return btnCancelLabel;
	}

	/**
	 * @param btnCancelLabel
	 *            the btnCancelLabel to set
	 */
	public void setBtnCancelLabel(String btnCancelLabel) {
		this.btnCancelLabel = btnCancelLabel;
	}

	/**
	 * @return the btnOkLabel
	 */
	public String getBtnOkLabel() {
		return btnOkLabel;
	}

	/**
	 * @param btnOkLabel
	 *            the btnOkLabel to set
	 */
	public void setBtnOkLabel(String btnOkLabel) {
		this.btnOkLabel = btnOkLabel;
	}

}
