package com.ysp.houge.model.entity.db;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.ysp.houge.model.entity.bean.PhotoEntity;

/**
 * @描述:工作经历列表项
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月25日上午11:35:22
 * @version 2.2
 */
public class ItemWorkInfoEntity {
	/**
	 * @字段：workInfoId
	 * @功能描述：工作经历ID
	 * @创建人：tyn
	 * @创建时间：2015年7月25日上午11:38:20
	 */
	@SerializedName(value = "id")
	private int workInfoId;
	/**
	 * @字段：workInfoName
	 * @功能描述：工作经历名称
	 * @创建人：tyn
	 * @创建时间：2015年7月25日上午11:38:28
	 */
	@SerializedName(value = "title")
	private String workInfoName;
	/**
	 * @字段：workInfoTime
	 * @功能描述：工作经历时间
	 * @创建人：tyn
	 * @创建时间：2015年7月25日上午11:38:35
	 */
	@SerializedName(value = "created")
	private String workInfoTime;
	/**
	 * @字段：workInfoContent
	 * @功能描述：工作经历内容
	 * @创建人：tyn
	 * @创建时间：2015年7月25日上午11:38:42
	 */
	@SerializedName(value = "content")
	private String workInfoContent;
	/**
	 * @字段：photoEntities
	 * @功能描述：工作经历的图片
	 * @创建人：tyn
	 * @创建时间：2015年7月25日上午11:38:49
	 */
	@SerializedName(value = "piclist")
	private List<PhotoEntity> photoEntities;

	/**
	 * @return the workInfoId
	 */
	public int getWorkInfoId() {
		return workInfoId;
	}

	/**
	 * @param workInfoId
	 *            the workInfoId to set
	 */
	public void setWorkInfoId(int workInfoId) {
		this.workInfoId = workInfoId;
	}

	/**
	 * @return the workInfoName
	 */
	public String getWorkInfoName() {
		return workInfoName;
	}

	/**
	 * @param workInfoName
	 *            the workInfoName to set
	 */
	public void setWorkInfoName(String workInfoName) {
		this.workInfoName = workInfoName;
	}

	/**
	 * @return the workInfoTime
	 */
	public String getWorkInfoTime() {
		return workInfoTime;
	}

	/**
	 * @param workInfoTime
	 *            the workInfoTime to set
	 */
	public void setWorkInfoTime(String workInfoTime) {
		this.workInfoTime = workInfoTime;
	}

	/**
	 * @return the workInfoContent
	 */
	public String getWorkInfoContent() {
		return workInfoContent;
	}

	/**
	 * @param workInfoContent
	 *            the workInfoContent to set
	 */
	public void setWorkInfoContent(String workInfoContent) {
		this.workInfoContent = workInfoContent;
	}

	/**
	 * @return the photoEntities
	 */
	public List<PhotoEntity> getPhotoEntities() {
		return photoEntities;
	}

	/**
	 * @param photoEntities
	 *            the photoEntities to set
	 */
	public void setPhotoEntities(List<PhotoEntity> photoEntities) {
		this.photoEntities = photoEntities;
	}

}
