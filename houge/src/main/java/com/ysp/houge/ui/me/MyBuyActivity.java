package com.ysp.houge.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.BuyOrderAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.MyBuyEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IMyBuyPresenter;
import com.ysp.houge.presenter.impl.MyBuyPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IMyBuyPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.order.OrderDetailsActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.MyActionBar;

import java.util.List;

/**
 * 描述： 我购买的
 *
 * @ClassName: MeBuyActivity
 * @author: hx
 * @date: 2015年12月13日 上午10:51:49
 * <p/>
 * 版本: 1.0
 */
public class MyBuyActivity extends BaseFragmentActivity
		implements IMyBuyPageView, OnItemClickListener, com.ysp.houge.lisenter.OnItemClickListener {

	/**
	 * 下拉刷新listView
	 */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<MyBuyEntity>> listViewHelper;
	private IMyBuyPresenter<List<MyBuyEntity>> iMyBuyPresenter;
	private UserInfoEntity userInfoEntity;
	private BuyOrderAdapter adapter;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, MyBuyActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_buy);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.me_buy));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
		mRefreshListView.getRefreshableView().setDividerHeight(SizeUtils.dip2px(this, 10));
		mRefreshListView.setOnItemClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iMyBuyPresenter = new MyBuyPresenter(this);
		listViewHelper = new ListViewHelper<List<MyBuyEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iMyBuyPresenter);
		adapter = new BuyOrderAdapter(this);
		adapter.setListener(this);
		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<MyBuyEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<MyBuyEntity> t) {
		listViewHelper.loadMoreDataComplete(t);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (null != mRefreshListView && null != iMyBuyPresenter) {
			listViewHelper.refresh();
		}
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		jumpToOrderDetails(position, -1);
	}

	@Override
	public void OnClick(int position, int operation) {
		switch (operation) {
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_ORDER_DETAIL:
				jumpToOrderDetails(position, -1);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_ORDER_FUNCTION:
				jumpToOrderDetails(position, CLICK_OPERATION_ORDER_FUNCTION);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
				jumpToUserDetails(position);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL:
				jumpToSkillDetails(position);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_NEED_DETAIL:
				jumpToNeedDetails(position);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_IM_TO_SALE:
				jumpToCharPageOne(position);
				break;
			default:
				jumpToOrderDetails(position, operation);
				break;
		}
	}


	@Override
	public void jumpToOrderDetails(int position, int operation) {
		MyBuyEntity buyEntity = adapter.getData().get(position);
		if (null != buyEntity) {
			Bundle bundle = new Bundle();
			bundle.putString(OrderDetailsActivity.TAG, OrderDetailsActivity.BUY);
			bundle.putInt(OrderDetailsActivity.OPERATION, operation);
			bundle.putString(OrderDetailsActivity.KEY, buyEntity.order_id);
			OrderDetailsActivity.jumpIn(this, bundle);
		}
	}

	@Override
	public void jumpToUserDetails(int position) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, adapter.getData().get(position).seller_user_id);
		UserDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToSkillDetails(int position) {
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, adapter.getData().get(position).good_id);
		SkillDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToNeedDetails(int position) {
		Bundle bundle = new Bundle();
		bundle.putInt(NeedDetailsActivity.KEY, adapter.getData().get(position).good_id);
		NeedDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToCharPageOne(int position) {
		MyBuyEntity buyEntity = adapter.getData().get(position);
		userInfoEntity = buyEntity.seller_user;
		ChatPageEntity chatPageEntity = new ChatPageEntity(String.valueOf(userInfoEntity.id));
		Bundle bundle = new Bundle();
		bundle.putSerializable(ChatActivity.CHAT_PAGE_ENTITY, chatPageEntity);
		ChatActivity.jumpIn(this, bundle);

	}

}
