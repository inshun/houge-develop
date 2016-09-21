/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.tyn.view.listviewhelper;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.tyn.utils.NetworkUtils;
import com.tyn.view.IRefreshPresenter;
import com.tyn.view.listviewhelper.ILoadViewFactory.ILoadMoreView;
import com.tyn.view.listviewhelper.ILoadViewFactory.ILoadView;
import com.tyn.view.listviewhelper.imp.DeFaultLoadViewFactory;

/**
 * <h1>下拉刷新，上滑加载更多的控件的辅助类</h1><br>
 * <br>
 * 刷新，加载更多规则<br>
 * 当用户下拉刷新时，会取消掉当前的刷新，以及加载更多的任务<br>
 * 当用户加载更多的时候，如果有已经正在刷新或加载更多是不会再执行加载更多的操作。<br>
 * <br>
 * 要添加 android.permission.ACCESS_NETWORK_STATE 权限，这个用来检测是否有网络
 * 
 * @author LuckyJayce
 * 
 * @param <DATA>
 */
public class ListViewHelper<DATA> {
	private final String TAG = getClass().getSimpleName();

	public static ILoadViewFactory loadViewFactory = new DeFaultLoadViewFactory();
	public boolean isLoading = false;
	DATA dataBack;
	boolean flag = false;
	private IDataAdapter<DATA> dataAdapter;
	private PullToRefreshAdapterViewBase<? extends ListView> pullToRefreshPinnedHeaderListView;
	private IRefreshPresenter<DATA> refreshPresenter;
	private ListView mListView;
	private Context context;
	private OnStateChangeListener<DATA> onStateChangeListener;
	private long loadDataTime = -1;
	/**
	 * 是否还有更多数据。如果服务器返回的数据为空的话，就说明没有更多数据了，也就没必要自动加载更多数据
	 */
	private boolean hasMoreData = true;
	private ILoadView mLoadView;
	private ILoadMoreView mLoadMoreView;
	private boolean autoLoadMore = true;
	private OnClickListener onClickLoadMoreListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			loadMore();
		}
	};
	private OnClickListener onClickRefresListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			refresh();
		}
	};
	/**
	 * 滚动到底部自动加载更多数据
	 */
	private OnScrollListener onScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView listView, int scrollState) {
			if (autoLoadMore) {
				if (hasMoreData) {
					if (!pullToRefreshPinnedHeaderListView.isRefreshing()) {// 如果不是刷新状态

						if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
								&& listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行

							// 如果网络可以用
							if (NetworkUtils.hasNetwork(context)) {
								loadMore();
							} else {
								if (!isLoading()) {
									mLoadMoreView.showFail();
								}
							}
						}
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		}
	};
	/**
	 * 针对于电视 选择到了底部项的时候自动加载更多数据
	 */
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> listView, View view, int position, long id) {
			if (autoLoadMore) {
				if (hasMoreData) {
					if (listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行
						// 如果网络可以用
						if (NetworkUtils.hasNetwork(context)) {
							loadMore();
						}
					}
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	public ListViewHelper(PullToRefreshAdapterViewBase<? extends ListView> pullToRefreshAdapterViewBase) {
		this(pullToRefreshAdapterViewBase, loadViewFactory.madeLoadView(), loadViewFactory.madeLoadMoreView());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ListViewHelper(PullToRefreshAdapterViewBase<? extends ListView> pullToRefreshAdapterViewBase,
			ILoadView loadView, ILoadMoreView loadMoreView) {
		super();
		this.context = pullToRefreshAdapterViewBase.getContext().getApplicationContext();
		this.autoLoadMore = true;
		this.pullToRefreshPinnedHeaderListView = pullToRefreshAdapterViewBase;
		mListView = pullToRefreshPinnedHeaderListView.getRefreshableView();
		mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		mListView.setCacheColorHint(Color.TRANSPARENT);
		pullToRefreshPinnedHeaderListView.setOnRefreshListener(new OnRefreshListener211());
		mListView.setOnScrollListener(onScrollListener);
		mListView.setOnItemSelectedListener(onItemSelectedListener);
		pullToRefreshPinnedHeaderListView.setMode(Mode.PULL_FROM_START);
		mLoadView = loadView;
		mLoadView.init(mListView, onClickRefresListener);

		mLoadMoreView = loadMoreView;
		mLoadMoreView.init(mListView, onClickLoadMoreListener);
	}

	/**
	 * 设置LoadView的factory，用于创建使用者自定义的加载失败，加载中，加载更多等布局
	 *
	 * @param fractory
	 */
	public static void setLoadViewFractory(ILoadViewFactory fractory) {
		loadViewFactory = fractory;
	}

	/**
	 * @描述:如果有缓存数据时的页面首次刷新 @方法名: refreshWithCache @返回类型 void @创建人 tyn @创建时间
	 *                     2015年7月21日下午7:50:01 @修改人 tyn @修改时间
	 *                     2015年7月21日下午7:50:01 @修改备注 @since @throws
	 */
	public void refreshWithCache() {
		// 如果有缓存数据的时候，直接在原来的基础上自动下拉刷新请求最新列表
		if (dataAdapter.getData() != null && dataAdapter.getData() instanceof List<?>
				&& !((List<?>) dataAdapter.getData()).isEmpty()) {
			Log.d("ListViewHelper", "走了缓存框架");
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					pullToRefreshPinnedHeaderListView.setRefreshing();
				}
			}, 100);
			// 如果没数据时，走刷新框架
		} else {
			Log.d("ListViewHelper", "走刷新框架");
			refresh();
		}

	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		pullToRefreshPinnedHeaderListView.setOnItemClickListener(onItemClickListener);
	}

	/**
	 * 刷新，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
	 */
	public void refresh() {
		if (dataAdapter == null || refreshPresenter == null) {

			if (pullToRefreshPinnedHeaderListView != null) {
				pullToRefreshPinnedHeaderListView.onRefreshComplete();
			}
			return;
		}
		isLoading = true;
		// mLoadMoreView.showNormal();
		if (dataAdapter.isEmpty()) {
			mLoadView.showLoading();
			pullToRefreshPinnedHeaderListView.onRefreshComplete();
		} else {
			pullToRefreshPinnedHeaderListView.showHeadRefreshing();
		}
		if (onStateChangeListener != null) {
			onStateChangeListener.onStartRefresh(dataAdapter);
		}
		refreshPresenter.refresh();
	}

	public void refreshComplete(DATA result) {
		if (result == null) {
			if (dataAdapter.isEmpty()) {
				 mLoadView.showFail();
			} else {
				// mLoadView.tipFail();
			}
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					isLoading = false;
					pullToRefreshPinnedHeaderListView.onRefreshComplete();
				}
			}, 100);
		} else {
			loadDataTime = System.currentTimeMillis();
			dataAdapter.setData(result, true);
			Log.d("测试ListView加载刷新更多", "listviewhelper");

			dataAdapter.notifyDataSetChanged();
			if (dataAdapter.isEmpty()) {
				mLoadView.showEmpty();
			} else {
				mLoadView.restore();
				pullToRefreshPinnedHeaderListView.getRefreshableView().setSelection(0);
			}
			hasMoreData = refreshPresenter.hasData();
			if (hasMoreData) {
				mLoadMoreView.showNormal();
			} else {
				mLoadMoreView.showNomore();
			}
			isLoading = false;
			pullToRefreshPinnedHeaderListView.onRefreshComplete();
		}
		if (onStateChangeListener != null) {
			onStateChangeListener.onEndRefresh(dataAdapter, result);
		}

	}

	/**
	 * 加载更多，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void loadMore() {
		if (isLoading()) {
			return;
		}
		if (dataAdapter.isEmpty()) {
			refresh();
			return;
		}

		if (dataAdapter == null || refreshPresenter == null) {
			if (pullToRefreshPinnedHeaderListView != null) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						pullToRefreshPinnedHeaderListView.onRefreshComplete();
					}
				}, 200);
			}
			return;
		}
		isLoading = true;
		if (onStateChangeListener != null) {
			onStateChangeListener.onStartLoadMore(dataAdapter);
		}
		mLoadMoreView.showLoading();

		refreshPresenter.loadMore();
	}

	public void loadMoreDataComplete(DATA result) {
		if (result == null) {
			mLoadMoreView.showFail();
		} else {
			dataAdapter.setData(result, false);
			dataAdapter.notifyDataSetChanged();
			if (dataAdapter.isEmpty()) {
				mLoadView.showEmpty();
			} else {
				mLoadView.restore();
			}
			hasMoreData = refreshPresenter.hasData();
			if (hasMoreData) {
				mLoadMoreView.showNormal();
			} else {
				mLoadMoreView.showNomore();
			}
		}
		if (onStateChangeListener != null) {
			onStateChangeListener.onEndLoadMore(dataAdapter, result);
		}
		isLoading = false;
	}

	/**
	 * 是否正在加载中
	 *
	 * @return
	 */
	public boolean isLoading() {
		return isLoading;
	}

	public ListView getListView() {
		return pullToRefreshPinnedHeaderListView.getRefreshableView();
	}

	/**
	 * 获取上次刷新数据的时间（数据成功的加载），如果数据没有加载成功过，那么返回-1
	 *
	 * @return
	 */
	public long getLoadDataTime() {
		return loadDataTime;
	}

	public OnStateChangeListener<DATA> getOnStateChangeListener() {
		return onStateChangeListener;
	}

	/**
	 * 设置状态监听，监听开始刷新，刷新成功，开始加载更多，加载更多成功
	 *
	 * @param onStateChangeListener
	 */
	public void setOnStateChangeListener(OnStateChangeListener<DATA> onStateChangeListener) {
		this.onStateChangeListener = onStateChangeListener;
	}

	public IDataAdapter<DATA> getAdapter() {
		return dataAdapter;
	}

	/**
	 * 设置适配器，用于显示数据
	 *
	 * @param adapter
	 */
	public void setAdapter(IDataAdapter<DATA> adapter) {
		mListView.setAdapter(adapter);
		this.dataAdapter = adapter;
	}

	public IRefreshPresenter<DATA> getRefreshPresenter() {
		return refreshPresenter;
	}

	/**
	 * 设置数据源，用于加载数据
	 *
//	 * @param dataSource
	 */
	public void setRefreshPresenter(IRefreshPresenter<DATA> refreshPresenter) {
		this.refreshPresenter = refreshPresenter;
	}

	public PullToRefreshAdapterViewBase<? extends ListView> getPullToRefreshAdapterViewBase() {
		return pullToRefreshPinnedHeaderListView;
	}

	public ILoadView getLoadView() {
		return mLoadView;
	}

	public ILoadMoreView getLoadMoreView() {
		return mLoadMoreView;
	}

	public boolean isAutoLoadMore() {
		return autoLoadMore;
	}

	public void setAutoLoadMore(boolean autoLoadMore) {
		this.autoLoadMore = autoLoadMore;
		if (!isLoading()) {
			mLoadMoreView.showNormal();
		}
	}

	private class OnRefreshListener211<T extends ListView> implements OnRefreshListener2<T> {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<T> refreshView) {
			refresh();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<T> refreshView) {
			loadMore();
		}

	}

}