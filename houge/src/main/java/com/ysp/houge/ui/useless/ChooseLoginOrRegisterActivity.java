package com.ysp.houge.ui.useless;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.eventbus.FinishActivityEventBusEntity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.login.RegisterActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.EmoDotView;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * @描述:选择登录还是注册页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月25日下午3:25:58
 * @version 2.4
 */
public class ChooseLoginOrRegisterActivity extends BaseFragmentActivity
		implements OnClickListener, OnPageChangeListener {
	/** 软件介绍切换 */
	private ViewPager mMainPager;
	private Button mLoginBtn;
	private Button mRegisterBtn;
	private TextView mForgotPassword;
	/** 切换位置指示控件 */
	private EmoDotView mEmoDotView;
	/** 倒计时 */
	private TimeCount timeCount;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_choose_login_or_register);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mMainPager = (ViewPager) findViewById(R.id.mMainPager);
		mLoginBtn = (Button) findViewById(R.id.mLoginBtn);
		mRegisterBtn = (Button) findViewById(R.id.mRegisterBtn);
		mForgotPassword = (TextView) findViewById(R.id.mForgotPassword);
		mEmoDotView = (EmoDotView) findViewById(R.id.mEmoDotView);
		mMainPager.addOnPageChangeListener(this);
		mLoginBtn.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);
		mForgotPassword.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		mMainPager.setAdapter(new IntroducePagerAdapter());

		mEmoDotView.setPos(4, 0);
		mEmoDotView.setColor(getResources().getColor(R.color.introduce_dot_normal),
				getResources().getColor(R.color.introduce_dot_choosed));

		timeCount = new TimeCount(Integer.MAX_VALUE, 3000);

		EventBus.getDefault().register(this);
	}

	@Override
	protected void initActionbar() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mLoginBtn:
			// UIHelper.jumpToLogin(ChooseLoginOrRegisterActivity.this);
			break;
		case R.id.mRegisterBtn:
			RegisterActivity.jumpIn(this);
			// UIHelper.jumpToPartTimeJobList(ChooseLoginOrRegisterActivity.this,
			// null, FormWhichPageType.RegisterPage);
			break;
		case R.id.mForgotPassword:
			// UIHelper.jumpToWriteMobileToGetPwd(ChooseLoginOrRegisterActivity.this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		int pos = position % 4;
		mEmoDotView.setPos(4, pos);
	}

	private RelativeLayout initPageOne(Context context, int resId) {
		RelativeLayout pageOne = new RelativeLayout(context);
		pageOne.setBackgroundColor(getResources().getColor(R.color.introduce_bg));
		// 获取屏幕的宽
		int screenWidth = SizeUtils.getScreenWidth(ChooseLoginOrRegisterActivity.this);
		// 获取屏幕的高
		int screenHeight = SizeUtils.getScreenHeight(ChooseLoginOrRegisterActivity.this);
		// 设置圆图位置
		ImageView mCenterImg = new ImageView(ChooseLoginOrRegisterActivity.this);
		mCenterImg.setBackgroundResource(resId);
		LayoutParams marginParams = new RelativeLayout.LayoutParams((int) ((502.0 / 640) * screenWidth),
				(int) ((502.0 / 640) * screenWidth));
		marginParams.topMargin = (int) ((170.0 / 1136) * screenHeight);
		marginParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		pageOne.addView(mCenterImg, marginParams);
		return pageOne;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ChooseLoginOrRegisterActivity"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
		timeCount.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		timeCount.cancel();
		MobclickAgent.onPageEnd("ChooseLoginOrRegisterActivity"); // 保证
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	/**
	 * @描述:登录或注册成功时通知当前页面进行finish操作 @方法名: onEventMainThread @param
	 *                              finishActivityEventBusEntity @返回类型 void @创建人
	 *                              tyn @创建时间 2015年9月25日下午3:26:19 @修改人 tyn @修改时间
	 *                              2015年9月25日下午3:26:19 @修改备注 @since @throws
	 */
	public void onEventMainThread(FinishActivityEventBusEntity finishActivityEventBusEntity) {
		if (finishActivityEventBusEntity != null) {
			switch (finishActivityEventBusEntity.getFinishViewType()) {
			case ChooseLoginOrRegisterActivity:
				finish();
				break;

			default:
				break;
			}
		}
	}

	public class IntroducePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			switch (position % 4) {
			case 0:
				final RelativeLayout pageOne = initPageOne(container.getContext(), R.drawable.login_one_picture);
				container.addView(pageOne, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				return pageOne;
			case 1:
				final RelativeLayout pageTwo = initPageOne(container.getContext(), R.drawable.login_two_picture);
				container.addView(pageTwo, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				return pageTwo;
			case 2:
				final RelativeLayout pageThree = initPageOne(container.getContext(), R.drawable.login_three_picture);
				container.addView(pageThree, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				return pageThree;
			case 3:
				final RelativeLayout pageFour = initPageOne(container.getContext(), R.drawable.login_four_picture);
				container.addView(pageFour, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				return pageFour;
			default:
				final RelativeLayout page = initPageOne(container.getContext(), R.drawable.login_one_picture);
				container.addView(page, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				return page;
			}

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	public class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mMainPager.setCurrentItem(mMainPager.getCurrentItem() + 1);
		}
	}
}
