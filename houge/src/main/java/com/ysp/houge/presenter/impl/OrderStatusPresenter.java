package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.presenter.IOrderStatusPresenter;
import com.ysp.houge.ui.iview.IOrderStatusPageView;

public class OrderStatusPresenter implements IOrderStatusPresenter<List<String>> {
	private List<String> list;
	private IOrderStatusPageView iOrderStatusPageView;

	public OrderStatusPresenter(IOrderStatusPageView iOrderStatusPageView) {
		super();
		this.iOrderStatusPageView = iOrderStatusPageView;
	}

	@Override
	public void refresh() {
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("");
		}
		iOrderStatusPageView.refreshComplete(list);
	}

	@Override
	public void loadMore() {

	}

	@Override
	public boolean hasData() {
		return false;
	}
}
