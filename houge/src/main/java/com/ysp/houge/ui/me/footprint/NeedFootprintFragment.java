package com.ysp.houge.ui.me.footprint;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.NeedDetailsAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.presenter.INeedFootprintPresenter;
import com.ysp.houge.presenter.impl.NeedFootprintPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.INeedFootprintPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.share.ShareUtils;

import java.util.List;

/**
 * 描述： 需求足迹
 *
 * @ClassName: NeedFootprintFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月26日 下午5:41:17
 * 
 *        版本: 1.0
 */
public class NeedFootprintFragment extends BaseFragment implements INeedFootprintPageView {
	public NeedDetailsAdapter adapter;

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private INeedFootprintPresenter<List<GoodsDetailEntity>> iNeedFootprintPresenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_need_footprint;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iNeedFootprintPresenter = new NeedFootprintPresenter(this);
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iNeedFootprintPresenter);
		adapter = new NeedDetailsAdapter(getActivity());
		adapter.setListener(iNeedFootprintPresenter);
		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<GoodsDetailEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<GoodsDetailEntity> t) {
		listViewHelper.loadMoreDataComplete(t);
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
	public void jumpToNeedDetails(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(NeedDetailsActivity.KEY, id);
		NeedDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToUserDetails(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, id);
		UserDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToHaveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(getActivity(),chatPageEntity);
	}

	@Override
	public void share(GoodsDetailEntity detailEntity) {
        ShareUtils.share(getActivity(),detailEntity, 2);
	}
}
