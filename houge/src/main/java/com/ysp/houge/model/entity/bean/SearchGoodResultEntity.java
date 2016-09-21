package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： 搜索商品结果实体实体
 *
 * @ClassName: SearchResultEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月10日 下午3:05:35
 * 
 *        版本: 1.0
 */
public class SearchGoodResultEntity implements Serializable {
	private List<GoodsDetailEntity> list;
	private boolean next;

	public SearchGoodResultEntity() {
	}

	public SearchGoodResultEntity(List<GoodsDetailEntity> list, boolean next) {
		super();
		this.list = list;
		this.next = next;
	}

	public List<GoodsDetailEntity> getList() {
		return list;
	}

	public void setList(List<GoodsDetailEntity> list) {
		this.list = list;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

}
