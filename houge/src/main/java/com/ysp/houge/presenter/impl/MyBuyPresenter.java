package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.MyBuyEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.IMyBuyPresenter;
import com.ysp.houge.ui.iview.IMyBuyPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

public class MyBuyPresenter implements IMyBuyPresenter<List<MyBuyEntity>> {
	private int page = 1;
	private boolean hasDate = false;

	private IMyBuyPageView iMeBuyPageView;
	private IUserInfoModel iUserInfoModel;
	private List<MyBuyEntity> list = new ArrayList<MyBuyEntity>();

	public MyBuyPresenter(IMyBuyPageView iMeBuyPageView) {
		super();
		iUserInfoModel = new UserInfoModelImpl();
		this.iMeBuyPageView = iMeBuyPageView;
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();

		iUserInfoModel.myBuyer(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof List<?>) {
					list = (List<MyBuyEntity>) data;
					if (list.isEmpty() || list.size() < 10) {
						hasDate = false;
					} else {
						hasDate = true;
					}
					iMeBuyPageView.refreshComplete(list);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iMeBuyPageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
		page++;
		list.clear();

		iUserInfoModel.myBuyer(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof List<?>) {
					List<MyBuyEntity> list = (List<MyBuyEntity>) data;
					if (list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
					iMeBuyPageView.loadMoreComplete(list);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
                page--;
                hasDate = true;
				iMeBuyPageView.loadMoreComplete(list);
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

}
