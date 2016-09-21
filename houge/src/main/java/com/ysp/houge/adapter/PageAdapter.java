package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.ui.base.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @描述: 推荐页面的内部Fragment适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月29日下午5:19:40
 * @version 1.0
 */
public class PageAdapter extends FragmentStatePagerAdapter {

	private List<BaseFragment> list;
	private List<WorkTypeEntity> title;

	public PageAdapter(FragmentManager fm, List<BaseFragment> list, List<WorkTypeEntity> title) {
		super(fm);
		this.list = list;
	}

	public void setList(List<BaseFragment> list) {
		this.list = list;
	}

	public void setTitle(List<WorkTypeEntity> title) {
		this.title = title;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return title.get(position).getName();
	}
}
