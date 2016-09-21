package com.ysp.houge.ui.iview;


public interface IBaseRefreshListView<T> extends IBaseView {
	void refresh();

	void refreshComplete(T t);

	void loadMoreComplete(T t);
}
