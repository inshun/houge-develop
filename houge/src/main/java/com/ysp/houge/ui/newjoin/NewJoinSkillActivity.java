package com.ysp.houge.ui.newjoin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SkillDetailstAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.presenter.INewJoinSkillPresenter;
import com.ysp.houge.presenter.impl.NewJoinSkillPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.INewJoinSkillPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.share.ShareUtils;
import com.ysp.houge.widget.MyActionBar;

import java.util.List;

/**
 * 描述： 新加入技能
 *
 * @ClassName: NewJoinSkillActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月5日 上午10:29:02
 * 
 *        版本: 1.0
 */
public class NewJoinSkillActivity extends BaseFragmentActivity implements INewJoinSkillPageView {
	public static final String KEY = "workTypeEntity";
	private SkillDetailstAdapter adapter;

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private INewJoinSkillPresenter<List<GoodsDetailEntity>> iNewJoinSkillPresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, NewJoinSkillActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_new_join_skill);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.new_join));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iNewJoinSkillPresenter = new NewJoinSkillPresenter(this);
		if (getIntent().getExtras().containsKey(KEY)) {
			WorkTypeEntity workTypeEntity = (WorkTypeEntity) getIntent().getExtras().getSerializable(KEY);
			iNewJoinSkillPresenter.setWorkType(workTypeEntity);
		}
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iNewJoinSkillPresenter);
		adapter = new SkillDetailstAdapter(this, SizeUtils.getScreenWidth(this));
		adapter.setListener(iNewJoinSkillPresenter);
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
	public void jumpToSkillDetails(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, id);
		SkillDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToNeedDetails(int id) {
	}

	@Override
	public void jumpToUserInfo(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, id);
		UserDetailsActivity.jumpIn(this, bundle);
	}

    @Override
    public void jumpToHaveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(this, chatPageEntity);
    }

    @Override
    public void share(GoodsDetailEntity goodsDetailEntity) {
        ShareUtils.share(this, goodsDetailEntity, 1);
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
