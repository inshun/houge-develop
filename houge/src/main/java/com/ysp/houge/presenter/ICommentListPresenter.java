package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;

public interface ICommentListPresenter<DATA> extends IRefreshPresenter<DATA> {
	void setOrderId(int orderId);
}
