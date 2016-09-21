package com.ysp.houge.ui.search;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.NeedDetailsAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.presenter.ISearchNeedPresenter;
import com.ysp.houge.presenter.impl.SearchNeedPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.ISearchNeedPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.share.ShareUtils;

import java.util.List;

public class SearchNeedFragment extends BaseFragment implements ISearchNeedPageView {
	public NeedDetailsAdapter adapter;
	private String searchText;
	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private ISearchNeedPresenter<List<GoodsDetailEntity>> iSearchNeedPresenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_search_need;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iSearchNeedPresenter = new SearchNeedPresenter(this);
		iSearchNeedPresenter.setSearchText(searchText);
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		mRefreshListView.setMode(Mode.DISABLED);
		listViewHelper.setRefreshPresenter(iSearchNeedPresenter);
		adapter = new NeedDetailsAdapter(getActivity());
		adapter.setListener(iSearchNeedPresenter);
		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
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
	public void refreshComplete(List<GoodsDetailEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<GoodsDetailEntity> t) {
		listViewHelper.loadMoreDataComplete(t);
	}

    @Override
    public void setSearchText(String searchText) {
		this.searchText = searchText;
    }

    @Override
	public void jumpToUserInfoDetailPage(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, id);
		UserDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToNeedDetailPage(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(NeedDetailsActivity.KEY, id);
		NeedDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToHaveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(getActivity(),chatPageEntity);
	}

	@Override
	public void share(GoodsDetailEntity goodsDetailEntity) {
        ShareUtils.share(getActivity(),goodsDetailEntity, 2);
	}

	/** 接收搜索历史列表的点击事件 */
	public void onEventMainThread(String string) {
		if (isAdded() && isVisible()) {
			iSearchNeedPresenter.setSearchText(string);
			listViewHelper.refresh();
		}
	}

}
