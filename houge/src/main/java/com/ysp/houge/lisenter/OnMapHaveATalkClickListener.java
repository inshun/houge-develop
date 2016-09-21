package com.ysp.houge.lisenter;

import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

/**
 * 描述： 地图聊一聊点击事件
 *
 * @ClassName: OnMapHaveATalkClickListener
 * 
 * @author: hx
 * 
 * @date: 2015年12月26日 上午9:55:42
 * 
 *        版本: 1.0
 */
public interface OnMapHaveATalkClickListener {

	/** 商品详情 */
	void onHaveATalkClick(GoodsDetailEntity detailEntity);
}
