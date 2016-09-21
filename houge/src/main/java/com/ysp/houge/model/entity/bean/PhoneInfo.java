package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @描述: 手机信息实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月28日下午5:37:09
 * @version 1.0
 */
public class PhoneInfo implements Serializable {
	// 手机唯一编号
	private String device_id;

	// 手机型号
	private String device_model;

	//屏幕宽
	private int screenWidth;

	//屏幕高
	private int screenHeight;

	public PhoneInfo() {
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getDevice_model() {
		return device_model;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
}
