package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.INewPasswordPagePresenter;
import com.ysp.houge.ui.iview.INewPasswordPageView;

import android.text.TextUtils;

/**
 * @描述:修改密码页面的MVP模式中的presenter层
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月23日下午8:56:31
 * @version 1.8
 */
public class NewPasswordPagePresenter extends BasePresenter<INewPasswordPageView> implements INewPasswordPagePresenter {
	private IUserInfoModel iUserInfoModel;

	public NewPasswordPagePresenter(INewPasswordPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iUserInfoModel = new UserInfoModelImpl();
	}

	@Override
	public void revisePassword(String newPassword, String doubleInputPassword, String checkCode, String mobile) {
		if (TextUtils.isEmpty(checkCode) || TextUtils.isEmpty(mobile)) {
			mView.showToast(R.string.request_failed);
			return;
		}
		if (TextUtils.isEmpty(newPassword)) {
			mView.showToast(R.string.please_input_new_password);
			return;
		}
		if (TextUtils.isEmpty(doubleInputPassword)) {
			mView.showToast(R.string.please_re_input_new_password);
			return;
		}
		if (!newPassword.equals(doubleInputPassword)) {
			mView.showToast(R.string.password_not_agreement);
			return;
		}
		if (newPassword.length() < 8 || newPassword.length() > 16) {
			mView.showToast(R.string.password_tip);
			return;
		}
		if (doubleInputPassword.length() < 8 || doubleInputPassword.length() > 16) {
			mView.showToast(R.string.password_tip);
			return;
		}
		mView.showProgress();
		// iUserInfoModel.revisePasswordRequest(newPassword,
		// doubleInputPassword, checkCode, mobile,
		// new OnNetResponseCallback() {
		//
		// @Override
		// public void onSuccess(Object t) {
		// mView.hideProgress();
		// mView.reviseSuccess();
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
