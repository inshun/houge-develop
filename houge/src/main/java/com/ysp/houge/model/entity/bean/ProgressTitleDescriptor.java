package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * 帶有progressBar的顶部布局描述实体类
 * */
public class ProgressTitleDescriptor implements Serializable {
	private String leftLable;
	private String centerLable;
	private int imageId;

	public ProgressTitleDescriptor(String centerLable) {
		this.centerLable = centerLable;
	}

	public ProgressTitleDescriptor(String leftLable, String centerLable,
			int imageId) {
		this.leftLable = leftLable;
		this.centerLable = centerLable;
		this.imageId = imageId;
	}

	public ProgressTitleDescriptor(String leftLable, String centerLable) {
		super();
		this.leftLable = leftLable;
		this.centerLable = centerLable;
	}

	public String getLeftLable() {
		return leftLable;
	}

	public void setLeftLable(String leftLable) {
		this.leftLable = leftLable;
	}

	public String getCenterLable() {
		return centerLable;
	}

	public void setCenterLable(String centerLable) {
		this.centerLable = centerLable;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

}
