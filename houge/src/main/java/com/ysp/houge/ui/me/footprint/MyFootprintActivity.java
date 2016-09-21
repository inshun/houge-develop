package com.ysp.houge.ui.me.footprint;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.FootprintAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IMyFootprintPageView;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

//我的足迹
public class MyFootprintActivity extends BaseFragmentActivity
		implements OnClickListener, IMyFootprintPageView, OnPageChangeListener {

	private int currentPage = 0;
	private List<BaseFragment> list;

	private ViewPager vpFootPrint;
	private View skill, user, need;

    public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, MyFootprintActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_my_footprint);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.my_footprint));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		vpFootPrint = (ViewPager) findViewById(R.id.vp_footprint);
		vpFootPrint.addOnPageChangeListener(this);

		skill = findViewById(R.id.v_skill_footprint);
		user = findViewById(R.id.v_user_footprint);
		need = findViewById(R.id.v_need_footprint);

		findViewById(R.id.rela_skill_footprint).setOnClickListener(this);
		findViewById(R.id.rela_user_footprint).setOnClickListener(this);
		findViewById(R.id.rela_need_footprint).setOnClickListener(this);

        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_SELLER:
                skill.setBackgroundColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                user.setBackgroundColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                need.setBackgroundColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                break;
            case MyApplication.LOG_STATUS_BUYER:
                skill.setBackgroundColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                user.setBackgroundColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                need.setBackgroundColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                break;
        }
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		list = new ArrayList<BaseFragment>();
		list.add(new SkillFootprintFragment());
		list.add(new UserFootprintFragment());
		list.add(new NeedFootprintFragment());

		vpFootPrint.setAdapter(new FootprintAdapter(getSupportFragmentManager(), list));

		setIndex(currentPage);
		vpFootPrint.setCurrentItem(currentPage);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela_skill_footprint:
			if (currentPage == 0) {
				return;
			}
			currentPage = 0;
			setIndex(currentPage);
			vpFootPrint.setCurrentItem(currentPage);
			break;
		case R.id.rela_user_footprint:
			if (currentPage == 1) {
				return;
			}
			currentPage = 1;
			setIndex(currentPage);
			vpFootPrint.setCurrentItem(currentPage);
			break;
		case R.id.rela_need_footprint:
			if (currentPage == 2) {
				return;
			}
			currentPage = 2;
			setIndex(currentPage);
			vpFootPrint.setCurrentItem(currentPage);
			break;
		}
	}

	@Override
	public void setIndex(int index) {
		switch (index) {
		case 0:
			skill.setVisibility(View.VISIBLE);
			user.setVisibility(View.INVISIBLE);
			need.setVisibility(View.INVISIBLE);
			break;
		case 1:
			skill.setVisibility(View.INVISIBLE);
			user.setVisibility(View.VISIBLE);
			need.setVisibility(View.INVISIBLE);
			break;
		case 2:
			skill.setVisibility(View.INVISIBLE);
			user.setVisibility(View.INVISIBLE);
			need.setVisibility(View.VISIBLE);
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
	public void onPageSelected(int arg0) {
		currentPage = arg0;
		setIndex(currentPage);
	}
}
