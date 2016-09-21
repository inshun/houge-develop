package com.ysp.houge.presenter.impl;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.INearbyModel;
import com.ysp.houge.model.IRecommendModel;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.impl.NearbyModelImpl;
import com.ysp.houge.model.impl.RecommendModleImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.INearByPresenter;
import com.ysp.houge.ui.iview.INearByPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 附近外层(不包含列表)页面Presenter层
 *
 * @ClassName: NearByPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:30:04
 * 
 *        版本: 1.0
 */
public class NearByPresenter extends BasePresenter<INearByPageView> implements INearByPresenter {
	List<WorkTypeEntity> list = new ArrayList<WorkTypeEntity>();
	List<SortTypeEntity> sort = new ArrayList<SortTypeEntity>();
	SortTypeEntity chooseSortType;
	private int viewType = INearByPresenter.VIEW_TYPE_LIST;

	private INearbyModel iNearbyModel;
	private IRecommendModel iRecommendModel;

	public NearByPresenter(INearByPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iNearbyModel = new NearbyModelImpl();
		iRecommendModel = new RecommendModleImpl();
	}

	@Override
	public void loadListDate() {
		// TODO 加载数据
		requestWorkType();
	}

	@Override
	public void showChangeStatusDialog() {
		// TODO 改变登陆身份弹窗
		mView.showChangeLoginStatusDialog();
	}

	@Override
	public void changeLoginStatus() {
		// TODO 改变登陆身份
		MyApplication.getInstance()
				.setLoginStaus(MyApplication.getInstance().getLoginStaus() == MyApplication.LOG_STATUS_BUYER
						? MyApplication.LOG_STATUS_SELLER : MyApplication.LOG_STATUS_BUYER);
		// TODO 重置显示状态
		mView.changeLoginStatus();

		loadListDate();
	}

	@Override
	public void changeMapOrList() {
		// TODO 切换地图或则列表

		viewType = (viewType == VIEW_TYPE_LIST) ? VIEW_TYPE_MAP : VIEW_TYPE_LIST;
		mView.changeMapOrList(viewType);
	}

	@Override
	public void search() {
		// TODO 搜索
		mView.jumpToSearch();
	}

	@Override
	public void spreadRecommendList() {
		// TODO 展开关注列表
		mView.showMoreRecommendPop();
	}

	@Override
	public void ListPopCliclk(int index) {
		// TODO 展开的关注列表
		mView.setIndex(index);
	}

	@Override
	public void spreadRecommendSort() {
		// TODO 展开排序列表
		mView.showSortTypePop(chooseSortType);
	}

	@Override
	public void SortPopCliclk(int position, SortTypeEntity entity) {
		// TODO 展开的排序列表 点击事件
		chooseSortType = entity;
		EventBus.getDefault().post(chooseSortType);
	}

	private void requestWorkType() {
		mView.showProgress();

		// 如果集合已经有数据了，清除数据
		if (list.size() > 0) {
			list.clear();
		}

		iNearbyModel.getWorkType(new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					list.addAll((List<WorkTypeEntity>) data);
					if (sort != null && sort.size() > 0) {
						mView.loadListFinish(list, sort);
						chooseSortType = sort.get(0);
						mView.hideProgress();
					}else {
						requestSortType();
					}
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
					sort = entities;
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
