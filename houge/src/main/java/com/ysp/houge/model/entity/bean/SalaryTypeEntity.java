package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日下午1:16:49
 * @version 1.0
 */
public class SalaryTypeEntity implements Serializable {
	private int salaryTypeId;

	private String salaryTypeName;

	private int salaryTypeNameResId;

	private boolean isChoosed;

	/**
	 * @描述
	 * @param salaryTypeId
	 * @param salaryTypeName
	 * @param salaryTypeNameResId
	 * @param isChoosed
	 */
	public SalaryTypeEntity(int salaryTypeId, String salaryTypeName,
			boolean isChoosed) {
		super();
		this.salaryTypeId = salaryTypeId;
		this.salaryTypeName = salaryTypeName;
		this.isChoosed = isChoosed;
	}

	/**
	 * @描述
	 * @param salaryTypeId
	 * @param salaryTypeNameResId
	 * @param isChoosed
	 */
	public SalaryTypeEntity(int salaryTypeId, int salaryTypeNameResId,
			boolean isChoosed) {
		super();
		this.salaryTypeId = salaryTypeId;
		this.salaryTypeNameResId = salaryTypeNameResId;
		this.isChoosed = isChoosed;
	}

	/**
	 * @return the salaryTypeId
	 */
	public int getSalaryTypeId() {
		return salaryTypeId;
	}

	/**
	 * @param salaryTypeId
	 *            the salaryTypeId to set
	 */
	public void setSalaryTypeId(int salaryTypeId) {
		this.salaryTypeId = salaryTypeId;
	}

	/**
	 * @return the salaryTypeName
	 */
	public String getSalaryTypeName() {
		return salaryTypeName;
	}

	/**
	 * @param salaryTypeName
	 *            the salaryTypeName to set
	 */
	public void setSalaryTypeName(String salaryTypeName) {
		this.salaryTypeName = salaryTypeName;
	}

	/**
	 * @return the salaryTypeNameResId
	 */
	public int getSalaryTypeNameResId() {
		return salaryTypeNameResId;
	}

	/**
	 * @param salaryTypeNameResId
	 *            the salaryTypeNameResId to set
	 */
	public void setSalaryTypeNameResId(int salaryTypeNameResId) {
		this.salaryTypeNameResId = salaryTypeNameResId;
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
