package com.tyn.view;

public interface IRefreshPresenter<DATA> {

	void refresh();

	void loadMore();

	boolean hasData();

}
