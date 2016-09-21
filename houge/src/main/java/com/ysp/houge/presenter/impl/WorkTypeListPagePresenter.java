package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ysp.houge.R;
import com.ysp.houge.model.IGetWorkTypeModel;
import com.ysp.houge.model.entity.bean.SkillEntity;
import com.ysp.houge.model.entity.bean.TimeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;
import com.ysp.houge.model.impl.GetWorkTypeModelImpl;
import com.ysp.houge.model.impl.TimeModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IWorkTypeListPagePresenter;
import com.ysp.houge.ui.iview.IWorkTypeListPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述:工种选择页面Presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 *
 * @author tyn
 * @date 2015年8月30日下午6:19:25
 * @version 1.0
 */
public class WorkTypeListPagePresenter extends BasePresenter<IWorkTypeListPageView>
		implements IWorkTypeListPagePresenter {
	private List<WorkTypeEntity> entities;
	private List<String> is_watch;
	private String recommendStr;

	private IGetWorkTypeModel iGetWorkTypeModel;
	private WorkTypeSetFinishEventBusEntity busEntity;
	private TimeModelImpl iTimeModel;
	private Gson gson;
	private List<TimeEntity> list;
	private String enterPage;
	private boolean isSettingTime = false;
	private boolean isSettingAddress = false;

	/**
	 * @描述 构造方法
	 * @param view
	 *            当前的View对象
	 * @param entities
	 *            选中的工种列表
	 */
	public WorkTypeListPagePresenter(IWorkTypeListPageView view) {
		super(view);
		is_watch = new ArrayList<String>();
	}

	@Override
	public void initModel() {
		iTimeModel = new TimeModelImpl();
		iGetWorkTypeModel = new GetWorkTypeModelImpl();
	}

	@Override
	public void setRecommendStr(String str) {
		this.recommendStr = str;
	}

	@Override
	public void getWorkTypeList(final int buy) {
		mView.showProgress();
		iGetWorkTypeModel.getWorkTypeList(new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object object) {
				mView.hideProgress();
				if (object != null && object instanceof List<?>) {
					entities = (List<WorkTypeEntity>) object;

					if(buy == 2 || buy == 3){
						for (int i = 0; i < entities.size(); i++) {
						is_watch.add(entities.get(i).getIs_watch());
						mView.setList(entities);
						}
					}else{
						for (int i = 0; i < entities.size(); i++) {
							// 先根据传过来的，改变选中状态
							if (recommendStr.indexOf(entities.get(i).getName()) > -1) {
								entities.get(i).setIs_watch("yes");
							} else {
								entities.get(i).setIs_watch("no");
							}
							is_watch.add(entities.get(i).getIs_watch());
							mView.setList(entities);
						}
					}
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
	public void submit(int buy) {
		// TODO 提交
		// 如果一个都未选中，需要提示
		int count = 0;
		 busEntity = new WorkTypeSetFinishEventBusEntity();
		busEntity.entities = entities;
		StringBuilder sbList = new StringBuilder();
		StringBuilder sbDelList = new StringBuilder();
		for (int i = 0; i < entities.size(); i++) {
			// 如果两个的选中状态有差异
			if (!TextUtils.equals(entities.get(i).getIs_watch(), is_watch.get(i))) {
				if (TextUtils.equals(entities.get(i).getIs_watch(), "yes")) {
					sbList.append(entities.get(i).getId());
					sbList.append(",");
				} else {
					sbDelList.append(entities.get(i).getId());
					sbDelList.append(",");
				}
			}

			if (TextUtils.equals(entities.get(i).getIs_watch(), "yes")) {
				count++;
			}
		}

		if (!(count > 0)) {
			mView.showToast("请至少添加一个");
			return;
		}

		if (sbList.lastIndexOf(",") > 0 && sbList.lastIndexOf(",") == sbList.length() - 1) {
			sbList.deleteCharAt(sbList.length() - 1);
		}

		if (sbDelList.lastIndexOf(",") > 0 && sbDelList.lastIndexOf(",") == sbDelList.length() - 1) {
			sbDelList.deleteCharAt(sbDelList.length() - 1);
		}

		busEntity.delList = sbDelList.toString();
		busEntity.list = sbList.toString();

		if(buy == 2 || buy == 3){
			requestRecommend(buy);
		}
		mView.submit(busEntity);
	}

	/**
	 * 关注点请求
	 */
	private void requestRecommend(final int buy) {
		mView.showProgress();
		iGetWorkTypeModel.recommendSetting(busEntity.delList, busEntity.list, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {

				mView.showToast("设置完成");
				if(buy == 2){
					mView.jumpToSkillPulishActivity();
				}else if(buy == 3){
					mView.jumpToNeedPulishActivity();
				}

			}

			@Override
			public void onError(int errorCode, String message) {
				//失败了不接着往下设置
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

	public void itemClick(int position) {
		// TODO item 点击后的操作
		// 更改选中的值
		String is_watch = entities.get(position).getIs_watch();
		entities.get(position).setIs_watch(TextUtils.equals(is_watch, "yes") ? "no" : "yes");

		mView.setList(entities);
	}
}
