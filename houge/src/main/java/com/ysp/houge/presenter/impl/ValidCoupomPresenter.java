package com.ysp.houge.presenter.impl;

import java.util.List;

import com.ysp.houge.model.entity.bean.CoupomEntity;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IValidCoupomPresenter;
import com.ysp.houge.ui.iview.IValidCoupomPagerView;

import android.view.View;
import android.widget.AdapterView;

public class ValidCoupomPresenter extends BasePresenter<IValidCoupomPagerView> implements IValidCoupomPresenter<List<CoupomEntity>> {

	public ValidCoupomPresenter(IValidCoupomPagerView view) {
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
	public void clickListItem(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initModel() {
		// TODO Auto-generated method stub
		
	}

}
