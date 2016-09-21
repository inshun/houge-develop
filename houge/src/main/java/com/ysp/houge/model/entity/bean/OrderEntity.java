package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @author it_hu
 * 
 *         订单实体
 *
 */
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 4293333403394619320L;

	public int id;

	public int num;// 购买数量

	public double totalMoney;// 总金额

	public int payWay;

	public String serviceDate;// 服务时间

	public int skillid;

	public GoodsDetailEntity skillDetailEntity;

	public int user_id;

	public AddressEntity addressEntity;
}
