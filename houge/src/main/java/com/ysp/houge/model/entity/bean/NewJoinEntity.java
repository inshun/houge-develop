package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * 描述： 最新加入实体
 *
 * @ClassName: NewJoinEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月12日 下午2:48:09
 * 
 *        版本: 1.0
 */
public class NewJoinEntity implements Serializable {
	private String date;
	private int count;

	public NewJoinEntity() {
	}

	public NewJoinEntity(String date, int count) {
		super();
		this.date = date;
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
