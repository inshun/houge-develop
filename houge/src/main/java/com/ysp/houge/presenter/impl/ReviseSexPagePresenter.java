package com.ysp.houge.presenter.impl;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.entity.eventbus.MeReviseEventBusEntity;
import com.ysp.houge.model.entity.eventbus.MeReviseEventBusEntity.MeReviseType;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IReviseSexPagePresenter;
import com.ysp.houge.ui.iview.IReviseSexPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

public class ReviseSexPagePresenter extends BasePresenter<IReviseSexPageView> implements IReviseSexPagePresenter {
	private IUserInfoModel iMeInfoModel;

	public ReviseSexPagePresenter(IReviseSexPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iMeInfoModel = new UserInfoModelImpl();
	}

	@Override
	public void clickRevise(final int sex) {
		mView.showProgress();
		iMeInfoModel.reviseUserInfo("", "", String.valueOf(sex), new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				mView.showToast(R.string.revise_success);
				// 更新数据库的性别
				UserInfoController.update(UserInfoController.SEX, sex, MyApplication.getInstance().getCurrentUid());
				// 更新内存的性别
				UserInfoEntity info = MyApplication.getInstance().getUserInfo();
				info.sex = sex;
				MyApplication.getInstance().setUserInfo(info);
				EventBus.getDefault().post(new MeReviseEventBusEntity(MeReviseType.ReviseSex, sex));
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
					mView.showToast(R.string.request_failed);
					break;
				}
			}
		});
	}
}
