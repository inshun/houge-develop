package com.ysp.houge.ui.useless;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.CharacteristicEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.presenter.ICharacteristicPresenter;
import com.ysp.houge.presenter.impl.CharacteristicListPresenter;
import com.ysp.houge.ui.base.BaseListOrGridFragmentActivity;
import com.ysp.houge.ui.iview.ICharacteristicListView;
import com.ysp.houge.widget.MyActionBar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @描述:特点列表页面
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月14日下午1:02:35
 * @version 1.0
 */
public class CharacteristicListActivity extends BaseListOrGridFragmentActivity<CharacteristicEntity>
		implements OnClickListener, ICharacteristicListView {
	public static final String PART_TIME_JOB_LIST = "partTimeJobList";
	private GridView mGridView;
	private Button mNextStepBtn;

	private ICharacteristicPresenter iCharacteristicPresenter;
	private ArrayList<WorkTypeEntity> partTimeJobEntities;

	@SuppressWarnings("unchecked")
	@Override
	protected void initializeState(Bundle savedInstanceState) {
		super.initializeState(savedInstanceState);
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(PART_TIME_JOB_LIST)) {
				partTimeJobEntities = (ArrayList<WorkTypeEntity>) getIntent().getExtras()
						.getSerializable(PART_TIME_JOB_LIST);
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(PART_TIME_JOB_LIST, partTimeJobEntities);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState.containsKey(PART_TIME_JOB_LIST)) {
			partTimeJobEntities = (ArrayList<WorkTypeEntity>) savedInstanceState.getSerializable(PART_TIME_JOB_LIST);
		}
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_work_type_list);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mGridView = (GridView) findViewById(R.id.mGridView);
		// mNextStepBtn = (Button) findViewById(R.id.mNextStepBtn);
		mNextStepBtn.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		mAdapter = new ListAdapter(mDatas);
		mGridView.setAdapter(mAdapter);
		iCharacteristicPresenter = new CharacteristicListPresenter(this);
		iCharacteristicPresenter.getCharacteristicList();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.mNextStepBtn:
		// iCharacteristicPresenter.clickNextStepBtn(mDatas);
		// break;

		default:
			break;
		}
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.what_characteristic_you_have));
		actionBar.setLeftEnable(false);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@SuppressLint("InflateParams")
	@Override
	public View setDataAtPositon(int position, View convertView, ViewGroup viewGroup) {
		final Holder holder;
		final CharacteristicEntity characteristicEntity = mDatas.get(position);
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(CharacteristicListActivity.this).inflate(R.layout.item_work_type,
					null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			holder.mItemText.setText(characteristicEntity.getCharacteristicName());
			if (characteristicEntity.isChecked()) {
				holder.mItemIcon.setVisibility(View.VISIBLE);
				holder.mItemText.setTextColor(getResources().getColor(R.color.white));
				holder.mItemLayout.setBackgroundResource(R.drawable.rectangle_border_actionbar_bg_actionbar);
			} else {
				holder.mItemIcon.setVisibility(View.INVISIBLE);
				holder.mItemText.setTextColor(getResources().getColor(R.color.color_actionbar_bg));
				holder.mItemLayout.setBackgroundResource(R.drawable.rectangle_border_actionbar_bg_write);
			}
			holder.mItemLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (characteristicEntity.isChecked()) {
						characteristicEntity.setChecked(false);
						holder.mItemIcon.setVisibility(View.INVISIBLE);
						holder.mItemText.setTextColor(getResources().getColor(R.color.color_actionbar_bg));
						holder.mItemLayout.setBackgroundResource(R.drawable.rectangle_border_actionbar_bg_write);
					} else {
						characteristicEntity.setChecked(true);
						holder.mItemIcon.setVisibility(View.VISIBLE);
						holder.mItemText.setTextColor(getResources().getColor(R.color.white));
						holder.mItemLayout
								.setBackgroundResource(R.drawable.rectangle_border_dp5_actionbar_bg_actionbar);
					}
				}
			});
		}
		return convertView;
	}

	@Override
	public void setList(List<CharacteristicEntity> characteristicEntities) {
		mDatas.clear();
		mDatas.addAll(characteristicEntities);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void JumpToNextPage(ArrayList<CharacteristicEntity> characteristicChoosed) {

		// UIHelper.jumpToChooseMap(CharacteristicListActivity.this,
		// new ChooseMapEntity(partTimeJobEntities, characteristicChoosed));
	}

	class Holder {
		private ImageView mItemIcon;
		private TextView mItemText;
		private RelativeLayout mItemLayout;

		public Holder(View convertView) {
			// ItemIcon = (ImageView) convertView.findViewById(R.id.mItemIcon);
			// mImtemText = (TextView) convertView.findViewById(R.id.mItemText);
			mItemLayout = (RelativeLayout) convertView.findViewById(R.id.mItemLayout);
		}

	}
}
