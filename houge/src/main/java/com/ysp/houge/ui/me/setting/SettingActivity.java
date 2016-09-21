package com.ysp.houge.ui.me.setting;

import com.easemob.chat.EMChatManager;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.AppManager;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.impl.PushInfoModelImpl;
import com.ysp.houge.presenter.ISettingPagePresenter;
import com.ysp.houge.presenter.impl.SettingPagePresenter;
import com.ysp.houge.receiver.PushMessageManager;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ISettingPageView;
import com.ysp.houge.ui.login.LoginActivity;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @描述:设置页面View层
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月13日下午7:40:55
 * @version 1.0
 */
public class SettingActivity extends BaseFragmentActivity implements OnClickListener, ISettingPageView {
	private TextView tv_weixingongzhong;
	private TextView tv_contactUs;

	private ISettingPagePresenter iSettingPagePresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, SettingActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_setting);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(R.string.system);
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		findViewById(R.id.rl_changePassword).setOnClickListener(this);
		findViewById(R.id.rl_new_message_inform).setOnClickListener(this);
		findViewById(R.id.rl_aboutUs).setOnClickListener(this);
		findViewById(R.id.rl_contactUs).setOnClickListener(this);
		findViewById(R.id.rl_yijian).setOnClickListener(this);
		findViewById(R.id.rl_logout).setOnClickListener(this);

		tv_weixingongzhong = (TextView) findViewById(R.id.tv_weixingongzhong);
		tv_contactUs = (TextView) findViewById(R.id.tv_contactUs);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iSettingPagePresenter = new SettingPagePresenter(this);
		iSettingPagePresenter.getAppContacts();
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
    protected void onDestroy() {
        hideProgress();
        super.onDestroy();
    }

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_changePassword:
			ChangePasswordActivity.jumpIn(this);
			break;
		case R.id.rl_new_message_inform:
			NewMessageNotificationActivity.jumpIn(this);
			break;
		case R.id.rl_aboutUs:
//			WebEntity entity = new WebEntity(HttpApi.getAbsPathUrl(HttpApi.GET_APP_ABOUT_US), "关于我们");
//			Bundle bundle = new Bundle();
//			bundle.putSerializable(WebViewActivity.KEY, entity);
//			WebViewActivity.jumpIn(this, bundle);
			 AboutUsActivity.jumpIn(this);
			break;
		case R.id.rl_contactUs:
			iSettingPagePresenter.clickCustomService();
			break;
		case R.id.rl_yijian:
			FeedBackActivity.jumpIn(this);
			break;
		case R.id.rl_logout:
			createLogoutDialog();
			break;

		default:
			break;
		}

	}

	/** 创建拨打电话对话框 */
	private void createCallPhoneDialog(final String telephone) {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity(getString(R.string.sure_contact_system_office),
				telephone, getString(R.string.cancel), getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(SettingActivity.this, dialogEntity,
				new OnYesOrNoDialogClickListener() {

					@Override
					public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
						switch (yesOrNoType) {
						case BtnOk:
							GetUri.openCallTelephone(SettingActivity.this, telephone);
							break;

						default:
							break;
						}
					}
				});
		dialog.show();
	}

	private void createLogoutDialog() {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity(getString(R.string.sure_logout), "",
				getString(R.string.cancel), getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(SettingActivity.this, dialogEntity,
				new OnYesOrNoDialogClickListener() {

					@Override
					public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
						switch (yesOrNoType) {
						case BtnOk:
							PushMessageManager.unBindAlias(SettingActivity.this,
									MyApplication.getInstance().getCurrentUid());
							MyApplication.getInstance().getPreferenceUtils().cleanLoginPreference();
							MyApplication.getInstance().setUserInfo(null);
							AppManager.getAppManager().finishAllActivity();
							// 推送信息收集接口状态下线
							requestPushInfo();

							// 下线聊天
							EMChatManager.getInstance().logout();// 此方法为同步方法
							break;

						default:
							break;
						}
					}
				});
		dialog.show();
	}

	/** 发送推送信息 */
	private void requestPushInfo() {
		showProgress("请稍后...");
		new PushInfoModelImpl().setPushInfo(1, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				hideProgress();
				LoginActivity.jumpIn(SettingActivity.this);
				close();
			}

			@Override
			public void onError(int errorCode, String message) {
				hideProgress();
				LoginActivity.jumpIn(SettingActivity.this);
				close();
			}
		});
	}

	/** 显示官方公众号和联系方式 */
	@Override
	public void showContact(String officePublicNumber, String customServiceNumber) {
		if (!TextUtils.isEmpty(officePublicNumber)) {
			tv_weixingongzhong.setText(officePublicNumber);
		}
		if (!TextUtils.isEmpty(customServiceNumber)) {
			tv_contactUs.setText(customServiceNumber);
		}
	}

	@Override
	public void showCallPhoneDialog(String telephone) {
		createCallPhoneDialog(telephone);
	}
}
