package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.IUserFootprintPresenter;
import com.ysp.houge.ui.iview.IUserFootprintPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @author it_huang
 * 
 *         用户足迹Presenter层
 *
 */
public class UserFootprintPresenter implements IUserFootprintPresenter<List<UserInfoEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	private IUserInfoModel iUserInfoModel;
	private IUserFootprintPageView iUserFootprintPageView;
	private List<UserInfoEntity> list = new ArrayList<UserInfoEntity>();

	public UserFootprintPresenter(IUserFootprintPageView iUserFootprintPageView) {
		super();
		this.iUserFootprintPageView = iUserFootprintPageView;
		iUserInfoModel = new UserInfoModelImpl();
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();

		iUserInfoModel.myFootprintUser(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					list = (List<UserInfoEntity>) data;
					if (null == list || list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iUserFootprintPageView.refreshComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				iUserFootprintPageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
		page++;

		iUserInfoModel.myFootprintUser(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					list = (List<UserInfoEntity>) data;
					if (null == list || list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iUserFootprintPageView.loadMoreComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				iUserFootprintPageView.loadMoreComplete(list);
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}
}
