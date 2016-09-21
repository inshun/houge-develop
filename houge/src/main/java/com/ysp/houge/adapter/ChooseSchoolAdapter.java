package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.SchoolEntity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ChooseSchoolAdapter extends BaseAdapter implements
		IDataAdapter<List<SchoolEntity>> {
	private ArrayList<SchoolEntity> chooseSchoolEntities;
	private LayoutInflater mInflater;

	public ChooseSchoolAdapter(Context context,ArrayList<SchoolEntity> chooseSchoolEntities) {
		super();
		this.chooseSchoolEntities = chooseSchoolEntities;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return chooseSchoolEntities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chooseSchoolEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		SchoolEntity chooseSchoolEntity = chooseSchoolEntities
				.get(position);
		if (convertView == null) {
			convertView = (View) mInflater
					.inflate(R.layout.item_choose_school, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			holder.tv_schoolName.setText(chooseSchoolEntity.getName());
		}
		return convertView;
	}

	@Override
	public void setData(List<SchoolEntity> data, boolean isRefresh) {
		if (isRefresh) {
			chooseSchoolEntities.clear();
		}
		chooseSchoolEntities.addAll(data);

	}

	@Override
	public List<SchoolEntity> getData() {
		// TODO Auto-generated method stub
		return chooseSchoolEntities;
	}

	class Holder {
		TextView tv_schoolName;

		public Holder(View convertView) {
			tv_schoolName = (TextView) convertView
					.findViewById(R.id.tv_schoolName);
		}

	}

}
