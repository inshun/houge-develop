package com.ysp.houge.ui.me;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.CommentAdapter;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.presenter.ICommentListPresenter;
import com.ysp.houge.presenter.impl.CommentListPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ICommentListPageView;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

/**
 * 描述： 评论列表
 *
 * @ClassName: CommentListActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月17日 上午11:23:43
 * 
 *        版本: 1.0
 */
public class CommentListActivity extends BaseFragmentActivity implements ICommentListPageView {
	public static final String KEY = "order_id";

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<CommentEntity>> listViewHelper;
	private ICommentListPresenter<List<CommentEntity>> iCommentListPresenter;

    public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, CommentListActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_comment_list);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.all_comment));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iCommentListPresenter = new CommentListPresenter(this);
		if (null == getIntent() || null == getIntent().getExtras()  || !getIntent().getExtras().containsKey(KEY)) {
			iCommentListPresenter.setOrderId(-1);
		} else {
			iCommentListPresenter.setOrderId(getIntent().getExtras().getInt(KEY));
		}

		listViewHelper = new ListViewHelper<List<CommentEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iCommentListPresenter);
		listViewHelper.setAdapter(new CommentAdapter(this));
		listViewHelper.refresh();
	}

	@Override
	public void refresh() {
		mRefreshListView.onRefreshComplete();
	}

	@Override
	public void refreshComplete(List<CommentEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<CommentEntity> t) {
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
}
