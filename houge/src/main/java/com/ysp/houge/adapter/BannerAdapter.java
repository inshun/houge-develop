package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.model.entity.bean.BannerEntity;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * 描述： 暂时废弃
 *
 * @ClassName: BannerAdapter
 * 
 * @author: hx
 * 
 * @date: 2015年12月11日 下午11:36:22
 * 
 *        版本: 1.0
 */
public class BannerAdapter extends PagerAdapter {
	private List<BannerEntity> banner;

	public BannerAdapter(List<BannerEntity> banner) {
		super();
		this.banner = banner;
	}

	@Override
	public int getCount() {
		return banner.size();
	}
	//
	// @Override
	// public void destroyItem(View container, int position, Object object) {
	// // TODO Auto-generated method stub
	// super.destroyItem(container, position, object);
	// }

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}