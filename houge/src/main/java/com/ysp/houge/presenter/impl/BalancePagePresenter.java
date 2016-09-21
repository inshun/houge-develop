package com.ysp.houge.presenter.impl;

import java.text.NumberFormat;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IBalanceModel;
import com.ysp.houge.model.entity.bean.BalanceEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.BalanceModelImpl;
import com.ysp.houge.presenter.IBalancePagePresenter;
import com.ysp.houge.ui.iview.IBalancePageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

public class BalancePagePresenter implements IBalancePagePresenter<List<BalanceEntity>> {
	private UserInfoEntity info;
	private List<BalanceEntity> list;

	private IBalancePageView iBalancePageView;
	private IBalanceModel iBalanceModel;

	public BalancePagePresenter(IBalancePageView iBalancePageView) {
		super();
		this.iBalancePageView = iBalancePageView;
		iBalanceModel = new BalanceModelImpl();
	}

	@Override
	public void refresh() {
		iBalancePageView.showProgress();
		iBalanceModel.getBalanceList(new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				iBalancePageView.hideProgress();
				if (data != null && data instanceof List<?>) {
					BalancePagePresenter.this.list = (List<BalanceEntity>) data;
					iBalancePageView.refreshComplete(list);
				} else {
					iBalancePageView.refreshComplete(null);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iBalancePageView.hideProgress();
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					iBalancePageView.showToast(message);
					break;
				default:
					iBalancePageView.showToast(R.string.request_failed);
					break;
				}
				iBalancePageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
	}

	@Override
	public boolean hasData() {
		return false;
	}

	@Override
	public void loadBalance() {
		info = MyApplication.getInstance().getUserInfo();
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		String balance = format.format((info.balance != 0) ? info.balance : 0);
		iBalancePageView.showBalance(balance);
	}

	@Override
	public void clickWithdrawDeposit() {
		iBalancePageView.jumpToWithdrawDeposit();
	}

	@Override
	public void clickTopUp() {
		iBalancePageView.jumpToTopUp();
	}

	@Override
	public void onItemClick(int position) {

	}

}
