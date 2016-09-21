package com.ysp.houge.model.entity.db;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @描述:请求兼职详情时返回的工作特点
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月15日下午6:52:31
 * @version 1.0
 */
public class JobPersonStyleEntity implements Serializable {
	/**
	 * @字段：serialVersionUID
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:55:03
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @字段：id
	 * @功能描述：特点ID
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:54:08
	 */
	@SerializedName(value = "id")
	private int id;

	/**
	 * @字段：title
	 * @功能描述：特点名称
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:54:18
	 */
	@SerializedName(value = "title")
	private String title;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
