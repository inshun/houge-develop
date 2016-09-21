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
 * @version 1.0, 2014-10-13 下午9:13:30
 */

public class ClearEditDescriptor {
	private String hint;
	private String btnLabel;

	public ClearEditDescriptor(String hint, String btnLabel) {
		super();
		this.hint = hint;
		this.btnLabel = btnLabel;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getBtnLabel() {
		return btnLabel;
	}

	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}

}
