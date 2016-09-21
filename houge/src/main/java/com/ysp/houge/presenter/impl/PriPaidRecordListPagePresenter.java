package com.ysp.houge.presenter.impl;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import com.ysp.houge.app.Constants;
import com.ysp.houge.model.IRefreshListModel;
import com.ysp.houge.model.entity.db.ItemPrePaidRecordEntity;
import com.ysp.houge.model.impl.PriPaidRecordListModelImpl;
import com.ysp.houge.presenter.IPriPaidRecordListPagePresenter;
import com.ysp.houge.ui.iview.IPriPaidRecordListPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月12日下午12:33:37
 * @version 1.0
 */
public class PriPaidRecordListPagePresenter implements
		IPriPaidRecordListPagePresenter {
	private int page;
	private IPriPaidRecordListPageView iPriPaidRecordListPageView;
	private IRefreshListModel priPaidRecordListModelImpl;

	public PriPaidRecordListPagePresenter(
			IPriPaidRecordListPageView iPriPaidRecordListPageView) {
		this.iPriPaidRecordListPageView = iPriPaidRecordListPageView;
		priPaidRecordListModelImpl = new PriPaidRecordListModelImpl();
	}

	@Override
	public void refresh() {
		setListRequest(0, Constants.REQUEST_SIZE, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					page = 0;
					iPriPaidRecordListPageView
							.refreshComplete((List<ItemPrePaidRecordEntity>) data);
				} else {
					iPriPaidRecordListPageView.refreshComplete(null);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iPriPaidRecordListPageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
		setListRequest(page + 1, Constants.REQUEST_SIZE,
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						if (data != null && data instanceof List<?>) {
							page++;
							iPriPaidRecordListPageView
									.loadMoreComplete((List<ItemPrePaidRecordEntity>) data);
						} else {
							iPriPaidRecordListPageView.loadMoreComplete(null);
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						iPriPaidRecordListPageView.loadMoreComplete(null);
					}
				});
	}

	@Override
	public boolean hasData() {
		// if (itemPartJobEntities != null
		// && itemPartJobEntities.size() < Constants.REQUEST_SIZE) {
		// return false;
		// }
		// return true;
		return page < 5;
	}

	/**
	 * @描述:网络请求列表
	 * @方法名: setListRequest
	 * @param pageSize
	 * @param size
	 * @param onNetResponseListener
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年6月30日下午7:23:29
	 * @修改人 tyn
	 * @修改时间 2015年6月30日下午7:23:29
	 * @修改备注
	 * @since
	 * @throws
	 */
	public void setListRequest(int pageSize, int size,
			OnNetResponseCallback onNetResponseListener) {
		priPaidRecordListModelImpl.onListModelRequest(pageSize,
				Constants.REQUEST_SIZE, onNetResponseListener);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ItemPrePaidRecordEntity itemPrePaidRecordEntity = (ItemPrePaidRecordEntity) parent
				.getAdapter().getItem(position);
		if (itemPrePaidRecordEntity != null) {
			iPriPaidRecordListPageView.jumpToNextPage(itemPrePaidRecordEntity);
		}
	}

}
