package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IReviseEducationPagePresenter;
import com.ysp.houge.ui.iview.IReviseEducationPageView;

public class ReviseEdcationalPagePresenter extends BasePresenter<IReviseEducationPageView>
		implements IReviseEducationPagePresenter {
	private IUserInfoModel iMeInfoModel;

	public ReviseEdcationalPagePresenter(IReviseEducationPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iMeInfoModel = new UserInfoModelImpl();
	}

	@Override
	public void clickRevise(final int educational) {
		if (educational == -1) {
			mView.showToast(R.string.please_choose_educational);
			return;
		}
		mView.showProgress();
//		iMeInfoModel.reviseDegrees(educational, new OnNetResponseCallback() {
//
//			@Override
//			public void onSuccess(Object data) {
//				mView.hideProgress();
//				mView.showToast(R.string.revise_success);
//				mView.sumitReviseSuccess();
//				// // 通知上一页面，修改了学历
//				// EventBus.getDefault().post(
//				// new MeReviseEventBusEntity(
//				// MeReviseType.ReviseEducation, educational));
//			}
//
//			@Override
//			public void onError(int errorCode, String message) {
//				switch (errorCode) {
//				case ResponseCode.TIP_ERROR:
//					mView.hideProgress();
//					mView.showToast(message);
//					break;
//				default:
//					mView.hideProgress();
//					mView.showToast(R.string.request_failed);
//					break;
//				}
//			}
//		});
	}
}
