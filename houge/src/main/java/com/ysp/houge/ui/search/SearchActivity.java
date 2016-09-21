package com.ysp.houge.ui.search;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.db.SearchHistoryEntity;
import com.ysp.houge.popwindow.SearchTypePopupWindow;
import com.ysp.houge.presenter.ISearchPresenter;
import com.ysp.houge.presenter.impl.SearchPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ISearchPageView;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.ClearEditText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 描述： 搜索页面
 *
 * @ClassName: SearchActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月5日 下午2:41:23
 * 
 *        版本: 1.0
 */
@SuppressLint({ "InflateParams", "CommitTransaction" })
public class SearchActivity extends BaseFragmentActivity implements OnClickListener, ISearchPageView {
	public static final int SEARCH_TYPE_USER = 0;// 搜索类型 用户
	public static final int SEARCH_TYPE_SKILL = 2;// 搜索类型技能
	public static final int SEARCH_TYPE_NEED = 1;// 搜索类型需求

	private View searchTypeView;
	private TextView searchType;
	private ClearEditText text;
	private ImageView search, finish;

	private FragmentTransaction fragmentTransaction;
	private SearchHistoryFragment historyFragment;
	private SearchSkillFragment skillFragment;
	private SearchNeedFragment needFragment;
	private SearchUserFragment userFragment;
	private BaseFragment fragment;

	private ISearchPresenter iSearchPresenter;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, SearchActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_search);
	}

	@Override
	protected void initActionbar() {
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		searchType = (TextView) findViewById(R.id.tv_seatch_type);
		text = (ClearEditText) findViewById(R.id.cet_search);
		search = (ImageView) findViewById(R.id.iv_search);
		finish = (ImageView) findViewById(R.id.menu_head_left_iv);

		finish.setOnClickListener(this);
		search.setOnClickListener(this);
		searchType.setOnClickListener(this);

        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_BUYER:
                search.setImageResource(R.drawable.icon_search_orange);
                search.setBackgroundColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                finish.setImageResource(R.drawable.menu_left_orange);
                break;
            case MyApplication.LOG_STATUS_SELLER:
                search.setImageResource(R.drawable.icon_search_blue);
                search.setBackgroundColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                finish.setBackgroundResource(R.drawable.menu_left_blue);
                break;
        }
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (savedInstanceState != null) {
			historyFragment = (SearchHistoryFragment) getFragmentFromCatch("historyFragment");
			skillFragment = (SearchSkillFragment) getFragmentFromCatch("skillFragment");
			needFragment = (SearchNeedFragment) getFragmentFromCatch("needFragment");
			userFragment = (SearchUserFragment) getFragmentFromCatch("userFragment");

			if (historyFragment == null) {
				historyFragment = new SearchHistoryFragment();
			} else {
				fragmentTransaction.show(historyFragment);
			}

			if (skillFragment != null) {
				fragmentTransaction.hide(skillFragment);
			}

			if (needFragment != null) {
				fragmentTransaction.hide(needFragment);
			}

			if (userFragment != null) {
				fragmentTransaction.hide(userFragment);
			}
			fragmentTransaction.commitAllowingStateLoss();
		} else {
			// 显示历史Fragment
			showHistoryList(null);
		}

		iSearchPresenter = new SearchPresenter(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
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
	public void showSearchTypePop() {
		if (searchTypeView == null) {
			searchTypeView = LayoutInflater.from(this).inflate(R.layout.popup_search_type, null);
		}

		SearchTypePopupWindow pop = new SearchTypePopupWindow(searchTypeView, SizeUtils.dip2px(this, 160),
				LayoutParams.WRAP_CONTENT);

		pop.showAsDropDown(searchType, 0, 0);
		pop.setListener(iSearchPresenter);
	}

	@Override
	public void changeSearchType(int searchType) {
		if (historyFragment.isAdded() && historyFragment.isVisible()) {
			historyFragment.setType(searchType);
		}
		this.text.setText("");

		switch (searchType) {
		case SEARCH_TYPE_USER:
			this.searchType.setText(getString(R.string.search_user));
			break;
		case SEARCH_TYPE_SKILL:
			this.searchType.setText(getString(R.string.search_skill));
			break;
		case SEARCH_TYPE_NEED:
			this.searchType.setText(getString(R.string.search_need));
			break;
		}
	}

	@Override
	public void showHistoryList(BaseFragment fragment) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (historyFragment != null && historyFragment.isAdded()) {
			historyFragment = (SearchHistoryFragment) getFragmentFromCatch("historyFragment");
			if (fragment != null) {
				// 隐藏上一个fragment,显示新的fragment
				fragmentTransaction.hide(fragment).show(historyFragment);
			} else {
				fragmentTransaction.show(historyFragment);
			}
		} else {
			historyFragment = new SearchHistoryFragment();
			if (fragment != null) {
				// 如果老的不为空，就隐藏
				fragmentTransaction.hide(fragment);
			}
			fragmentTransaction.add(R.id.line_search_context, historyFragment, "historyFragment").show(historyFragment);
		}
		this.fragment = historyFragment;
		fragmentTransaction.commit();
	}

	@Override
	public void showSkillList(BaseFragment fragment, String text) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (skillFragment != null && skillFragment.isAdded()) {
			EventBus.getDefault().post(text);
			skillFragment = (SearchSkillFragment) getFragmentFromCatch("skillFragment");
			if (fragment != null) {
				// 隐藏上一个fragment,显示新的fragment
				fragmentTransaction.hide(fragment).show(skillFragment);
			} else {
				fragmentTransaction.show(skillFragment);
			}
		} else {
			skillFragment = new SearchSkillFragment();
            skillFragment.setSearchText(text);
			if (fragment != null) {
				// 如果老的不为空，就隐藏
				fragmentTransaction.hide(fragment);
			}
			fragmentTransaction.add(R.id.line_search_context, skillFragment, "skillFragment").show(skillFragment);
		}
		this.fragment = skillFragment;
		fragmentTransaction.commit();
	}

	@Override
	public void showNeedList(BaseFragment fragment, String text) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (needFragment != null && needFragment.isAdded()) {
			EventBus.getDefault().post(text);
			needFragment = (SearchNeedFragment) getFragmentFromCatch("needFragment");
			if (fragment != null) {
				// 隐藏上一个fragment,显示新的fragment
				fragmentTransaction.hide(fragment).show(needFragment);
			} else {
				fragmentTransaction.show(needFragment);
			}
		} else {
			needFragment = new SearchNeedFragment();
            needFragment.setSearchText(text);
			if (fragment != null) {
				// 如果老的不为空，就隐藏
				fragmentTransaction.hide(fragment);
			}
			fragmentTransaction.add(R.id.line_search_context, needFragment, "needFragment").show(needFragment);
		}
		this.fragment = needFragment;
		fragmentTransaction.commit();
	}

	@Override
	public void showUserList(BaseFragment fragment, String text) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (userFragment != null && userFragment.isAdded()) {
			EventBus.getDefault().post(text);
			userFragment = (SearchUserFragment) getFragmentFromCatch("userFragment");
			if (fragment != null) {
				// 隐藏上一个fragment,显示新的fragment
				fragmentTransaction.hide(fragment).show(userFragment);
			} else {
				fragmentTransaction.show(userFragment);
			}
		} else {
			userFragment = new SearchUserFragment();
            userFragment.setSearchText(text);
			if (fragment != null) {
				// 如果老的不为空，就隐藏
				fragmentTransaction.hide(fragment);
			}
			fragmentTransaction.add(R.id.line_search_context, userFragment, "userFragment").show(userFragment);
		}
		this.fragment = userFragment;
		fragmentTransaction.commit();
	}

	private Fragment getFragmentFromCatch(String tag) {
		if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
			return getSupportFragmentManager().findFragmentByTag(tag);
		}
		return null;
	}

	/** 接收搜索历史列表的点击事件 */
	public void onEventMainThread(SearchHistoryEntity entity) {
		if (entity != null && !TextUtils.isEmpty(entity.text)) {
			text.setText(entity.text);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_head_left_iv:
			close();
			break;

		// 搜索类型
		case R.id.tv_seatch_type:
			iSearchPresenter.clickSearchType();
			break;

		// 搜索
		case R.id.iv_search:
			iSearchPresenter.search(text.getText().toString(), fragment);
			break;

		default:
			break;
		}
	}
}
