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
 * @描述:编辑框的属性配置实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午11:20:31
 * @version 1.0
 */
public class EditChooseViewDescriptor {
	/**
	 * @字段：leftText
	 * @功能描述：编辑框左侧文字
	 * @创建人：tyn
	 * @创建时间：2015年7月18日上午11:20:44
	 */
	private String leftText;
	/**
	 * @字段：hintText
	 * @功能描述：编辑框的hint文字
	 * @创建人：tyn
	 * @创建时间：2015年7月18日上午11:20:59
	 */
	private String hintText;
	/**
	 * @字段：viewColor
	 * @功能描述：编辑框底部下划线的颜色
	 * @创建人：tyn
	 * @创建时间：2015年7月18日上午11:19:35
	 */
	private int viewColor;

	public EditChooseViewDescriptor(String hintText) {
		super();
		this.hintText = hintText;
	}

	/**
	 * @描述
	 * @param leftText
	 * @param hintText
	 */
	public EditChooseViewDescriptor(String hintText, String leftText) {
		super();
		this.leftText = leftText;
		this.hintText = hintText;
	}

	/**
	 * @描述
	 * @param leftText
	 * @param hintText
	 * @param viewColor
	 */
	public EditChooseViewDescriptor(String leftText, String hintText,
			int viewColor) {
		super();
		this.leftText = leftText;
		this.hintText = hintText;
		this.viewColor = viewColor;
	}

	/**
	 * @return the leftText
	 */
	public String getLeftText() {
		return leftText;
	}

	/**
	 * @param leftText
	 *            the leftText to set
	 */
	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}

	/**
	 * @return the hintText
	 */
	public String getHintText() {
		return hintText;
	}

	/**
	 * @param hintText
	 *            the hintText to set
	 */
	public void setHintText(String hintText) {
		this.hintText = hintText;
	}

	/**
	 * @return the viewColor
	 */
	public int getViewColor() {
		return viewColor;
	}

	/**
	 * @param viewColor
	 *            the viewColor to set
	 */
	public void setViewColor(int viewColor) {
		this.viewColor = viewColor;
	}

}
