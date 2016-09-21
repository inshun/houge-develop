package com.ysp.houge.ui.useless;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SchoolAdapter;
import com.ysp.houge.model.entity.bean.SchoolEntity;
import com.ysp.houge.presenter.IChooseSchoolPresenter;
import com.ysp.houge.presenter.impl.ChooseSchoolListPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IChooseSchoolView;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.SideBar;
import com.ysp.houge.widget.SideBar.OnTouchingLetterChangedListener;

/**
 * @描述:选择学校页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月14日上午8:21:46
 * @version 1.0
 */
public class MeInfoChooseSchoolActivity extends BaseFragmentActivity implements
		OnItemClickListener, IChooseSchoolView {
	public static final String WHICH_PAGE = "whichPage";

	private PullToRefreshListView mRefreshListView;

	private SideBar mSideBar;

	private TextView mDialog;

	private SchoolAdapter mAdapter;

	private IChooseSchoolPresenter iChooseSchoolPresenter;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_info_choose_school);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) this
				.findViewById(R.id.mRefreshListView);
		mRefreshListView.setMode(Mode.DISABLED);
		mSideBar = (SideBar) this.findViewById(R.id.sidrbar);
		mDialog = (TextView) this.findViewById(R.id.dialog);

		mAdapter = new SchoolAdapter(this);

		mSideBar.setTextView(mDialog);
		mRefreshListView.setOnItemClickListener(this);
		mRefreshListView.setAdapter(mAdapter);
		// 设置右侧触摸监听
		mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = mAdapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mRefreshListView.getRefreshableView().setSelection(
							position + 1);
				}

			}
		});

	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		if (getIntent().getExtras() == null
				|| !getIntent().getExtras().containsKey(WHICH_PAGE)) {
			finish();
			return;
		}
		iChooseSchoolPresenter = new ChooseSchoolListPresenter(this,
				getIntent().getExtras().getInt(WHICH_PAGE));
		iChooseSchoolPresenter.getSchoolListRequest(0);

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("学校");
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		iChooseSchoolPresenter.clickListItem(parent, view, position, id);
	}

	@Override
	public void setAllSchoolList(List<SchoolEntity> schoolEntities) {
		mAdapter.setData(schoolEntities, true);
		mAdapter.notifyDataSetChanged();
	}

}
