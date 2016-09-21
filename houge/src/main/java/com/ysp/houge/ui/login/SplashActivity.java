package com.ysp.houge.ui.login;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.igexin.sdk.PushManager;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.Constants;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.PhoneInfo;
import com.ysp.houge.model.entity.bean.VersionInfoEntity;
import com.ysp.houge.presenter.ISplashActivytPresenter;
import com.ysp.houge.presenter.impl.SplashActivityPresenter;
import com.ysp.houge.ui.HomeActivity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ISplashActivity;
import com.ysp.houge.utility.ImageUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.SystemUtils;

import java.io.IOException;
/**
 * 描述： App启动页面
 *
 * @ClassName: SplashActivity
 *
 * @author: hx
 *
 * @date: 2015年12月3日 下午5:22:19
 *
 *        版本: 1.0
 */
public class SplashActivity extends BaseFragmentActivity implements ISplashActivity {
	private ImageView mRootLayout;
	//页面跳转倒计时
	private CountDownTimer timer;
	/*提示升级*/
	private ISplashActivytPresenter presenter;
	private boolean isShowDialog = false;
	private String updataInfo;
	private int mustUpData = 0;/** 0 代表不是必须升级用户可选择
	 1代表有重大升级（包括紧急BUG 或重大业务逻辑升级）必须升级*/
	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_splash);
	}
	@Override
	protected void initActionbar() {
	}
	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRootLayout = (ImageView) findViewById(R.id.mRootLayout);
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void initializeData(Bundle savedInstanceState) {
		// 打开友盟统计
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
		// 打开个推
		PushManager.getInstance().initialize(this.getApplicationContext());
		//获取手机信息
		initPhoneInfo();
		/*获取版本号*/
		presenter = new SplashActivityPresenter(this);
		presenter.getLatestVertionInfo();


		try {
			// 显示页面背景图
			Drawable drawable = ImageUtil.ResImgToDrawable(SplashActivity.this, R.drawable.splash);
			mRootLayout.setBackground(drawable);
		} catch (IOException e) {
			mRootLayout.setBackgroundResource(R.drawable.splash);
		}
		check();
	}
	public void check(){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
				!= PackageManager.PERMISSION_GRANTED) {
			//申请WRITE_EXTERNAL_STORAGE权限
			ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE},
					100);
		}else {
			//已赋予过权限
			//延时跳转
			delayedJump();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode==100){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 允许
				//延时跳转
				delayedJump();
			} else {
//				if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
				//无权限
				//延时跳转
				delayedJump();
				showToast("请打开对应的权限，否者会导致App无法正常运行！");
			}
		}
	}
	private void delayedJump() {
		// 用计时器处理能解决即使期间点击back、home键问题
		timer = new CountDownTimer(2000, 2000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
				// 这里直接跳转首页，后面需要引导图再做处理
				//HomeActivity.jumpIn(SplashActivity.this, null);
				//需要强制登陆的启用下面的代码
				if (null == MyApplication.getInstance().getUserInfo()) {
					SplashGuideViewActivity.jumpIn(SplashActivity.this, null);
				} else {
					Bundle bundle = new Bundle();
					bundle.putBoolean(Constants.KEY_IS_SHOW_UP_DATA_DIALOG, isShowDialog);
					bundle.putString(Constants.KEY_UP_DATA_INFO,updataInfo);
					bundle.putInt(Constants.KEY_MUST_UP_DATA,mustUpData);
					HomeActivity.jumpIn(SplashActivity.this, bundle);
				}
			}
		};
		timer.start();

	}
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 统计时长
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	private void initPhoneInfo() {
		// TODO 获取手机信息(用于登录时的手机信息)
		PhoneInfo phoneInfo = new PhoneInfo();
		//DeviceId
		String dev_id = SystemUtils.getDeviceId(getApplicationContext());
		phoneInfo.setDevice_id(TextUtils.isEmpty(dev_id) ? "" : dev_id);
		//手机型号
		String phone_name = SystemUtils.getProductModel();
		phoneInfo.setDevice_model(TextUtils.isEmpty(phone_name) ? "" : phone_name);
		phoneInfo.setScreenWidth(SizeUtils.getScreenWidth(this));
		phoneInfo.setScreenHeight(SizeUtils.getScreenHeight(this));
		//保存手机信息
		MyApplication.getInstance().setPhoneInfo(phoneInfo);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_HOME:
			case KeyEvent.KEYCODE_BACK:
				timer.cancel();
				System.exit(0);
			default:
				break;
		}
		return false;
	}
	/*检查版本信息 判断是否有升级提醒*/
	@Override
	public void checkVersionInfo(VersionInfoEntity info) {
		if (info == null){
			return;
		}
		if (!SystemUtils.getAppVersionName(this) .equals(info.getVersion_name()) ){
			isShowDialog = true;
			updataInfo =info.getVersion_info();
			mustUpData = info.getVersion_must_update();
		}
	}
}