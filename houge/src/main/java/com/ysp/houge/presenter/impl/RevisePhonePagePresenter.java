package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.model.ISettingModel;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IRevisePhonePagePresenter;
import com.ysp.houge.ui.iview.IRevisePhonePageView;
import com.ysp.houge.utility.MatcherUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * @描述:修改手机号码页面p层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午10:37:32
 * @version 1.0
 */
public class RevisePhonePagePresenter extends BasePresenter<IRevisePhonePageView> implements IRevisePhonePagePresenter {
	private String password;
	private String newTelephone;

	private ISettingModel iSettingModel;

	public RevisePhonePagePresenter(IRevisePhonePageView view) {
		super(view);

	}

	@Override
	public void initModel() {
		iSettingModel = new SettingModelImpl();

	}

	@Override
	public void requstSubmit() {
		mView.showProgress();
		// iSettingModel.changePhoneRequest(password, newTelephone,
		// new OnNetResponseCallback() {
		//
		// @Override
		// public void onSuccess(Object t) {
		// mView.hideProgress();
		// if (t != null) {
		// mView.showToast(R.string.revise_success);
		// mView.sumitReviseSuccess();
		// } else {
		// mView.showToast(R.string.revise_failed);
		// }
		//
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
		// mView.showToast(R.string.revise_failed);
		// break;
		// }
		//
		// }
		// });
	}

	@Override
	public void checkCanSubmitState(String password, String newTelephone) {
		if (TextUtils.isEmpty(password)) {
			mView.showToast(R.string.input_login_password);
			return;
		}
		if (TextUtils.isEmpty(newTelephone)) {
			mView.showToast(R.string.please_input_new_phone);
			return;
		}
		if (!MatcherUtil.checkTelephoneNumberVilid(newTelephone)) {
			mView.showToast(R.string.mobile_formart_error);
			return;
		}
		this.password = password;
		this.newTelephone = newTelephone;
		mView.showSubmitReviseDialog();
	}
}
