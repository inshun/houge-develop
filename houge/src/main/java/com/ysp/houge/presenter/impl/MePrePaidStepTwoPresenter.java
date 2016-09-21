package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.model.IFileUploadModel;
import com.ysp.houge.model.impl.FileUploadModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IMePrePaidStepTwoPresenter;
import com.ysp.houge.ui.iview.IMePrePaidStepTwoView;
import com.ysp.houge.utility.CharacterUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.text.TextUtils;

/**
 * @描述:预支工资第二步页面的presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月20日下午3:55:21
 * @version 1.0
 */
public class MePrePaidStepTwoPresenter extends BasePresenter<IMePrePaidStepTwoView>
		implements IMePrePaidStepTwoPresenter {
	private String workStudyImagePath;
	private String poorProveImagePath;
	private String studyScoreImagePath;
	private String moreImagePath;

	private IFileUploadModel iPictureUploadModel;

	public MePrePaidStepTwoPresenter(IMePrePaidStepTwoView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iPictureUploadModel = new FileUploadModelImpl();
	}

	@Override
	public void getDataFromFormalPage(String advanceMoney, String collectionAccount) {

	}

	@Override
	public void clickSubmitBtn(String number) {
		if (TextUtils.isEmpty(number)) {
			mView.showToast("请输入诚信联保人数");
			return;
		}
		if (CharacterUtil.isNumeric(number)) {
			mView.showToast("对不起，您输入的人数个人不正确");
			return;
		}
		List<String> paths = new ArrayList<String>();
		iPictureUploadModel.uploadMultiFile(paths, "", "", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					List<String> paths = (List<String>) data;

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
					mView.showToast(R.string.upload_avatar_failed);
					break;
				}
			}
		});
	}

	@Override
	public void showWhetherSubmitDialog() {

	}

	@Override
	public void sureSubmitRequest() {

	}

	@Override
	public void chooseWorkStudyImageBack(String path) {

	}

	@Override
	public void choosePoorProveImageBack(String path) {

	}

	@Override
	public void chooseStudyScoreImageBack(String path) {

	}

	@Override
	public void chooseMoreImageBack(String path) {

	}

}
