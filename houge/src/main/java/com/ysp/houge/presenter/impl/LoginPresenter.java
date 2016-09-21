package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.model.IPushInfoModel;
import com.ysp.houge.model.IRegisterAndLoginModel;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.PushInfoModelImpl;
import com.ysp.houge.model.impl.RegisterAndLoginModel;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ILoginPresenter;
import com.ysp.houge.ui.iview.ILoginPageView;
import com.ysp.houge.utility.MatcherUtil;
import com.ysp.houge.utility.RsaUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * 描述： 登录页面Presenter层
 *
 * @ClassName: LoginPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月23日 下午11:22:06
 * 
 *        版本: 1.0
 */
public class LoginPresenter extends BasePresenter<ILoginPageView> implements ILoginPresenter {
	private IPushInfoModel iPushInfoModel;
	private IRegisterAndLoginModel iRegisterAndLoginModel;

	public LoginPresenter(ILoginPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iPushInfoModel = new PushInfoModelImpl();
		iRegisterAndLoginModel = new RegisterAndLoginModel();
	}

    @Override
    public void login(String mobile, String password) {
        //检查手机号
		if (TextUtils.isEmpty(mobile)) {
			mView.showToast(R.string.set_mobile);
			return;
		}

        //检查手机号码格式
		if (!MatcherUtil.checkTelephoneNumberVilid(mobile)) {
			mView.showToast(R.string.mobile_formart_error);
			return;
		}

        //检查密码
		if (TextUtils.isEmpty(password)) {
			mView.showToast(R.string.set_password);
			return;
		}

		 //检查密码长度
		if (password.length() < 8) {
			mView.showToast(R.string.login_error_by_lenth);
			return;
		}

        //检查完成之后进行登录网络请求
        loginRequest(mobile,password);
    }

    /** 登录请求 */
	public void loginRequest(String mobile, final String password) {
		mView.showProgress("登录中");
		iRegisterAndLoginModel.login(mobile, RsaUtils.encryptByPublic(password), new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				mView.hideProgress();
				if (t != null && t instanceof UserInfoEntity) {
					UserInfoEntity userInfo = (UserInfoEntity) t;
					// 如果没有返回token提示获取失败
					if (TextUtils.isEmpty(userInfo.token)) {
						mView.showToast(R.string.request_token_failed);
					} else {
						// 保存token
						MyApplication.getInstance().getPreferenceUtils().setToken(userInfo.token);
						// 保存登陆的用户对象
						MyApplication.getInstance().getPreferenceUtils().setId(userInfo.id);
						// 将用户登陆数据保存到数据库库
						UserInfoController.createOrUpdate(userInfo);

						// 发送推送统计后不管成功失败进入主页面
                        pushInfoRequest();
						mView.junpToHome();
					}
				} else {
					mView.showToast(R.string.request_failed);
				}
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
					mView.showToast(R.string.request_failed);
					break;
				}
			}
		});
	}

	/** 推送信息收集请求 */
	private void pushInfoRequest() {
		iPushInfoModel.setPushInfo(0, new OnNetResponseCallback() {
			@Override
			public void onSuccess(Object data) {
				// 将用户信息传给主页面
				mView.junpToHome();
			}
			@Override
			public void onError(int errorCode, String message) {
				// 将好友信息传给主页面
				mView.junpToHome();
			}
		});
	}
}
