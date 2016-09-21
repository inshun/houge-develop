package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.MySellEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.IMySellPresenter;
import com.ysp.houge.ui.iview.IMySellPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * 描述： 卖出的Presenter层
 *
 * @ClassName: MySellPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月17日 下午3:59:01
 * 
 *        版本: 1.0
 */
public class MySellPresenter implements IMySellPresenter<List<MySellEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	private IUserInfoModel iUserInfoModel;
	private IMySellPageView iMySellPageView;
	private List<MySellEntity> list = new ArrayList<MySellEntity>();

	public MySellPresenter(IMySellPageView iMySellPageView) {
		super();
		iUserInfoModel = new UserInfoModelImpl();
		this.iMySellPageView = iMySellPageView;
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();

		iUserInfoModel.meSeller(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof List<?>) {
					list = (List<MySellEntity>) data;
					if (list.isEmpty() || list.size() < 10) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iMySellPageView.refreshComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				iMySellPageView.refreshComplete(list);
			}
		});
	}

	@Override
	public void loadMore() {
		page++;
		list.clear();

		iUserInfoModel.meSeller(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof List<?>) {
					list = (List<MySellEntity>) data;
					if (list.isEmpty() || list.size() < 10) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iMySellPageView.loadMoreComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				page--;
				iMySellPageView.loadMoreComplete(list);
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void serviceClick() {
		iMySellPageView.showCallPhonePop();
	}

}
