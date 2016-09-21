package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @描述:学校实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月13日下午6:57:21
 * @version 1.0
 */
public class SchoolEntity implements Serializable {

	/**
	 * @字段：serialVersionUID
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年7月19日上午11:58:24
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @字段：id
	 * @功能描述：学校的ID
	 * @创建人：tyn
	 * @创建时间：2015年7月13日下午6:57:05
	 */
	@SerializedName(value = "id")
	private int id;
	/**
	 * @字段：name
	 * @功能描述：学校的名称
	 * @创建人：tyn
	 * @创建时间：2015年7月13日下午6:57:07
	 */
	@SerializedName(value = "name")
	private String name;

	/**
	 * @字段：sortLetter
	 * @功能描述：排序字母
	 * @创建人：tyn
	 * @创建时间：2015年9月13日上午10:18:48
	 */
	private String sortLetter;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sortLetter
	 */
	public String getSortLetter() {
		return sortLetter;
	}

	/**
	 * @param sortLetter
	 *            the sortLetter to set
	 */
	public void setSortLetter(String sortLetter) {
		this.sortLetter = sortLetter;
	}

}
