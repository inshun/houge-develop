package com.ysp.houge.ui.nearby;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.NeedDetailsAdapter;
import com.ysp.houge.adapter.ViewPagerImgAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.BannerEntity;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.presenter.INearbyNeedPresenter;
import com.ysp.houge.presenter.impl.NearbyNeedPresenter;
import com.ysp.houge.receiver.PushMessageManager;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.INearbyNeedPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.newjoin.NewJoinNeedActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.utility.share.ShareUtils;
import com.ysp.houge.widget.EmoDotView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 附近需求
 *
 * @ClassName: NearbyNeedFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月18日 下午6:59:08
 * 
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class NearbyNeedFragment extends BaseFragment
		implements INearbyNeedPageView, OnClickListener, OnPageChangeListener {
	public NeedDetailsAdapter adapter;
	// 头部布局控件 start
	private View headView;
	private TextView tvNewJoin; // 最新加入
	private int count;// Banner的大小，用在指示器
	private ViewPager vpGenius;// 牛人Banner
	private EmoDotView emoDotView; // 指示器
	private List<ImageView> vpList;
	private RelativeLayout bannerLayout;
	private ViewPagerImgAdapter bannerAdapter;
	private WorkTypeEntity workTypeEntity;
	private SortTypeEntity sortTypeEntity;
	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private INearbyNeedPresenter<List<GoodsDetailEntity>> iNearbyNeedPresenter;


	@Override
	protected int getContentView() {
		return R.layout.fragment_need_footprint;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);

		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
		mRefreshListView.getRefreshableView().setDividerHeight(SizeUtils.dip2px(getActivity(), 10));

		// 加载头布局
		headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_nearby_need, null);

		// 初始头布局中的控件 start
		// 最新加入
		tvNewJoin = (TextView) headView.findViewById(R.id.tv_new_join_and_skill);
		tvNewJoin.setOnClickListener(this);

		vpGenius = (ViewPager) headView.findViewById(R.id.vp_nearby_skill_genius);
		emoDotView = (EmoDotView) headView.findViewById(R.id.mEmoDotView);
		vpGenius.addOnPageChangeListener(this);
		emoDotView.setColor(getResources().getColor(R.color.introduce_dot_normal),
				getResources().getColor(R.color.status_text_bg));
		bannerLayout = (RelativeLayout) headView.findViewById(R.id.rela_genius_banner);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iNearbyNeedPresenter = new NearbyNeedPresenter(this);
		iNearbyNeedPresenter.setNearbyEntity(workTypeEntity, sortTypeEntity);
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		mRefreshListView.getRefreshableView().addHeaderView(headView);
		listViewHelper.setRefreshPresenter(iNearbyNeedPresenter);
		adapter = new NeedDetailsAdapter(getActivity());
		adapter.setListener(iNearbyNeedPresenter);
		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();

		iNearbyNeedPresenter.loadHeadData();
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
	public void setDate(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity) {
		this.workTypeEntity = workTypeEntity;
		this.sortTypeEntity = sortTypeEntity;
	}

	@Override
	public void initNewJion(int count) {
		// TODO 初始化最新加入
		if (count == 0) {
			tvNewJoin.setVisibility(View.GONE);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(count);
			sb.append("个新需求，");
			sb.append("点击查看");
			tvNewJoin.setText(sb.toString());
		}
	}

	@Override
	public void initBanner(List<BannerEntity> entity) {
		if (null == entity || entity.isEmpty() || null == getActivity()) {
			bannerLayout.setVisibility(View.GONE);
		} else {
			count = entity.size();
			emoDotView.setPos(count, 0);

			// 保证Banner的ImageView集合只有当前的数据
			if (null == vpList) {
				vpList = new ArrayList<ImageView>();
			} else {
				vpList.clear();
			}

			for (int i = 0; i < entity.size(); i++) {
				ImageView imageView = new ImageView(getActivity());
				imageView.setTag("IMG");
				imageView.setOnClickListener(this);
				// 防止拉伸变形(根据宽高自动裁剪)
				imageView.setScaleType(ScaleType.CENTER_CROP);
				MyApplication.getInstance().getImageLoaderManager().loadNormalImage(imageView, entity.get(i).getPic(),
						LoadImageType.NormalImage);
				vpList.add(imageView);
			}

			// 如果适配器为空就创建他
			if (bannerAdapter == null) {
				bannerAdapter = new ViewPagerImgAdapter(vpList);
			}
			vpGenius.setAdapter(bannerAdapter);
		}
	}

	@Override
	public void jumpToNeedDetailPage(int id) {
		// TODO 跳转到详情页面
		Bundle bundle = new Bundle();
		bundle.putInt(NeedDetailsActivity.KEY, id);
		NeedDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToNewJoin(WorkTypeEntity entity) {
		// TODO 跳转到最新加入
		Bundle bundle = new Bundle();
		bundle.putSerializable(NewJoinNeedActivity.KEY, entity);
		NewJoinNeedActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToUserInfo(int id) {
		// TODO 跳转到个人详情
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
        ShareUtils.share(getActivity(), detailEntity, 2);
	}

    @Override
    public void  jumpToBannerInfo(BannerEntity bannerEntity) {
        PushMessageManager.handlePushMsg(getActivity(), bannerEntity.getUrl());
    }

	@Override
	public void onPageSelected(int arg0) {
		emoDotView.setPos(count, arg0);
	}

	/** 接收更换排序方式的消息 */
	public void onEventMainThread(SortTypeEntity sortTypeEntity) {
		if (isAdded() && isVisible()) {
			iNearbyNeedPresenter.setNearbyEntity(null, sortTypeEntity);
		}
	}

	/** 接收定位信息 */
	public void onEventMainThread(BDLocation location) {
        if (isAdded() && isVisible() && listViewHelper != null) {
            listViewHelper.refresh();
        }
	}

	@Override
	public void onClick(View v) {
		// TODO 点击事件
		switch (v.getId()) {
		// 最新加入
		case R.id.tv_new_join_and_skill:
			iNearbyNeedPresenter.newJion();
			tvNewJoin.setVisibility(View.GONE);
			refresh();
			break;
		default:
			break;
		}

		// Banner图片
		if (v.getTag() != null && v.getTag() instanceof String && TextUtils.equals((String) v.getTag(), "IMG")) {
			iNearbyNeedPresenter.bannerClick(vpGenius.getCurrentItem());
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

}
