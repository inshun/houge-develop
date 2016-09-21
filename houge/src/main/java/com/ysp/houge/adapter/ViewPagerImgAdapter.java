package com.ysp.houge.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ViewPagerImgAdapter extends PagerAdapter {
	private List<ImageView> pages;

	public ViewPagerImgAdapter(List<ImageView> lists) {
		pages = lists;
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(pages.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(pages.get(position), 0);
		return pages.get(position);
	}

	@Override
	public boolean isViewFromObject(View vizsew, Object object) {
		return vizsew == object;
	}

}
