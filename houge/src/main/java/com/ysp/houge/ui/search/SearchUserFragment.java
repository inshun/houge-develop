package com.ysp.houge.ui.search;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SearchUserAdapter;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.ISearchUserPresenter;
import com.ysp.houge.presenter.impl.SearchUserPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.ISearchUserPageView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 描述： 搜索用户View层
 *
 * @ClassName: SearchUserFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 上午10:27:30
 * 
 *        版本: 1.0
 */
public class SearchUserFragment extends BaseFragment implements ISearchUserPageView, OnItemClickListener {
	private String searchText;
	private SearchUserAdapter adapter;

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<UserInfoEntity>> listViewHelper;
	private ISearchUserPresenter<List<UserInfoEntity>> iSearchUserPresenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_search_user;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);

		mRefreshListView.setOnItemClickListener(this);
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
	protected void initializeData(Bundle savedInstanceState) {
		iSearchUserPresenter = new SearchUserPresenter(this);
		iSearchUserPresenter.setSearchText(searchText);
		listViewHelper = new ListViewHelper<List<UserInfoEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iSearchUserPresenter);
		adapter = new SearchUserAdapter(getActivity());
		listViewHelper.setAdapter(adapter);
		mRefreshListView.setOnItemClickListener(this);
		listViewHelper.refresh();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<UserInfoEntity> t) {
		if (isAdded()) {
			listViewHelper.refreshComplete(t);
		}
	}

	@Override
	public void loadMoreComplete(List<UserInfoEntity> t) {
		if (isAdded()) {
			listViewHelper.loadMoreDataComplete(t);
		}
	}

	/** 接收搜索历史列表的点击事件 */
	public void onEventMainThread(String string) {
		if (isAdded() && isVisible()) {
			iSearchUserPresenter.setSearchText(string);
			listViewHelper.refresh();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, (int) arg3);
		UserDetailsActivity.jumpIn(getActivity(), bundle);
	}

    @Override
    public void setSearchText(String searchText) {
		this.searchText = searchText;
    }
}
