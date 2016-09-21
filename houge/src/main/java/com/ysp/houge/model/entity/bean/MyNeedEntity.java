package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： 我的技能网络返回实体
 *
 * @ClassName: MyNeedEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月12日 下午8:23:26
 * 
 *        版本: 1.0
 */
public class MyNeedEntity implements Serializable {
	private List<GoodsDetailEntity> list;

	public MyNeedEntity() {
		super();
	}

	public MyNeedEntity(List<GoodsDetailEntity> list) {
		super();
		this.list = list;
	}

	public List<GoodsDetailEntity> getList() {
		return list;
	}

	public void setList(List<GoodsDetailEntity> list) {
		this.list = list;
	}

}
