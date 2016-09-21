package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： 附近列表返回的实体
 *
 * @ClassName: NearbyListEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月11日 下午11:15:04
 * 
 *        版本: 1.0
 */
public class NearbyListEntity implements Serializable {

	private List<BannerEntity> banner;
	private List<GoodsDetailEntity> goodsList;

	public NearbyListEntity() {
	}

	public NearbyListEntity(List<BannerEntity> banner, List<GoodsDetailEntity> goodsList) {
		super();
		this.banner = banner;
		this.goodsList = goodsList;
	}

	public List<BannerEntity> getBanner() {
		return banner;
	}

	public void setBanner(List<BannerEntity> banner) {
		this.banner = banner;
	}

	public List<GoodsDetailEntity> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodsDetailEntity> goodsList) {
		this.goodsList = goodsList;
	}

}
