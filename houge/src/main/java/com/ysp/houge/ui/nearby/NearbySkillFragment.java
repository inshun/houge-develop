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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SkillDetailstAdapter;
import com.ysp.houge.adapter.ViewPagerImgAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.BannerEntity;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.presenter.INearbySkillPresenter;
import com.ysp.houge.presenter.impl.NearbySkillPresenter;
import com.ysp.houge.receiver.PushMessageManager;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.INearbySkillPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.newjoin.NewJoinSkillActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.utility.share.ShareUtils;
import com.ysp.houge.widget.EmoDotView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 附近技能列表页面
 *
 * @ClassName: NearbySkillFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:32:44
 * 
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class NearbySkillFragment extends BaseFragment
		implements INearbySkillPageView, OnClickListener, OnPageChangeListener {

	/** 下拉刷新listView */
	public SkillDetailstAdapter adapter;
	// 头部布局控件 start
	private View headView;
	private TextView tvNewJoin; // 最新加入
	private int count;// Banner的大小，用在指示器
	private ViewPager vpGenius;// 牛人Banner
	private EmoDotView emoDotView; // 指示器
	private List<ImageView> vpList;
	private RelativeLayout bannerLayout;
	private ViewPagerImgAdapter bannerAdapter;
	// 推荐技能最外层布局
	private LinearLayout lineNearbyLayout;
	private LinearLayout lineRecommendOne;
	private LinearLayout lineRecommendTwo;
	private LinearLayout lineRecommendThree;
	private LinearLayout lineRecommendFour;
	private ImageView ivRecommendOne;
	private ImageView ivRecommendTwo;
	private ImageView ivRecommendThree;
	private ImageView ivRecommendFour;
	private TextView tvRecomemdTitleOne;
	private TextView tvRecomemdTitleTwo;
	private TextView tvRecomemdTitleThree;
	private TextView tvRecomemdTitleFour;
	private TextView tvRecomemdLoveOne;
	private TextView tvRecomemdLoveTwo;
	private TextView tvRecomemdLoveThree;
	// 头部布局控件 end
	private TextView tvRecomemdLoveFour;
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private INearbySkillPresenter<List<GoodsDetailEntity>> iNearbySkillPresenter;

	private int size;
	private WorkTypeEntity workTypeEntity;
	private SortTypeEntity sortTypeEntity;
	private List<NearbySkill> skillsView;

	@Override
	protected int getContentView() {
		return R.layout.fragment_buyer_nearby_list;
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
		headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_nearby_skill, null);

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

		lineNearbyLayout = (LinearLayout) headView.findViewById(R.id.line_nearby_skills);

		skillsView = new ArrayList<NearbySkillFragment.NearbySkill>();

		lineRecommendOne = (LinearLayout) headView.findViewById(R.id.line_nearby_one);
		ivRecommendOne = (ImageView) headView.findViewById(R.id.iv_img_one);
		tvRecomemdTitleOne = (TextView) headView.findViewById(R.id.tv_name_one);
		tvRecomemdLoveOne = (TextView) headView.findViewById(R.id.tv_love_one);
		NearbySkill skillOne = new NearbySkill(lineRecommendOne, ivRecommendOne, tvRecomemdTitleOne, tvRecomemdLoveOne);
		skillsView.add(skillOne);

		lineRecommendTwo = (LinearLayout) headView.findViewById(R.id.line_nearby_two);
		ivRecommendTwo = (ImageView) headView.findViewById(R.id.iv_img_two);
		tvRecomemdTitleTwo = (TextView) headView.findViewById(R.id.tv_name_two);
		tvRecomemdLoveTwo = (TextView) headView.findViewById(R.id.tv_love_two);
		NearbySkill skillTwo = new NearbySkill(lineRecommendTwo, ivRecommendTwo, tvRecomemdTitleTwo, tvRecomemdLoveTwo);
		skillsView.add(skillTwo);

		lineRecommendThree = (LinearLayout) headView.findViewById(R.id.line_nearby_three);
		ivRecommendThree = (ImageView) headView.findViewById(R.id.iv_img_three);
		tvRecomemdTitleThree = (TextView) headView.findViewById(R.id.tv_name_three);
		tvRecomemdLoveThree = (TextView) headView.findViewById(R.id.tv_love_three);
		NearbySkill skillTherr = new NearbySkill(lineRecommendThree, ivRecommendThree, tvRecomemdTitleThree,
				tvRecomemdLoveThree);
		skillsView.add(skillTherr);

		lineRecommendFour = (LinearLayout) headView.findViewById(R.id.line_nearby_four);
		ivRecommendFour = (ImageView) headView.findViewById(R.id.iv_img_four);
		tvRecomemdTitleFour = (TextView) headView.findViewById(R.id.tv_name_four);
		tvRecomemdLoveFour = (TextView) headView.findViewById(R.id.tv_love_four);
		NearbySkill skillFour = new NearbySkill(lineRecommendFour, ivRecommendFour, tvRecomemdTitleFour,
				tvRecomemdLoveFour);
		skillsView.add(skillFour);

		lineRecommendOne.setOnClickListener(this);
		lineRecommendTwo.setOnClickListener(this);
		lineRecommendThree.setOnClickListener(this);
		lineRecommendFour.setOnClickListener(this);
		// 初始头布局中的控件 end

		size = (SizeUtils.getScreenWidth(getActivity()) - SizeUtils.dip2px(getActivity(), 1)) / 2;
		for (int i = 0; i < skillsView.size(); i++) {
			skillsView.get(i).imageView.getLayoutParams().width = size;
			skillsView.get(i).imageView.getLayoutParams().height = size;
		}
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iNearbySkillPresenter = new NearbySkillPresenter(this);
		iNearbySkillPresenter.setNearbyEntity(workTypeEntity, sortTypeEntity);
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		mRefreshListView.getRefreshableView().addHeaderView(headView);
		listViewHelper.setRefreshPresenter(iNearbySkillPresenter);
		adapter = new SkillDetailstAdapter(getActivity(), SizeUtils.getScreenWidth(getActivity()));
		adapter.setListener(iNearbySkillPresenter);
		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();

		iNearbySkillPresenter.loadHeadData();
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
	public void initNewJion(int count) {
		// TODO 初始化最新加入
		if (count == 0) {
			tvNewJoin.setVisibility(View.GONE);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(count);
			sb.append("个新技能，");
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
	public void initNearby(List<GoodsDetailEntity> list) {
		if (null == list || list.isEmpty()) {
			lineNearbyLayout.setVisibility(View.GONE);
		} else {
			// 是否隐藏下面两个
			boolean hideButtom = list.size() < 3;
			for (int i = 0; i < skillsView.size(); i++) {
				// 如果数据数量大于视图数量，证明还能接着显示
				if (list.size() > i) {
					GoodsDetailEntity detailEntity = list.get(i);
					if (detailEntity != null) {
						skillsView.get(i).layout.setClickable(true);
						// 显示第一章图片
						if (null != detailEntity.image && detailEntity.image.size() > 0) {
							MyApplication.getInstance().getImageLoaderManager().loadNormalImage(
									skillsView.get(i).imageView, detailEntity.image.get(0), LoadImageType.NormalImage);
						}

						// 标题
						if (TextUtils.isEmpty(detailEntity.title)) {
							skillsView.get(i).name.setText("未知");
						} else {
							skillsView.get(i).name.setText(detailEntity.title);
						}

						// 浏览次数
						if (detailEntity.view_count == 0) {
							skillsView.get(i).loveount.setText("0");
						} else {
							skillsView.get(i).loveount.setText(String.valueOf(detailEntity.view_count));
						}
					}
				} else {
					// 如果大于1而且 下面两个需要隐藏，那么属性为GONE，否则为INVISIBLE
					if (i > 1 && hideButtom) {
						skillsView.get(i).layout.setVisibility(View.GONE);
					} else {
						skillsView.get(i).layout.setVisibility(View.INVISIBLE);
						skillsView.get(i).layout.setClickable(false);
					}
				}
			}
		}
	}

	@Override
	public void jumpToNewJoin(WorkTypeEntity entity) {
		// TODO 跳转到最新加入
		Bundle bundle = new Bundle();
		bundle.putSerializable(NewJoinSkillActivity.KEY, entity);
		NewJoinSkillActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToUserInfo(int id) {
		// TODO 跳转到个人详情
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, id);
		UserDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToSkillDetailPage(int id) {
		// TODO 跳转到详情页面
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, id);
		SkillDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToHaveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(getActivity(), chatPageEntity);
	}

	@Override
	public void share(GoodsDetailEntity detailEntity) {
        ShareUtils.share(getActivity(),detailEntity, 1);
	}

	@Override
	public void  jumpToBannerInfo(BannerEntity bannerEntity) {
        PushMessageManager.handlePushMsg(getActivity(),bannerEntity.getUrl());
	}

	@Override
	public void onClick(View v) {
		// TODO 点击事件
		switch (v.getId()) {
		// 最新加入
		case R.id.tv_new_join_and_skill:
			iNearbySkillPresenter.newJion();
			tvNewJoin.setVisibility(View.GONE);
//			refresh();
			break;

		// 推荐1
		case R.id.line_nearby_one:
			iNearbySkillPresenter.recommned(0);
			break;

		// 推荐2
		case R.id.line_nearby_two:
			iNearbySkillPresenter.recommned(1);
			break;

		// 推荐3
		case R.id.line_nearby_three:
			iNearbySkillPresenter.recommned(2);
			break;

		// 推荐4
		case R.id.line_nearby_four:
			iNearbySkillPresenter.recommned(3);
			break;
		default:
			break;
		}

		// Banner图片
		if (v.getTag() != null && v.getTag() instanceof String && TextUtils.equals((String) v.getTag(), "IMG")) {
			iNearbySkillPresenter.genius(vpGenius.getCurrentItem());
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		emoDotView.setPos(count, arg0);
	}

	/** 接收更换排序方式的消息 */
	public void onEventMainThread(SortTypeEntity sortTypeEntity) {
		if (isAdded() && isVisible()) {
			iNearbySkillPresenter.setNearbyEntity(null, sortTypeEntity);
		}
	}

	/** 接收定位信息 */
	public void onEventMainThread(BDLocation location) {
        if (isAdded() && isVisible() && listViewHelper != null) {
            listViewHelper.refresh();
        }
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	class NearbySkill {
		LinearLayout layout;
		ImageView imageView;
		TextView name;
		TextView loveount;

		public NearbySkill(LinearLayout layout, ImageView imageView, TextView name, TextView loveount) {
			super();
			this.layout = layout;
			this.imageView = imageView;
			this.name = name;
			this.loveount = loveount;
		}
	}
}
