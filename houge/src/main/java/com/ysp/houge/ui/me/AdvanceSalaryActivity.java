package com.ysp.houge.ui.me;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.GetUri;
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
 * 描述： 预支工资
 *
 * @ClassName: AdvanceSalaryActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月17日 下午4:51:56
 * 
 *        版本: 1.0
 */
public class AdvanceSalaryActivity extends BaseFragmentActivity {
	private TextView mPhoneNum;

	private String phoneNum;

    public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, AdvanceSalaryActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_advance_salary);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.advance_salary));
		actionBar.setRightText("拨打");
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GetUri.openCallTelephone(AdvanceSalaryActivity.this, phoneNum);
			}
		});
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mPhoneNum = (TextView) findViewById(R.id.tv_service_phone);

        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_SELLER:
                mPhoneNum.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                break;
            case MyApplication.LOG_STATUS_BUYER:
                mPhoneNum.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                break;
        }
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		String initJson = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
		if (TextUtils.isEmpty(initJson)) {
			showToast("获取客服电话失败");
			close();
		} else {
			phoneNum = new Gson().fromJson(initJson, AppInitEntity.class).mobile;
			mPhoneNum.setText(phoneNum);
		}
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
