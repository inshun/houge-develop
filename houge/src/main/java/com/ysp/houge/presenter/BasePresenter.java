package com.ysp.houge.presenter;

import com.ysp.houge.ui.iview.IBaseView;

public abstract class BasePresenter<V extends IBaseView> {
	public V mView;

	public BasePresenter(V view) {
		initView(view);
		initModel();
	}

	public void initView(V view) {
		this.mView = view;
	}

	public abstract void initModel();
}
