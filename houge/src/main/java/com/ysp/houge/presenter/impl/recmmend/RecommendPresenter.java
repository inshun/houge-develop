package com.ysp.houge.presenter.impl.recmmend;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IRecommendModel;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.impl.RecommendModleImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IRecommendPresenter;
import com.ysp.houge.ui.iview.IRecommandPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 关注外层页面(不包含列表的部分)presenter层
 *
 * @ClassName: RecommendPresenter
 *
 * @author: hx
 *
 * @date: 2015年12月4日 下午2:27:25
 *
 *        版本: 1.0
 */
public class RecommendPresenter extends BasePresenter<IRecommandPageView> implements IRecommendPresenter {
	private List<WorkTypeEntity> list = new ArrayList<WorkTypeEntity>();
	private List<SortTypeEntity> sort = new ArrayList<SortTypeEntity>();
	private SortTypeEntity chooseSortType;

	private IRecommendModel iRecommendModel;

	public RecommendPresenter(IRecommandPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iRecommendModel = new RecommendModleImpl();
	}

	@Override
	public void loadListDate() {
		requestWorkType();
	}

	@Override
	public void showChangeStatusPop() {
		// TODO 显示切换身份提升
		mView.showChangeLoginStatusDialog();

	}

	@Override
	public void changeLoginStutus() {
		MyApplication.getInstance()
				.setLoginStaus(MyApplication.getInstance().getLoginStaus() == MyApplication.LOG_STATUS_BUYER
						? MyApplication.LOG_STATUS_SELLER : MyApplication.LOG_STATUS_BUYER);
		// TODO 更换显示的文本
		mView.changeLoginStatus();
		// 刷新数据
		loadListDate();
	}

	@Override
	public void spreadRecommendList() {
		// TODO 展开关注列表
		mView.showMoreRecommendPop();
	}

	@Override
	public void spreadRecommendSort() {
		// TODO 展开排序列表
		mView.showSortTypePop(chooseSortType);
	}

	@Override
	public void settingRecommend() {
		// TODO 设置偏好
		mView.jumpToRecommendSettingPage();
	}

	@Override
	public void ListPopCliclk(int index) {
		// TODO 展开的关注列表 点击事件
		mView.setIndex(index);
	}

	@Override
	public void SortPopCliclk(int position, SortTypeEntity entity) {
		// TODO 展开的排序列表 点击事件
		chooseSortType = entity;
		EventBus.getDefault().post(chooseSortType);
	}

	/** 请求关注的工种列表 */
	private void requestWorkType() {
		mView.showProgress();

		// 如果集合已经有数据了，清除数据
		if (list.size() > 0) {
			list.clear();
		}

		// 请求接口
		iRecommendModel.getWorkTypeList(new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					list.addAll((List<WorkTypeEntity>) data);
					requestSortType();
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				// TODO 网络错误
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

	/** 排序类型 */
	private void requestSortType() {
		mView.showProgress();
		iRecommendModel.getSorTypetList(new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (data != null && data instanceof List<?>) {
					List<SortTypeEntity> entities = (List<SortTypeEntity>) data;
					if (sort.size() > 0) {
						sort.clear();
					} else {
						chooseSortType = entities.get(0);
					}
					sort.addAll(entities);
					mView.loadListFinish(list, sort);
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
}
