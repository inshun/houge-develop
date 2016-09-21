package com.ysp.houge.ui.useless;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ysp.houge.R;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.widget.MyActionBar;

/** 认证 提交成功 */
public class MeAuthenticationSuccessActivity extends BaseFragmentActivity {
	private Button bt_confirm;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_authentication_success);

	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		bt_confirm = (Button) findViewById(R.id.bt_confirm);
		bt_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("提交成功");
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

	}

}
