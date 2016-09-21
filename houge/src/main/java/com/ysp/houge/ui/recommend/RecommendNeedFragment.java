package com.ysp.houge.ui.recommend;

import android.os.Bundle;
import android.view.View;

import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.NeedDetailsAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.presenter.IRecommandNeedPresenter;
import com.ysp.houge.presenter.impl.recmmend.RecommendNeedPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IRecommandNeedPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.share.ShareUtils;

import java.util.List;

/**
 * 描述： 需求关注列表
 *
 * @ClassName: SellerRecommendListFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月18日 下午1:36:21
 * 
 *        版本: 1.0
 */
public class RecommendNeedFragment extends BaseFragment implements IRecommandNeedPageView {
	public NeedDetailsAdapter adapter;
	private WorkTypeEntity workTypeEntity;// 当前页面的关注点实体
	private SortTypeEntity sortTypeEntity;// 当前页面的排序实体
	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private IRecommandNeedPresenter<List<GoodsDetailEntity>> iRecommandNeedPresenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_recommend_need;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);

		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
		mRefreshListView.getRefreshableView().setDividerHeight(SizeUtils.dip2px(getActivity(), 10));
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iRecommandNeedPresenter = new RecommendNeedPresenter(this);
		iRecommandNeedPresenter.setRecommendAndSortTypeEntity(workTypeEntity, sortTypeEntity);

		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iRecommandNeedPresenter);

		adapter = new NeedDetailsAdapter(getActivity());
		adapter.setListener(iRecommandNeedPresenter);

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

	@Override
	public void setDate(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity){
		this.workTypeEntity = workTypeEntity;
		this.sortTypeEntity = sortTypeEntity;
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
        ChatActivity.haveATalk(getActivity(), chatPageEntity);
	}

	@Override
	public void share(GoodsDetailEntity detailEntity) {
        ShareUtils.share(getActivity(),detailEntity, 2);
	}

	/** 接收更换排序方式的消息 */
	public void onEventMainThread(SortTypeEntity sortTypeEntity) {
		if (isAdded() && isVisible()) {
			iRecommandNeedPresenter.setRecommendAndSortTypeEntity(null, sortTypeEntity);
		}
	}

	/** 接收定位信息 */
	public void onEventMainThread(BDLocation location) {
        if (isAdded() && isVisible() && listViewHelper != null) {
            listViewHelper.refresh();
        }
	}
}
