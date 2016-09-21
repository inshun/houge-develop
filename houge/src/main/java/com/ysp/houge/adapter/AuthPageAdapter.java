package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.ui.base.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AuthPageAdapter extends FragmentStatePagerAdapter {

	private List<BaseFragment> list;

	public AuthPageAdapter(FragmentManager fm, List<BaseFragment> list) {
		super(fm);
		this.list = list;
	}

	public void setList(List<BaseFragment> list) {
		this.list = list;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}
}
