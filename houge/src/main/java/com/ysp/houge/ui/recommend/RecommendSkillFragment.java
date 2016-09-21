package com.ysp.houge.ui.recommend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SkillDetailstAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.presenter.IRecommandSkillPresenter;
import com.ysp.houge.presenter.impl.recmmend.RecommendSkillPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IRecommandSkillPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.share.ShareUtils;

import java.util.List;

/**
 * 描述： 关注的技能列表
 *
 * @ClassName: RecommendSkillFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月3日 下午5:19:51
 * 
 *        版本: 1.0
 */
public class RecommendSkillFragment extends BaseFragment implements IRecommandSkillPageView {
	private final String TAG = getClass().getSimpleName();
	public SkillDetailstAdapter adapter;
	private WorkTypeEntity workTypeEntity;// 当前页面的关注点实体
	private SortTypeEntity sortTypeEntity;// 当前页面的排序实体
	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private IRecommandSkillPresenter<List<GoodsDetailEntity>> iRecommandPagePresenter;

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_buyer_recommend_list;
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);

		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
		mRefreshListView.getRefreshableView().setDividerHeight(SizeUtils.dip2px(getActivity(), 10));
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iRecommandPagePresenter = new RecommendSkillPresenter(this);
		iRecommandPagePresenter.setRecommendAndSortTypeEntity(workTypeEntity, sortTypeEntity);

		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iRecommandPagePresenter);

		adapter = new SkillDetailstAdapter(getActivity(), SizeUtils.getScreenWidth(getActivity()));
		adapter.setListener(iRecommandPagePresenter);

		LogUtil.deBug(TAG," "+adapter.getCount(),2);

		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();
		LogUtil.deBug(TAG," refresh "+adapter.getCount(),2);


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

	@Override
	public void setDate(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity) {
		this.workTypeEntity = workTypeEntity;
		this.sortTypeEntity = sortTypeEntity;
	}

	@Override
	public void jumpToSkillDetailPage(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, id);
		SkillDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToUserInfoDetailPage(int id) {
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
       ShareUtils.share(getActivity(), detailEntity, 1);
	}

	/** 接收更换排序方式的消息 */
	public void onEventMainThread(SortTypeEntity sortTypeEntity) {
		if (isAdded() && isVisible()) {
			iRecommandPagePresenter.setRecommendAndSortTypeEntity(null, sortTypeEntity);
		}
	}

    /** 接收更换排序方式的消息 */
    public void onEventMainThread(BDLocation location) {
        if (isAdded() && isVisible() && iRecommandPagePresenter != null) {
            iRecommandPagePresenter.refresh();
        }
    }
}