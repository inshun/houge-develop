package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @描述: 优惠劵实体
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月30日下午5:03:22
 * @version 1.0
 */
public class CoupomEntity implements Serializable {
	private static final long serialVersionUID = 6037971368078757977L;

	/** 选中状态 */
	public boolean isCheck;

	/** 金额 */
	public Double money;

	/** 描述 */
	public String description;

	/** 有效期 */
	public String validity;

	/** 劵号（编号） */
	public String id;

	public CoupomEntity(boolean isCheck, Double money, String description, String validity, String id) {
		super();
		this.isCheck = isCheck;
		this.money = money;
		this.description = description;
		this.validity = validity;
		this.id = id;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
