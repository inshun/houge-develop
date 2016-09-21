package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @描述: 选择服务时间的时间实体
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月25日下午2:52:36
 * @version 1.0
 */
public class DateTimeEntity implements Serializable {

	public String week;
	public String date;

	public DateTimeEntity() {
		super();
	}
	
	public DateTimeEntity(String week, String date) {
		super();
		this.week = week;
		this.date = date;
	}

}
