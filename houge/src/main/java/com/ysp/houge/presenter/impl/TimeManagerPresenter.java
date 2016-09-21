package com.ysp.houge.presenter.impl;

import java.util.List;

import com.google.gson.Gson;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.ITimeModel;
import com.ysp.houge.model.entity.bean.TimeEntity;
import com.ysp.houge.model.impl.TimeModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ITimeManagerPagePresenter;
import com.ysp.houge.ui.iview.ITimeManagerPageView;
import com.ysp.houge.ui.me.TimeManagerActivity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.text.TextUtils;

public class TimeManagerPresenter extends BasePresenter<ITimeManagerPageView> implements ITimeManagerPagePresenter {

	private ITimeModel iTimeModel;
	private List<TimeEntity> list;
	private String enterPage;

	public TimeManagerPresenter(ITimeManagerPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iTimeModel = new TimeModelImpl();
	}

	@Override
	public void setEnterPage(String enterPage) {
		this.enterPage = enterPage;
	}

	@Override
	public void setData(List<TimeEntity> list) {
		// TODO 根据数据初始化页面
		this.list = list;
        if (null == list || list.isEmpty())
            mView.initAllCheck(null);
        else {
            mView.initView(this.list);
        }
	}

	@Override
	public void getMySettingTime() {
		mView.showProgress();
		iTimeModel.getTime(MyApplication.LOG_STATUS_SELLER, new OnNetResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (data != null && data instanceof List<?>) {
					list = (List<TimeEntity>) data;
					mView.initView(list);
					mView.hideProgress();
				}else {
					mView.initAllCheck(null);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				mView.hideProgress();
				mView.initAllCheck(null);
			}
		});
	}

	@Override
	public void changeValue(int i, int j) {
		switch (j) {
		case 0:
			list.get(i).setMorning(
					list.get(i).getMorning() == TimeEntity.have_time ? TimeEntity.no_tinme : TimeEntity.have_time);
			break;

		case 1:
			list.get(i).setNoon(
					list.get(i).getNoon() == TimeEntity.have_time ? TimeEntity.no_tinme : TimeEntity.have_time);
			break;

		case 2:
			list.get(i).setNight(
					list.get(i).getNight() == TimeEntity.have_time ? TimeEntity.no_tinme : TimeEntity.have_time);
			break;
		}

		mView.initView(this.list);
	}

	@Override
	public void submit() {
		// 先判断，至少需要选择一个时间点
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMorning() == TimeEntity.have_time) {
				count++;
			}

			if (list.get(i).getNoon() == TimeEntity.have_time) {
				count++;
			}

			if (list.get(i).getNight() == TimeEntity.have_time) {
				count++;
			}
		}

		if (count == 0) {
			mView.showToast("请至少选择一个时间点");
			return;
		}

		if (TextUtils.equals(enterPage, TimeManagerActivity.ENTER_KEY_BUYER_SET)) {
			mView.postDate(list);
		} else {
			requestTime(new Gson().toJson(list));
		}
	}

	private void requestTime(String string) {
		mView.showProgress();
		iTimeModel.createOrUpdateTime(MyApplication.LOG_STATUS_SELLER, string, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				mView.showToast("设置成功");
                mView.postDate(null);
				mView.close();
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
	};
}
