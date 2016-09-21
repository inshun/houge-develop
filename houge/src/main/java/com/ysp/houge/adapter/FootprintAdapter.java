package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.ui.base.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FootprintAdapter extends FragmentStatePagerAdapter {
	private List<BaseFragment> list;

	public FootprintAdapter(FragmentManager fm, List<BaseFragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

}
