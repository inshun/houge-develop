package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月14日下午1:20:52
 * @version 1.0
 */
public class CharacteristicEntity implements Serializable {
	/**
	 * @字段：serialVersionUID
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年6月14日下午6:51:48
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @字段：characteristicId
	 * @功能描述：特点的ID
	 * @创建人：tyn
	 * @创建时间：2015年6月14日下午3:21:57
	 */
	@SerializedName(value = "id")
	private int characteristicId;
	/**
	 * @字段：characteristicName
	 * @功能描述：特点的名称
	 * @创建人：tyn
	 * @创建时间：2015年6月14日下午3:21:59
	 */
	@SerializedName(value = "name")
	private String characteristicName;

	/**
	 * @字段：isChecked
	 * @功能描述：是否被选中
	 * @创建人：tyn
	 * @创建时间：2015年6月14日下午3:23:54
	 */
	private boolean isChecked;

	/**
	 * @return the characteristicId
	 */
	public int getCharacteristicId() {
		return characteristicId;
	}

	/**
	 * @param characteristicId
	 *            the characteristicId to set
	 */
	public void setCharacteristicId(int characteristicId) {
		this.characteristicId = characteristicId;
	}

	/**
	 * @return the characteristicName
	 */
	public String getCharacteristicName() {
		return characteristicName;
	}

	/**
	 * @param characteristicName
	 *            the characteristicName to set
	 */
	public void setCharacteristicName(String characteristicName) {
		this.characteristicName = characteristicName;
	}

	/**
	 * @return the isChecked
	 */
	public boolean isChecked() {
		return isChecked;
	}

	/**
	 * @param isChecked
	 *            the isChecked to set
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
