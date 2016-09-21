package com.ysp.houge.model.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @描述:图片实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月25日上午11:37:02
 * @version 2.2
 */
public class PhotoEntity implements Serializable {
	/**
	 * @字段：photoId
	 * @功能描述：图片的ID
	 * @创建人：tyn
	 * @创建时间：2015年7月25日上午11:37:43
	 */
	@SerializedName(value = "id")
	private int photoId;
	/**
	 * @字段：photoUrl
	 * @功能描述：图片的地址
	 * @创建人：tyn
	 * @创建时间：2015年7月25日上午11:37:49
	 */
	@SerializedName(value = "picurl")
	private String photoUrl;

	/**
	 * @return the photoId
	 */
	public int getPhotoId() {
		return photoId;
	}

	/**
	 * @param photoId
	 *            the photoId to set
	 */
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl
	 *            the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
