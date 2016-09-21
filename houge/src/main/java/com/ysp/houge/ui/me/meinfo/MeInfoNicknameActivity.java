package com.ysp.houge.ui.me.meinfo;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.presenter.IReviseNickNamePagePresenter;
import com.ysp.houge.presenter.impl.ReviseNickNamePagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IReviseNicknamePageView;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * @描述: 修改昵称页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日下午6:50:38
 * @version 1.0
 */
public class MeInfoNicknameActivity extends BaseFragmentActivity implements IReviseNicknamePageView {

	public static final String NICKNAME = "nickname";
	private String nickname;
	private ClearEditText cetNickName;
	private IReviseNickNamePagePresenter iReviseNickNamePagePresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, MeInfoNicknameActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_info_nickame);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("修改昵称");
		actionBar.setLeftEnable(true);
		actionBar.setRightText(getString(R.string.submit));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iReviseNickNamePagePresenter.clickRevise(cetNickName.getText().toString().trim());
			}
		});
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeState(Bundle savedInstanceState) {
		super.initializeState(savedInstanceState);
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(NICKNAME)) {
				nickname = getIntent().getExtras().getString(NICKNAME);
			}
		}
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		cetNickName = (ClearEditText) findViewById(R.id.cet_nickname);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iReviseNickNamePagePresenter = new ReviseNickNamePagePresenter(this);
		cetNickName.setText(nickname);
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

    /** 修改成功 */
	@Override
	public void sumitReviseSuccess() {
		close();
	}
}
