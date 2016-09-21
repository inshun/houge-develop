package com.ysp.houge.presenter.impl;

import com.google.gson.Gson;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.ISettingModel;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ISettingPagePresenter;
import com.ysp.houge.ui.iview.ISettingPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.text.TextUtils;

/**
 * @author it_hu
 * 
 *         设置页面Presenter层
 *
 */
public class SettingPagePresenter extends BasePresenter<ISettingPageView> implements ISettingPagePresenter {
	private String initInfo;// 初始化信息

	private ISettingModel iSettingModel;

	public SettingPagePresenter(ISettingPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iSettingModel = new SettingModelImpl();
	}

	@Override
	public void getAppContacts() {
		// 获取已保存初始化信息
		initInfo = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
		if (TextUtils.isEmpty(initInfo)) {
			mView.showProgress();
		} else {
			AppInitEntity initEntity = new Gson().fromJson(initInfo, AppInitEntity.class);
			mView.showContact(initEntity.weixin, initEntity.mobile);
		}

		// 这里再次请求以刷新
		iSettingModel.appInit(new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (data != null && data instanceof AppInitEntity) {
					AppInitEntity initEntity = (AppInitEntity) data;
					mView.showContact(initEntity.weixin, initEntity.mobile);
				} else {
					mView.showToast(R.string.request_failed);
				}
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

	@Override
	public void clickCustomService() {
		if (!TextUtils.isEmpty(initInfo)) {
			mView.showCallPhoneDialog(new Gson().fromJson(initInfo, AppInitEntity.class).mobile);
		} else {
			mView.showToast("获取客服电话失败，请检查网络");
		}
	}

}
