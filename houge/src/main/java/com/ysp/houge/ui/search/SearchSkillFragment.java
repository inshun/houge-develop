package com.ysp.houge.ui.search;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SkillDetailstAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.presenter.ISearchSkillPresenter;
import com.ysp.houge.presenter.impl.SearchSkillPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.ISearchSkillPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.share.ShareUtils;

import java.util.List;

/**
 * 描述： 搜索技能页面
 *
 * @ClassName: SearchSkillFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月6日 上午11:27:55
 * 
 *        版本: 1.0
 */
public class SearchSkillFragment extends BaseFragment implements ISearchSkillPageView {
	public SkillDetailstAdapter adapter;
	private String searchText;
	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private ISearchSkillPresenter<List<GoodsDetailEntity>> iSearchSkillPresenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_search_skill;
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
		iSearchSkillPresenter = new SearchSkillPresenter(this);
		iSearchSkillPresenter.setSearchText(searchText);
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iSearchSkillPresenter);
		adapter = new SkillDetailstAdapter(getActivity(), SizeUtils.getScreenWidth(getActivity()));
		adapter.setListener(iSearchSkillPresenter);
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
		if (isAdded()) {
			listViewHelper.refreshComplete(t);
		}
	}

	@Override
	public void loadMoreComplete(List<GoodsDetailEntity> t) {
		if (isAdded()) {
			listViewHelper.loadMoreDataComplete(t);
		}
	}

	/** 接收搜索历史列表的点击事件 */
	public void onEventMainThread(String string) {
		if (isAdded() && isVisible()) {
			iSearchSkillPresenter.setSearchText(string);
			listViewHelper.refresh();
		}
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
	public void jumpToSkillDetailPage(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, id);
		SkillDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToHaveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(getActivity(),chatPageEntity);
	}

	@Override
	public void share(GoodsDetailEntity goodsDetailEntity) {
        ShareUtils.share(getActivity(),goodsDetailEntity, 1);
	}

}
