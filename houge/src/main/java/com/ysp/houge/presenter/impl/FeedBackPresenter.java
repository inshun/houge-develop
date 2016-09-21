package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.model.ISettingModel;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IFeedBackPresenter;
import com.ysp.houge.ui.iview.IFeedBackPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * @描述:意见反馈页面p层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午10:37:32
 * @version 1.0
 */
public class FeedBackPresenter extends BasePresenter<IFeedBackPageView> implements IFeedBackPresenter {
	private String content;
	private ISettingModel iFeedBackModel;

	public FeedBackPresenter(IFeedBackPageView view) {
		super(view);

	}

	@Override
	public void initModel() {
		iFeedBackModel = new SettingModelImpl();

	}

	@Override
	public void requstSubmitFeedBack() {
		mView.showProgress();
		iFeedBackModel.feedBackRequest(content, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				mView.hideProgress();
				mView.showToast(R.string.feedback_success);
				mView.submitFeedBackSuccess();
				content = "";
				mView.close();
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
					mView.showToast(R.string.feedback_failed);
					break;
				}

			}
		});

	}

	/** 检查是否可以反馈 */
	@Override
	public void checkCanFeedbackState(String content) {
		if (TextUtils.isEmpty(content)) {
			mView.showToast(R.string.please_input_feedback);
			return;
		}

		// 这里可以做一些关键字隔离

		mView.showSubmitFeedbackDialog();
		this.content = content;
	}
}
