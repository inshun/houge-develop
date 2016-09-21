package com.ysp.houge.ui.useless;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:添加工作经历页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月25日下午1:32:25
 * @version 1.0
 */
public class AddWorkInfoActivity extends BaseFragmentActivity implements
		OnClickListener {

	private EditText et_work_intro;
	private EditText et_work_descpritor;
	private TextView tv_left_count;
	private LinearLayout ll_work_type;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_add_work_info);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		et_work_intro = (EditText) findViewById(R.id.et_work_intro);
		et_work_descpritor = (EditText) findViewById(R.id.et_work_descpritor);
		tv_left_count = (TextView) findViewById(R.id.tv_left_count);
		ll_work_type = (LinearLayout) findViewById(R.id.ll_work_type);
		ll_work_type.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.work_experence_add));
		actionBar.setLeftEnable(true);
		actionBar.setLeftText(getString(R.string.back));
		actionBar.setRightText(getString(R.string.submit));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		actionbar.addView(actionBar);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_work_type:

			break;

		default:
			break;
		}
	}

}
