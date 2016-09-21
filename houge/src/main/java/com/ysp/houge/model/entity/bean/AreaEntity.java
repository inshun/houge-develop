package com.ysp.houge.model.entity.bean;

/**
 * @描述:区域实体类
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月4日上午10:50:39
 * @version 1.0
 */
public class AreaEntity {
	/**
	 * @字段：areaId
	 * @功能描述：区域ID
	 * @创建人：tyn
	 * @创建时间：2015年7月4日上午10:50:23
	 */
	private int areaId;
	/**
	 * @字段：areaName
	 * @功能描述：区域名称
	 * @创建人：tyn
	 * @创建时间：2015年7月4日上午10:50:31
	 */
	private String areaName;

	/**
	 * @字段：isChoosed
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年7月5日下午8:40:46
	 */
	private boolean isChoosed;

	/**
	 * @描述
	 * @param areaId
	 * @param areaName
	 * @param isChoosed
	 */
	public AreaEntity(int areaId, String areaName, boolean isChoosed) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
		this.isChoosed = isChoosed;
	}

	/**
	 * @return the areaId
	 */
	public int getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the isChoosed
	 */
	public boolean isChoosed() {
		return isChoosed;
	}

	/**
	 * @param isChoosed
	 *            the isChoosed to set
	 */
	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}

}
