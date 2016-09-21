package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.model.ISettingModel;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IChangePasswordPresenter;
import com.ysp.houge.ui.iview.IChangePasswordPageView;
import com.ysp.houge.utility.RsaUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * @描述:通过老密码修改密码页面p层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午10:37:32
 * @version 1.0
 */
public class ChangePasswordPresenter extends BasePresenter<IChangePasswordPageView>
		implements IChangePasswordPresenter {
	private String oldPassword;
	private String newPassword;
	private String reNewPassword;
	private ISettingModel iSettingModel;

	public ChangePasswordPresenter(IChangePasswordPageView view) {
		super(view);

	}

	@Override
	public void initModel() {
		iSettingModel = new SettingModelImpl();
	}

	@Override
	public void requstSubmit() {
		mView.showProgress();
		iSettingModel.changePasswordRequest(RsaUtils.encryptByPublic(oldPassword),RsaUtils.encryptByPublic(newPassword) ,RsaUtils.encryptByPublic(reNewPassword) , new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				// 更改数据库中的密码
				UserInfoController.update(UserInfoController.PASSWORD, newPassword,
						MyApplication.getInstance().getCurrentUid());
				// 更新配置文件中的密码
				UserInfoEntity info = MyApplication.getInstance().getUserInfo();
				info.password = newPassword;
				MyApplication.getInstance().setUserInfo(info);
				mView.hideProgress();
				mView.showToast(R.string.revise_success);
				mView.sumitReviseSuccess();
			}

			@Override
			public void onError(int errorCode, String message) {
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					mView.hideProgress();
					mView.showToast(message);
					break;
				default:
					mView.hideProgress();
					mView.showToast(R.string.revise_failed);
					break;
				}

			}
		});
	}

	@Override
	public void checkCanSubmitState(String oldPassword, String newPassword, String reNewPwd) {
		if (TextUtils.isEmpty(oldPassword)) {
			mView.showToast(R.string.please_input_login_password);
			return;
		}

		if (oldPassword.length() < 8) {
			mView.showToast("老密码在8到16位数");
			return;
		}

		if (TextUtils.isEmpty(newPassword)) {
			mView.showToast(R.string.please_input_new_password);
			return;
		}

		if (newPassword.length() < 8) {
			mView.showToast(R.string.password_need_to_lenth);
			return;
		}

		if (TextUtils.isEmpty(reNewPwd)) {
			mView.showToast("请确认新密码");
			return;
		}


        if (!TextUtils.equals(newPassword,reNewPwd)){
            mView.showToast("新密码不一致");
            return;
        }

        if (TextUtils.equals(oldPassword,newPassword)){
            mView.showToast("新旧密码不能一样");
            return;
        }

		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		reNewPassword = reNewPwd;
		mView.showSubmitReviseDialog();
	}
}
