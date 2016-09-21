package com.ysp.houge.model.entity.db;

import java.io.Serializable;

/**
 * @描述:预支纪录列表项实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月12日上午10:55:50
 * @version 1.0
 */
public class ItemPrePaidRecordEntity implements Serializable {
	/**
	 * @字段：serialVersionUID
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年7月12日上午10:58:33
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @字段：state
	 * @功能描述：审核的状态，有三种，审核中，未通过，已通过,0标示已通过，1标示未通过，2标示审核中
	 * @创建人：tyn
	 * @创建时间：2015年7月12日上午10:57:40
	 */
	private int state;
	/**
	 * @字段：money
	 * @功能描述：申请的金额
	 * @创建人：tyn
	 * @创建时间：2015年7月12日上午10:58:02
	 */
	private String money;
	/**
	 * @字段：created
	 * @功能描述：创建的时间
	 * @创建人：tyn
	 * @创建时间：2015年7月12日上午10:58:14
	 */
	private String created;

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the money
	 */
	public String getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

}
