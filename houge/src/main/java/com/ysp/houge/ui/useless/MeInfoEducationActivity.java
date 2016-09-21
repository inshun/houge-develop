package com.ysp.houge.ui.useless;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.presenter.IReviseEducationPagePresenter;
import com.ysp.houge.presenter.impl.ReviseEdcationalPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IReviseEducationPageView;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:修改学历页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日下午12:43:26
 * @version 1.0
 */
public class MeInfoEducationActivity extends BaseFragmentActivity implements
		IReviseEducationPageView, OnClickListener {
	public static final String EDUCATIONAL = "educational";
	private int education;
	private IReviseEducationPagePresenter iReviseEducationPagePresenter;
	private TextView tv_specialty;
	private TextView tv_undergraduate;
	private TextView tv_master;
	private TextView tv_doctor;

	@Override
	protected void initializeState(Bundle savedInstanceState) {
		super.initializeState(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(EDUCATIONAL)) {
				education = savedInstanceState.getInt(EDUCATIONAL);
			}
		} else if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(EDUCATIONAL)) {
				education = getIntent().getExtras().getInt(EDUCATIONAL);
			}
		} else {
			finish();
		}
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_info_education);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		tv_specialty = (TextView) findViewById(R.id.tv_specialty);
		tv_undergraduate = (TextView) findViewById(R.id.tv_undergraduate);
		tv_master = (TextView) findViewById(R.id.tv_master);
		tv_doctor = (TextView) findViewById(R.id.tv_doctor);
		tv_specialty.setOnClickListener(this);
		tv_undergraduate.setOnClickListener(this);
		tv_master.setOnClickListener(this);
		tv_doctor.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iReviseEducationPagePresenter = new ReviseEdcationalPagePresenter(this);
		switchEducation(education);
	}

	private void switchEducation(int education2) {
		switch (education2) {
		case 0:
			tv_specialty.setTextColor(getResources().getColor(R.color.white));
			tv_specialty.setBackgroundResource(R.drawable.round_green);

			tv_undergraduate.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_undergraduate.setBackgroundResource(R.drawable.round_white);
			tv_master.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_master.setBackgroundResource(R.drawable.round_white);
			tv_doctor.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_doctor.setBackgroundResource(R.drawable.round_white);
			break;
		case 1:
			tv_specialty.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_specialty.setBackgroundResource(R.drawable.round_white);

			tv_undergraduate.setTextColor(getResources()
					.getColor(R.color.white));
			tv_undergraduate.setBackgroundResource(R.drawable.round_green);

			tv_master.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_master.setBackgroundResource(R.drawable.round_white);
			tv_doctor.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_doctor.setBackgroundResource(R.drawable.round_white);
			break;
		case 2:
			tv_specialty.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_specialty.setBackgroundResource(R.drawable.round_white);

			tv_undergraduate.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_undergraduate.setBackgroundResource(R.drawable.round_white);

			tv_master.setTextColor(getResources().getColor(R.color.white));
			tv_master.setBackgroundResource(R.drawable.round_green);

			tv_doctor.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_doctor.setBackgroundResource(R.drawable.round_white);
			break;
		case 3:
			tv_specialty.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_specialty.setBackgroundResource(R.drawable.round_white);
			tv_undergraduate.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_undergraduate.setBackgroundResource(R.drawable.round_white);
			tv_master.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_master.setBackgroundResource(R.drawable.round_white);

			tv_doctor.setTextColor(getResources().getColor(R.color.white));
			tv_doctor.setBackgroundResource(R.drawable.round_green);
			break;

		default:
			tv_specialty.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_specialty.setBackgroundResource(R.drawable.round_white);
			tv_undergraduate.setTextColor(getResources().getColor(
					R.color.color_e5e5e5));
			tv_undergraduate.setBackgroundResource(R.drawable.round_white);
			tv_master.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_master.setBackgroundResource(R.drawable.round_white);

			tv_doctor.setTextColor(getResources()
					.getColor(R.color.color_e5e5e5));
			tv_doctor.setBackgroundResource(R.drawable.round_white);
			break;
		}
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.degrees));
		actionBar.setLeftEnable(true);
		actionBar.setLeftText(getString(R.string.back));
		actionBar.setRightText(R.string.submit);
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iReviseEducationPagePresenter.clickRevise(education);
			}
		});
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	public void sumitReviseSuccess() {
		close();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_specialty:
			switchEducation(0);
			education = 0;
			break;
		case R.id.tv_undergraduate:
			switchEducation(1);
			education = 1;
			break;
		case R.id.tv_master:
			switchEducation(2);
			education = 2;
			break;
		case R.id.tv_doctor:
			switchEducation(3);
			education = 3;
			break;

		default:
			break;
		}
	}

}
