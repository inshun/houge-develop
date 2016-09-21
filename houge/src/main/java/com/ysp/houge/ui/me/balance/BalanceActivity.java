package com.ysp.houge.ui.me.balance;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.BalanceAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.BalanceEntity;
import com.ysp.houge.model.entity.eventbus.RechargeSuccessEventBusEntity;
import com.ysp.houge.presenter.IBalancePagePresenter;
import com.ysp.houge.presenter.impl.BalancePagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IBalancePageView;
import com.ysp.houge.widget.MyActionBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 描述： 余额(收支记录)
 *
 * @ClassName: BalanceActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月23日 上午10:10:45
 * 
 * 版本: 1.0
 */
@SuppressLint("InflateParams")
public class BalanceActivity extends BaseFragmentActivity implements IBalancePageView, OnClickListener {
	private MyActionBar actionBar;

	private View headView;
	private TextView tvBalance;
	private TextView tvWithdrawdeposit;// 提现
	private TextView tvTopUp;// 充值

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private BalanceAdapter adapter;
	private ListViewHelper<List<BalanceEntity>> listViewHelper;
	private IBalancePagePresenter<List<BalanceEntity>> iBalancePagePresenter;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, BalanceActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_balance);
	}

	@Override
	protected void initActionbar() {
		actionBar = new MyActionBar(this);
		actionBar.setTitle(R.string.balance);
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

		headView = LayoutInflater.from(this).inflate(R.layout.head_balance, null);
		tvBalance = (TextView) headView.findViewById(R.id.tv_balance);
		tvWithdrawdeposit = (TextView) headView.findViewById(R.id.tv_withdraw_deposit);
		tvTopUp = (TextView) headView.findViewById(R.id.tv_top_up);

		tvWithdrawdeposit.setOnClickListener(this);
		tvTopUp.setOnClickListener(this);

		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
		mRefreshListView.getRefreshableView().addHeaderView(headView);

        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_BUYER:
                tvBalance.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                break;

            case MyApplication.LOG_STATUS_SELLER:
                tvBalance.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                break;
        }
	}

    @Override
	protected void initializeData(Bundle savedInstanceState) {
		iBalancePagePresenter = new BalancePagePresenter(this);
		iBalancePagePresenter.loadBalance();

		listViewHelper = new ListViewHelper<List<BalanceEntity>>(mRefreshListView);
		mRefreshListView.setMode(Mode.DISABLED);
		listViewHelper.setRefreshPresenter(iBalancePagePresenter);
		adapter = new BalanceAdapter(this);
		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<BalanceEntity> t) {
		listViewHelper.refreshComplete(t);
		if (null==t|| t.isEmpty()) {

		}
	}

	@Override
	public void loadMoreComplete(List<BalanceEntity> t) {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 提现
		case R.id.tv_withdraw_deposit:
			iBalancePagePresenter.clickWithdrawDeposit();
			break;
		// 充值
		case R.id.tv_top_up:
			iBalancePagePresenter.clickTopUp();
			break;
		default:
			break;
		}
	}

	@Override
	public void showBalance(String balance) {
		if (TextUtils.isEmpty(balance) || TextUtils.equals(balance, "0")) {
			tvBalance.setText("￥ 0.00");
		} else {
			tvBalance.setText("￥ " + balance);
		}
	}

    /** 充值成功的消息 */
    public void onEventMainThread(RechargeSuccessEventBusEntity busEntity) {
        listViewHelper.refresh();
    }


	@Override
	public void jumpToWithdrawDeposit() {
		WithdrawDepositActivity.jumpIn(this);
	}

	@Override
	public void jumpToTopUp() {
		RechargeActivity.jumpIn(this);
	}
}
