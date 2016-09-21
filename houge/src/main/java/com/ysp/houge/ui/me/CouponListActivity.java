package com.ysp.houge.ui.me;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.presenter.ICoupomPagerPresenter;
import com.ysp.houge.presenter.impl.CoupomPagerPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ICouponPaperView;
import com.ysp.houge.ui.useless.PastDateCoupomFragment;
import com.ysp.houge.ui.useless.ValidCoupomFragment;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.TwoSwitchBtnView;
import com.ysp.houge.widget.TwoSwitchBtnView.OnTwoSwitchBtnClickListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @描述: 优惠券列表页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月27日下午3:41:54
 * @version 1.0
 */
@SuppressLint("Recycle")
public class CouponListActivity extends BaseFragmentActivity implements ICouponPaperView, OnClickListener {
	/** 可用状态常量 */
	public static final int INDEX_VALID = 0;
	/** 国过期状态常量 */
	public static final int INDEX_PAST_DATE = 1;
	private final String VALID = "VALID";
	private final String PAST_DATE = "PAST_DATE";
	/** 选择的下标 */
	private int currentTabIndex;
	private TwoSwitchBtnView mTwoSwitchBtnView;
	/** 底部使用 取消 包裹布局 */
	private LinearLayout mUseAndCancleLayout;
	private Button mUseBtn, mCancleBtn;
	/** 有效优惠劵Fragment */
	private ValidCoupomFragment validCoupomFragment;
	/** 过期优惠劵Fragment */
	private PastDateCoupomFragment pastDateCoupomFragment;
	private ICoupomPagerPresenter iCoupomPagerPresenter;
	
	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context,CouponListActivity.class));
	}
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_coupon);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		// TODO 初始化View
		mTwoSwitchBtnView = (TwoSwitchBtnView) findViewById(R.id.mTwoSwitchBtnView);
		mTwoSwitchBtnView.setLeftBtnText("有效");
		mTwoSwitchBtnView.setRightBtnText("无效");

		mUseAndCancleLayout = (LinearLayout) findViewById(R.id.line_use_and_cancle_btn);
		mCancleBtn = (Button) findViewById(R.id.btn_cancle);
		//mUseBtn = (Button) findViewById(R.id.btn_use);

		if (savedInstanceState == null) {
			validCoupomFragment = new ValidCoupomFragment();
			pastDateCoupomFragment = new PastDateCoupomFragment();
			// 添加显示第一个fragment
			getSupportFragmentManager().beginTransaction().add(R.id.line_coupon, validCoupomFragment, VALID);
		} else {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			if (getSupportFragmentManager().findFragmentByTag(VALID) != null)
				validCoupomFragment = (ValidCoupomFragment) getSupportFragmentManager().findFragmentByTag(VALID);
			else
				validCoupomFragment = new ValidCoupomFragment();
			ft.hide(validCoupomFragment);
			if (getSupportFragmentManager().findFragmentByTag(PAST_DATE) != null)
				pastDateCoupomFragment = (PastDateCoupomFragment) getSupportFragmentManager().findFragmentByTag(PAST_DATE);
			else
				pastDateCoupomFragment = new PastDateCoupomFragment();	
			ft.hide(pastDateCoupomFragment);
		}
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iCoupomPagerPresenter = new CoupomPagerPresenter(this);
		currentTabIndex = INDEX_VALID;
		iCoupomPagerPresenter.setHeadIndex(currentTabIndex);

		iCoupomPagerPresenter.setHeadIndex(currentTabIndex);

		mTwoSwitchBtnView.setOnTwoSwitchBtnClickListener(new OnTwoSwitchBtnClickListener() {
			@Override
			public void clickRightBtn() {
				iCoupomPagerPresenter.setHeadIndex(INDEX_PAST_DATE);
			}

			@Override
			public void clickLeftBtn() {
				iCoupomPagerPresenter.setHeadIndex(INDEX_VALID);
			}
		});
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
	protected void initActionbar() {
		// TODO 初始化标题栏
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("优惠券");
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	public void use() {
		Toast.makeText(this, "use", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void cancle() {
		Toast.makeText(this, "cancle", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void changeButtomStatus(boolean isShow) {
		if (isShow) {
			mUseAndCancleLayout.setVisibility(View.VISIBLE);
		} else {
			mUseAndCancleLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancle:

			break;
//		case R.id.btn_use:
//
//			break;
		default:
			break;
		}
	}

	@Override
	public void onValidChoise() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.hide(pastDateCoupomFragment).show(validCoupomFragment);
	}

	@Override
	public void onPastDateChoise() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.hide(validCoupomFragment).show(pastDateCoupomFragment);
	}

}