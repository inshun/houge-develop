package com.ysp.houge.model.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PictureEntity implements Serializable {
	@SerializedName(value = "path")
	private String path;
	@SerializedName(value = "id")
	private String id;

	private int orderid;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

}
