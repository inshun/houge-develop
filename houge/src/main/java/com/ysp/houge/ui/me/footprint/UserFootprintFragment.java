package com.ysp.houge.ui.me.footprint;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.UserInfoAdapter;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IUserFootprintPresenter;
import com.ysp.houge.presenter.impl.UserFootprintPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IUserFootprintPageView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 描述： 用户足迹
 *
 * @ClassName: UserFootprintFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月26日 下午1:49:12
 * 
 * 版本: 1.0
 */
public class UserFootprintFragment extends BaseFragment implements OnItemClickListener, IUserFootprintPageView {
	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<UserInfoEntity>> listViewHelper;
	private IUserFootprintPresenter<List<UserInfoEntity>> iUserFootprintPresenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_user_footprint;
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
		iUserFootprintPresenter = new UserFootprintPresenter(this);
		listViewHelper = new ListViewHelper<List<UserInfoEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iUserFootprintPresenter);
		listViewHelper.setAdapter(new UserInfoAdapter(getActivity()));
		mRefreshListView.setOnItemClickListener(this);
		listViewHelper.refresh();
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<UserInfoEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<UserInfoEntity> t) {
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 跳转到个人详情
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, (int) arg3);
		UserDetailsActivity.jumpIn(getActivity(), bundle);
	}

}
