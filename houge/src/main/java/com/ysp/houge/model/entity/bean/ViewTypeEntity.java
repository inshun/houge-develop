package com.ysp.houge.model.entity.bean;

/**
 * @描述:视图类别实体类
 * @Copyright Copyright (c) 2015
 * @Company 猴哥.
 * 
 * @author tyn
 * @date 2015年7月4日上午8:11:55
 * @version 1.0
 */
public class ViewTypeEntity {

	/**
	 * @字段：viewTypeId
	 * @功能描述：视图类别的ID
	 * @创建人：tyn
	 * @创建时间：2015年7月4日上午8:11:28
	 */
	private int viewTypeId;
	/**
	 * @字段：viewTypeName
	 * @功能描述：视图类别的名称
	 * @创建人：tyn
	 * @创建时间：2015年7月4日上午8:11:40
	 */
	private String viewTypeName;

	private int viewTypeNameResId;

	/**
	 * @字段：isChoosed
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年7月4日上午10:23:25
	 */
	private boolean isChoosed;

	/**
	 * @描述
	 * @param viewTypeId
	 * @param viewTypeName
	 * @param isChoosed
	 */
	public ViewTypeEntity(int viewTypeId, String viewTypeName, boolean isChoosed) {
		super();
		this.viewTypeId = viewTypeId;
		this.viewTypeName = viewTypeName;
		this.isChoosed = isChoosed;
	}

	/**
	 * @描述
	 * @param viewTypeId
	 * @param viewTypeNameResId
	 * @param isChoosed
	 */
	public ViewTypeEntity(int viewTypeId, int viewTypeNameResId,
			boolean isChoosed) {
		super();
		this.viewTypeId = viewTypeId;
		this.viewTypeNameResId = viewTypeNameResId;
		this.isChoosed = isChoosed;
	}

	/**
	 * @描述
	 * @param viewTypeId
	 * @param viewTypeName
	 */
	public ViewTypeEntity(int viewTypeId, String viewTypeName) {
		super();
		this.viewTypeId = viewTypeId;
		this.viewTypeName = viewTypeName;
	}

	/**
	 * @return the viewTypeId
	 */
	public int getViewTypeId() {
		return viewTypeId;
	}

	/**
	 * @param viewTypeId
	 *            the viewTypeId to set
	 */
	public void setViewTypeId(int viewTypeId) {
		this.viewTypeId = viewTypeId;
	}

	/**
	 * @return the viewTypeName
	 */
	public String getViewTypeName() {
		return viewTypeName;
	}

	/**
	 * @param viewTypeName
	 *            the viewTypeName to set
	 */
	public void setViewTypeName(String viewTypeName) {
		this.viewTypeName = viewTypeName;
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

	/**
	 * @return the viewTypeNameResId
	 */
	public int getViewTypeNameResId() {
		return viewTypeNameResId;
	}

	/**
	 * @param viewTypeNameResId
	 *            the viewTypeNameResId to set
	 */
	public void setViewTypeNameResId(int viewTypeNameResId) {
		this.viewTypeNameResId = viewTypeNameResId;
	}

}
