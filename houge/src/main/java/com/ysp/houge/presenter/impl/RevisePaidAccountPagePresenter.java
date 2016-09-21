package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IRevisePaidAccountPagePresenter;
import com.ysp.houge.ui.iview.IRevisePaidAccountPageView;

import android.text.TextUtils;

public class RevisePaidAccountPagePresenter extends BasePresenter<IRevisePaidAccountPageView>
		implements IRevisePaidAccountPagePresenter {
	private IUserInfoModel iMeInfoModel;

	public RevisePaidAccountPagePresenter(IRevisePaidAccountPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iMeInfoModel = new UserInfoModelImpl();
	}

	@Override
	public void clickRevise(final String paidAccount) {
		if (TextUtils.isEmpty(paidAccount)) {
			mView.showToast(R.string.please_input_alipay_or_bank_card);
			return;
		}
		mView.showProgress();
		// iMeInfoModel.revisePaidAccount(paidAccount, new
		// OnNetResponseCallback() {
		//
		// @Override
		// public void onSuccess(Object data) {
		// mView.hideProgress();
		// mView.showToast(R.string.revise_success);
		// mView.sumitReviseSuccess();
		// // EventBus.getDefault().post(
		// // new MeReviseEventBusEntity(
		// // MeReviseType.RevisePaidAccount,
		// // paidAccount));
		// }
		//
		// @Override
		// public void onError(int errorCode, String message) {
		// switch (errorCode) {
		// case ResponseCode.TIP_ERROR:
		// mView.hideProgress();
		// mView.showToast(message);
		// break;
		// default:
		// mView.hideProgress();
		// mView.showToast(R.string.request_failed);
		// break;
		// }
		// }
		// });
	}
}
