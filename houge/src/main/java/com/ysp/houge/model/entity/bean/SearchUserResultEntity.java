package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.List;

import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * 描述： 搜索结果实体
 *
 * @ClassName: SearchResultEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月10日 下午3:05:35
 * 
 *        版本: 1.0
 */
public class SearchUserResultEntity implements Serializable {
	private List<UserInfoEntity> list;
	private boolean next;

	public SearchUserResultEntity() {
	}

	public SearchUserResultEntity(List<UserInfoEntity> list, boolean next) {
		super();
		this.list = list;
		this.next = next;
	}

	public List<UserInfoEntity> getList() {
		return list;
	}

	public void setList(List<UserInfoEntity> list) {
		this.list = list;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

}
