package com.ysp.houge.ui.me;

import java.util.List;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.NeedAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.presenter.IMyNeedPresenter;
import com.ysp.houge.presenter.impl.MyNeedPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.iview.IMyNeedPageView;
import com.ysp.houge.ui.publish.NeedPulishActivity;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author it_huang
 *
 *         我的需求View层
 */
public class MyNeedActivity extends BaseFragmentActivity implements IMyNeedPageView{
	private NeedAdapter adapter;
    private View headView;
    private TextView addNeed;
	
	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private IMyNeedPresenter<List<GoodsDetailEntity>> iMyNeedPresenter;

    public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, MyNeedActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_my_need);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.my_need));
		actionBar.setRightText("客服");
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iMyNeedPresenter.serviceClick();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
        headView = LayoutInflater.from(this).inflate(R.layout.head_my_skill, null);
        addNeed = (TextView) headView.findViewById(R.id.tv_add_skill);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, SizeUtils.dip2px(this, 48));
        addNeed.setLayoutParams(params);
        addNeed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NeedPulishActivity.JumpIn(MyNeedActivity.this, null);
                overridePendingTransition(R.anim.slide_in_from_top, R.anim.activity_stay);
            }
        });

		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
        mRefreshListView.getRefreshableView().addHeaderView(headView);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iMyNeedPresenter = new MyNeedPresenter(this);
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iMyNeedPresenter);
		adapter=new NeedAdapter(this);
		adapter.setListener(iMyNeedPresenter);
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
	public void showCallPhonePop() {
		String initJson = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
		if (TextUtils.isEmpty(initJson)) {
			showToast("获取客服电话失败");
		} else {
			final String phoneNum = new Gson().fromJson(initJson, AppInitEntity.class).mobile;

			YesOrNoDialogEntity entity = new YesOrNoDialogEntity("确定拨打客服电话?", phoneNum, getString(R.string.cancel),
					getString(R.string.sure));
			YesOrNoDialog dialog = new YesOrNoDialog(this, entity, new OnYesOrNoDialogClickListener() {

				@Override
				public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
					switch (yesOrNoType) {
					case BtnOk:
						GetUri.openCallTelephonePage(MyNeedActivity.this, phoneNum);
						break;
					case BtnCancel:
						break;
					}
				}

			});

			if (!dialog.isShowing()) {
				dialog.show();
			}
		}
	}

    @Override
    public void jumpToNeedDetails(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(NeedDetailsActivity.KEY,id);
        NeedDetailsActivity.jumpIn(this,bundle);
    }
}
