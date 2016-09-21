package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @描述: 需求实体
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月27日下午4:34:43
 * @version 1.0
 */
public class NeedEntity implements Serializable {
	public String title; // 订单标题
	public String desc; // 订单描述
	public double price; // 订单单价
	public String unit; // 订单计价单位
	public int count; // 数量
	public String serverTime; // 服务时间(起始)
	public int contact; // 联系方式编号
	public String picPath; // 上传时的图片集合字符串(,号隔开)
	public String recordPath; // 录音的路径

	public NeedEntity() {
		super();
	}

	/**
	 * 上传时候默认的构造方法
	 */
	public NeedEntity(String title, String desc, double price, String serverTime,
			int contact) {
		super();
		this.title = title;
		this.desc = desc;
		this.price = price;
		this.serverTime = serverTime;
		this.contact = contact;
	}

}
