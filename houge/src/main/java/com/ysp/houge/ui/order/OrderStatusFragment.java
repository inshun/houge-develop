package com.ysp.houge.ui.order;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.OrderStatusAdapter;
import com.ysp.houge.presenter.IOrderStatusPresenter;
import com.ysp.houge.presenter.impl.OrderStatusPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.IOrderStatusPageView;

import android.os.Bundle;
import android.view.View;

/**
 * 描述： 订单状态
 *
 * @ClassName: OrderStatusFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月16日 下午3:57:41
 * 
 *        版本: 1.0
 */
public class OrderStatusFragment extends BaseFragment implements IOrderStatusPageView {
	private String order_id;

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<String>> listViewHelper;
	private IOrderStatusPresenter<List<String>> iOrderStatusPresenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_order_status;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
		mRefreshListView.getRefreshableView().setDivider(null);
		mRefreshListView.getRefreshableView().setDividerHeight(0);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iOrderStatusPresenter = new OrderStatusPresenter(this);
		listViewHelper = new ListViewHelper<List<String>>(mRefreshListView);
		mRefreshListView.setMode(Mode.DISABLED);
		listViewHelper.setRefreshPresenter(iOrderStatusPresenter);
		listViewHelper.setAdapter(new OrderStatusAdapter(getActivity()));
		listViewHelper.refresh();
	}

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<String> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<String> t) {
	}

    @Override
    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }
}
