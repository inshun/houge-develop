package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.List;

public class CommentResultEntity implements Serializable {
	private List<CommentEntity> list;

	private int count;

	private boolean next;

	public CommentResultEntity() {
	}

	public CommentResultEntity(List<CommentEntity> list, int count, boolean next) {
		super();
		this.list = list;
		this.count = count;
		this.next = next;
	}

	public List<CommentEntity> getList() {
		return list;
	}

	public void setList(List<CommentEntity> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

}
