package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IPriPaidStepOnePagePresenter;
import com.ysp.houge.ui.iview.IPriPaidStepOnePageView;

public class PriPaidStepOnePagePresenter extends
		BasePresenter<IPriPaidStepOnePageView> implements
		IPriPaidStepOnePagePresenter {

	public PriPaidStepOnePagePresenter(IPriPaidStepOnePageView view) {
		super(view);

	}

	@Override
	public void initModel() {
	}

	@Override
	public void getPriAuthState() {
		// 如果认证状态不是已认证，弹出需要去认证的对话框
//		if (MyApplication.getInstance().getUserInfo().authStatus != 1) {
//			mView.showNeedAuthDialog();
//		}
	}

	@Override
	public void clickNextBtn(final String advanceMoney,
			final String collectionAccount) {
		if (TextUtils.isEmpty(advanceMoney)) {
			mView.showToast(R.string.set_advance_money);
			return;
		}
		if (advanceMoney.length() > 5) {
			mView.showToast(R.string.advance_salary_highest);
			return;
		}
		if (TextUtils.isEmpty(collectionAccount)) {
			mView.showToast(R.string.set_collection_account);
			return;
		}
		mView.jumpToNextPage(advanceMoney, collectionAccount);
	}

}
