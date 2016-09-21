package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @描述: 买家订单实体
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月27日下午4:34:43
 * @version 1.0
 */
public class SkillEntity implements Serializable{
	public String title; // 订单标题
	public String desc; // 订单描述
	public double price; // 订单单价
	public String unit; // 订单计价单位
	public String image; // 技能图片
	public String is_no_salary; // 是否免薪实习0否1是
	public String is_room; // 提供住宿
	public String is_good; // 提供工作餐

	public SkillEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SkillEntity(String title, String desc, int price, String unit, String is_no_salary, String is_room,
			String is_good) {
		super();
		this.title = title;
		this.desc = desc;
		this.price = price;
		this.unit = unit;
		this.is_no_salary = is_no_salary;
		this.is_room = is_room;
		this.is_good = is_good;
	}

}
