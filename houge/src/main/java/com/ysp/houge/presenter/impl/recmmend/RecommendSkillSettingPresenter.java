package com.ysp.houge.presenter.impl.recmmend;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IGetWorkTypeModel;
import com.ysp.houge.model.ITimeModel;
import com.ysp.houge.model.entity.bean.TimeEntity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;
import com.ysp.houge.model.impl.GetWorkTypeModelImpl;
import com.ysp.houge.model.impl.TimeModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IRecommendSkillSttingPresneter;
import com.ysp.houge.ui.iview.IRecommendSkillSettingPageView;
import com.ysp.houge.ui.me.TimeManagerActivity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.os.Bundle;

/**
 * @author it_hu
 * 
 *         买家关注设置presenter层
 *
 */
public class RecommendSkillSettingPresenter extends BasePresenter<IRecommendSkillSettingPageView>
		implements IRecommendSkillSttingPresneter {

	private boolean isRecommendChange = false;
	private boolean isServiceTimeChange = false;
	private Gson gson;
	private List<TimeEntity> list;// 服务时间""
	private WorkTypeSetFinishEventBusEntity busEntity;

	private IGetWorkTypeModel iGetWorkTypeModel;
	private ITimeModel iTimeModel;

	public RecommendSkillSettingPresenter(IRecommendSkillSettingPageView view) {
		super(view);
		gson = new Gson();
		list = new ArrayList<TimeEntity>();
	}

	@Override
	public void initModel() {
		iGetWorkTypeModel = new GetWorkTypeModelImpl();
		iTimeModel = new TimeModelImpl();
	}

	@Override
	public void getServiceTime() {
		mView.showProgress();
		iTimeModel.getTime(MyApplication.LOG_STATUS_BUYER, new OnNetResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (data != null && data instanceof List<?>) {
					list = (List<TimeEntity>) data;
					mView.notifyServiceTimeDate(list);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				mView.hideProgress();
			}
		});
	}

	@Override
	public void addSkillList() {
		// TODO 能力列表
		mView.jumpToSkillList();
	}

	@Override
	public void finishAddSkillList(WorkTypeSetFinishEventBusEntity busEntity) {
		isRecommendChange = true;
		this.busEntity = busEntity;
		mView.notifyListDate(busEntity.entities);
	}

	@Override
	public void setServiceTime() {
		// 这里会传一些已经设置的时间的数据
		Bundle bundle = new Bundle();
		bundle.putString(TimeManagerActivity.ENTER_KEY_BUYER_SET, gson.toJson(list));
		mView.jumpToTimeManager(bundle);
	}

	@Override
	public void setServiceTimeFinish(String string) {
		isServiceTimeChange = true;
		Type type = new TypeToken<ArrayList<TimeEntity>>() {
		}.getType();
		list = gson.fromJson(string, type);
		mView.notifyServiceTimeDate(list);
	}

	@Override
	public void setFinish() {
		// TODO 设置完成
		if (isRecommendChange || isServiceTimeChange) {
			mView.showProgress();
			if (isRecommendChange) {
				requestRecommend(busEntity.delList, busEntity.list);
			}

			if (!isRecommendChange && isServiceTimeChange) {
				requestTime(gson.toJson(RecommendSkillSettingPresenter.this.list));
			}
		} else {
			mView.jumpToHome(false);
		}

	}

	private void requestRecommend(String delList, String list) {
		// TODO 关注提交请求
		iGetWorkTypeModel.recommendSetting(delList, list, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (isServiceTimeChange) {
					// 关注点请求成功之后请求设置的时间
					requestTime(gson.toJson(RecommendSkillSettingPresenter.this.list));
				} else {
					mView.showToast((String) data);
					mView.jumpToHome(true);
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

	private void requestTime(String string) {
		mView.showProgress();
		iTimeModel.createOrUpdateTime(MyApplication.LOG_STATUS_BUYER, string, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				mView.showToast("设置成功");
				mView.jumpToHome(true);
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
