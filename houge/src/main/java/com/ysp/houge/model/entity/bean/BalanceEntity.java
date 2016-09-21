package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author it_hu
 * 
 *         余额实体
 *
 */
public class BalanceEntity implements Serializable {
	private static final long serialVersionUID = 6152412240169818423L;

	private double price;// 价格
	private String order_id;// 订单号
	private String action_time;// 发生时间
	private String explain;// 描述
	private String good_name;// 商品名称
	private List<Detais> sub_detail;// 详情
	private String commission;

	public BalanceEntity() {
	}

	public BalanceEntity(double price, String order_id, String action_time, String explain, String good_name,
			List<Detais> sub_detail, String commission) {
		super();
		this.price = price;
		this.order_id = order_id;
		this.action_time = action_time;
		this.explain = explain;
		this.good_name = good_name;
		this.sub_detail = sub_detail;
		this.commission = commission;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getAction_time() {
		return action_time;
	}

	public void setAction_time(String action_time) {
		this.action_time = action_time;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getGood_name() {
		return good_name;
	}

	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}

	public List<Detais> getSub_detail() {
		return sub_detail;
	}

	public void setSub_detail(List<Detais> sub_detail) {
		this.sub_detail = sub_detail;
	}

	public class Detais {
		public String type;
		public String explain;
		public String price;
	}
}
