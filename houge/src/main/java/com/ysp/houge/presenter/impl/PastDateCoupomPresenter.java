package com.ysp.houge.presenter.impl;

import java.util.List;

import com.ysp.houge.model.entity.bean.CoupomEntity;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IPastDateCoupomPresenter;
import com.ysp.houge.ui.iview.IPastDateCoupomPagerView;

public class PastDateCoupomPresenter extends BasePresenter<IPastDateCoupomPagerView> implements IPastDateCoupomPresenter<List<CoupomEntity>> {

	public PastDateCoupomPresenter(IPastDateCoupomPagerView view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initModel() {
		// TODO Auto-generated method stub
		
	}

}
