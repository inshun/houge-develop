package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.model.IRegisterAndLoginModel;
import com.ysp.houge.model.impl.RegisterAndLoginModel;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IFindPwdPresenter;
import com.ysp.houge.ui.iview.IFindPwdPageView;
import com.ysp.houge.utility.MatcherUtil;
import com.ysp.houge.utility.RsaUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * @描述: 找回密码Presenter层
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月23日下午8:56:31
 * @version 1.0
 */
public class FindPwdPresenter extends BasePresenter<IFindPwdPageView> implements IFindPwdPresenter {
	private IRegisterAndLoginModel iRegisterAndLoginModel;

	public FindPwdPresenter(IFindPwdPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iRegisterAndLoginModel = new RegisterAndLoginModel();
	}

	@Override
	public void getCode(String phoneNum, int codeType) {
		if (TextUtils.isEmpty(phoneNum)) {
			mView.showToast(R.string.please_input_telephone);
			return;
		}
		if (!MatcherUtil.checkTelephoneNumberVilid(phoneNum)) {
			mView.showToast(R.string.mobile_formart_error);
			return;
		}

        //验证完成后开始发送密码
        iRegisterAndLoginModel.getCodeRequest(phoneNum, codeType, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
                //告知用户短信发送成功
				mView.showToast("发送成功");
                //开启发送短信倒计时
				mView.startTimeCount(60);
			}

			@Override
			public void onError(int errorCode, String message) {
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					mView.showToast(message);
					break;
				default:
					mView.showToast(R.string.request_failed);
					break;
				}
			}
		});
	}

	@Override
	public void finishFindPassword(String phoneNum, String code, String newPassword) {
        //检查手机号
		if (TextUtils.isEmpty(phoneNum)) {
			mView.showToast(R.string.please_input_telephone);
			return;
		}

        //检查手机号码格式
		if (!MatcherUtil.checkTelephoneNumberVilid(phoneNum)) {
			mView.showToast(R.string.mobile_formart_error);
			return;
		}

        //检查验证码
		if (TextUtils.isEmpty(code)) {
			mView.showToast(R.string.please_input_checkcode);
			return;
		}

        //检查验证个数
		if (code.length() != 4) {
			mView.showToast("验证码是四位数的数字");
			return;
		}

        //检查新密码
		if (TextUtils.isEmpty(newPassword)) {
			mView.showToast(R.string.please_input_new_password);
			return;
		}

        //检查新密码长度（最大长度已经在布局中限制）
		if (newPassword.length() < 8) {
			mView.showToast(R.string.password_need_to_lenth);
			return;
		}

        //检查完成后进行修改密码网络请求
		findPwdRequest(phoneNum, code, newPassword);
	}

	private void findPwdRequest(final String phoneNum, String code, String newPassword) {
		mView.showProgress();
		iRegisterAndLoginModel.findPwdRequest(phoneNum, code, RsaUtils.encryptByPublic(newPassword), new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				mView.showToast(R.string.revise_success);
                //跳转登录页面
				mView.jumpToLogin(phoneNum);
			}

			@Override
			public void onError(int errorCode, String message) {
				mView.hideProgress();
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					mView.showToast(message);
					break;
				default:
					mView.showToast(R.string.request_failed);
					break;
				}
			}
		});
	}
}
