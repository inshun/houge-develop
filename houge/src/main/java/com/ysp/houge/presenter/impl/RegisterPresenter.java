package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.model.IRegisterAndLoginModel;
import com.ysp.houge.model.impl.RegisterAndLoginModel;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IRegisterPresenter;
import com.ysp.houge.ui.iview.IRegisterPageView;
import com.ysp.houge.utility.MatcherUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.os.Bundle;
import android.text.TextUtils;

/**
 * @描述: 注册页面Presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月17日下午8:16:21
 * @version 1.0
 */
public class RegisterPresenter extends BasePresenter<IRegisterPageView> implements IRegisterPresenter {

	private IRegisterAndLoginModel iRegisterAndLoginModel;

	public RegisterPresenter(IRegisterPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
        iRegisterAndLoginModel = new RegisterAndLoginModel();
	}

	@Override
	public void getCode(String phoneNum) {
        //检查手机号码
		if (TextUtils.isEmpty(phoneNum)) {
			mView.showToast(R.string.set_mobile);
			return;
		}

		// 检查手机号格式
		if (!MatcherUtil.checkTelephoneNumberVilid(phoneNum)) {
            mView.showToast(R.string.mobile_formart_error);
			return;
		}

		codeRequest(phoneNum, IRegisterAndLoginModel.CODE_TYPE_CODE);
	}

	@Override
	public void getVoiceCode(String phoneNum) {
        //检查手机号码
		if (TextUtils.isEmpty(phoneNum)) {
            mView.showToast(R.string.set_mobile);
			return;
		}

		// 检查手机号格式
		if (!MatcherUtil.checkTelephoneNumberVilid(phoneNum)) {
            mView.showToast(R.string.mobile_formart_error);
			return;
		}

		codeRequest(phoneNum, IRegisterAndLoginModel.CODE_TYPE_VOICE_CODE);
	}

	@Override
	public void nextStup(String phoneNum, String code, String pwd) {
        //检查手机号码
		if (TextUtils.isEmpty(phoneNum)) {
            mView.showToast(R.string.set_mobile);
			return;
		}

		// 检查手机号格式
		if (!MatcherUtil.checkTelephoneNumberVilid(phoneNum)) {
            mView.showToast(R.string.mobile_formart_error);
			return;
		}

        //检查验证码
		if (TextUtils.isEmpty(code)) {
            mView.showToast(R.string.please_input_checkcode);
			return;
		}

        //检查验证码长度
		if (code.length() != 4) {
            mView.showToast(R.string.sms_code_is_four_num);
			return;
		}

        //检查密码
		if (TextUtils.isEmpty(pwd)) {
            mView.showToast(R.string.please_input_password);
			return;
		}
		
		//密码长度必须大于8位数(后期有更多密码限制，诸如不能纯数字等，可以再次补足)
		if (pwd.length() < 8) {
            mView.showToast(R.string.password_need_to_lenth);
			return;
		}

        //经过以上步骤，则证明是一个完整的注册信息。将注册信息参数传递给下一个页面
		Bundle bundle = new Bundle();
		bundle.putString(PerfectRegisterInfoPresenter.KEY_PHONE_NUM, phoneNum);
		bundle.putString(PerfectRegisterInfoPresenter.KEY_CODE, code);
		bundle.putString(PerfectRegisterInfoPresenter.KEY_PASSWORD, pwd);
        mView.jumpToWriteInfo(bundle);
	}

	@Override
	public void codeRequest(String phoneNum, int codeType) {
		// TODO 短信验证码请求
        mView.showProgress(true,"短信发送中");
        iRegisterAndLoginModel.getCodeRequest(phoneNum, codeType, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
				mView.hideProgress();
                //提示短信发送成功
                mView.showToast("发送成功");
                //View层短信发送倒计时启动
                mView.sendCodeCountDown(60);
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
