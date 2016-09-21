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
import com.ysp.houge.presenter.IReviseNickNamePagePresenter;
import com.ysp.houge.ui.iview.IReviseNicknamePageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.text.TextUtils;

/**
 * @描述:修改昵称p层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日上午10:29:31
 * @version 1.0
 */
public class ReviseNickNamePagePresenter extends BasePresenter<IReviseNicknamePageView>
		implements IReviseNickNamePagePresenter {
	private IUserInfoModel iMeInfoModel;

	public ReviseNickNamePagePresenter(IReviseNicknamePageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iMeInfoModel = new UserInfoModelImpl();
	}

	@Override
	public void clickRevise(final String nickname) {
		if (TextUtils.isEmpty(nickname)) {
			mView.showToast(R.string.please_input_nickname);
			return;
		}

		if (nickname.length() < 2) {
			mView.showToast("昵称在2~8个字符之间");
			return;
		}

		mView.showProgress();
		iMeInfoModel.reviseUserInfo("", nickname, "", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				mView.showToast(R.string.revise_success);
				// 更新数据库的昵称
				UserInfoController.update(UserInfoController.NICK, nickname,
						MyApplication.getInstance().getUserInfo().id);
				// 更新配置文件的昵称
				UserInfoEntity info = MyApplication.getInstance().getUserInfo();
				info.nick = nickname;
				MyApplication.getInstance().setUserInfo(info);
				// 发送更改成功的消息
				EventBus.getDefault().post(new MeReviseEventBusEntity(MeReviseType.ReviseNickName, nickname));
				// finish此页面
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

}
