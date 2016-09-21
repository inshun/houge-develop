package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.PictureEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.FileUploadModelImpl;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IMeInfoPresenter;
import com.ysp.houge.ui.iview.IMeInfoPageView;
import com.ysp.houge.utility.ImageReduceUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.text.TextUtils;

/**
 * @描述:我的个人信息页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日下午2:25:26
 * @version 1.0
 */
public class MeInfoPagePresenter extends BasePresenter<IMeInfoPageView> implements IMeInfoPresenter {
	/**
	 * @字段：userInfo
	 * @功能描述：登录用户的个人信息
	 * @创建人：tyn
	 * @创建时间：2015年7月18日下午3:55:59
	 */
	private UserInfoEntity userInfo;

	private FileUploadModelImpl iFileUploadModel;
	private IUserInfoModel iMeInfoModel;

	public MeInfoPagePresenter(IMeInfoPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iFileUploadModel = new FileUploadModelImpl();
		iMeInfoModel = new UserInfoModelImpl();
		// 取个人信息数据
		userInfo = MyApplication.getInstance().getUserInfo();
	}

	/** 获取个人信息 */
	@Override
	public void getMeInfo() {
		if (userInfo != null) {
			mView.updateAvatar(userInfo.avatar);
			mView.updateNickName(userInfo.nick);
			mView.updateSex(userInfo.sex);
			// 认证
			mView.updateAuth("未认证");
			mView.updateCompanyAuth("未认证");
			mView.updateStudentAuth("未认证");
			if (!TextUtils.isEmpty(userInfo.verfied)) {
				if (TextUtils.equals("1", userInfo.verfied)) {
					mView.updateStudentAuth("已认证");
				} else if (TextUtils.equals("2", userInfo.verfied)) {
					mView.updateAuth("已认证");
				} else {
					mView.updateCompanyAuth("已认证");
				}
			}
		}

	}

	/** 上传头像 */
	@Override
	public void updateAvatar(final String cropAvatarPath) {
		mView.showProgress();

		// 先上传头像到服务器
		iFileUploadModel.uploadSingleFile(0, ImageReduceUtils.compression(cropAvatarPath), "path", "avatar", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (data != null && data instanceof PictureEntity && userInfo != null) {
					// 拿到上传的图片地址，将图片地址上传服务器
					PictureEntity pictureEntity = (PictureEntity) data;
					changeAvatar(pictureEntity.getPath(), cropAvatarPath);
				} else {
					mView.showToast(R.string.upload_avatar_failed);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				mView.hideProgress();
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					mView.hideProgress();
					mView.showToast(message);
					break;
				default:
					mView.hideProgress();
					mView.showToast(R.string.upload_avatar_failed);
					break;
				}
			}
		});
	}

	private void changeAvatar(final String path, final String cropAvatarPath) {
		mView.showProgress();

		iMeInfoModel.reviseUserInfo(path, "", "", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				// 更新内存中的头像
				userInfo.avatar = HttpApi.getAbsPathUrl(path);
				MyApplication.getInstance().setUserInfo(userInfo);
				// 更新数据库
				UserInfoController.update(UserInfoController.AVATAR, path, userInfo.id);
				mView.updateAvatar("file://" + cropAvatarPath);
				mView.showToast(R.string.revise_success);
			}

			@Override
			public void onError(int errorCode, String message) {
				mView.hideProgress();
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
	public void clickAvatarLayout() {
		if (userInfo != null) {
			mView.showChoosePictureDialog();
		}
	}

	@Override
	public void clickNicknameLayout() {
		if (userInfo != null) {
			mView.jumpToNickNamePage(userInfo.nick);
		}
	}

	@Override
	public void clickSexLayout() {
		if (userInfo != null) {
			mView.jumpToSexPage(userInfo.sex);
		}
	}

	@Override
	public void clickAuthLayout(int index) {
		mView.jumpToAuth(index);
	}
}
