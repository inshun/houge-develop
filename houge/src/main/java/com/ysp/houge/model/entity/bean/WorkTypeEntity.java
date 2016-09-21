package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * @描述: 工种列表实体
 * example ： 热门 微信推广 淘宝设计
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月14日下午1:20:52
 * @version 1.0
 */
public class WorkTypeEntity implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -2251774857383780670L;

	private int id;// 工种ID

	private String name;// 工种名称

	private String is_watch;


	public WorkTypeEntity(int id, String name, String is_watch) {
		super();
		this.id = id;
		this.name = name;
		this.is_watch = is_watch;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIs_watch() {
		return is_watch;
	}

	public void setIs_watch(String is_watch) {
		this.is_watch = is_watch;
	}


}
