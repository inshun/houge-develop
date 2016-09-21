package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.utility.LogUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class
AddressAdapter extends BaseAdapter implements IDataAdapter<List<AddressEntity>> {
	private List<AddressEntity> list = new ArrayList<AddressEntity>();
	private LayoutInflater mInflater;
    private  Context context;
    public int index;

	public AddressAdapter(Context context) {
		super();
        this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).id;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		// 获得操作对象
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_address_manager, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			if (("yes").equals(list.get(position).is_default)) {
				holder.isDefault.setChecked(true);
			} else {
				holder.isDefault.setChecked(false);
			}
			holder.isDefault.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 列表的CheckBox被选中的事件(这里用点击事件代替)
					if (AddressEntity.DEFAULT_TYPE_NO.equals(list.get(position).is_default)) {
						for (int i = 0; i < getCount(); i++) {
							list.get(i).is_default = AddressEntity.DEFAULT_TYPE_NO;
						}
						list.get(position).is_default = AddressEntity.DEFAULT_TYPE_YES;
						notifyDataSetChanged();

					}
				}
			});

			// 街道去掉分隔符逗号
			StringBuffer sb = new StringBuffer(list.get(position).street);
			if (sb.indexOf(",") > 0) {
				sb.deleteCharAt(sb.indexOf(","));
			}

			holder.address.setText(sb.toString());
			holder.contacts.setText(list.get(position).real_name);
			holder.contactNum.setText(list.get(position).mobile);
		}
		return convertView;
	}

	@Override
	public void setData(List<AddressEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);
	}

	@Override
	public List<AddressEntity> getData() {
		return list;
	}

	class Holder {
		CheckBox isDefault;
		TextView address;
		TextView contacts;
		TextView contactNum;

		public Holder(View convertView) {
			isDefault = (CheckBox) convertView.findViewById(R.id.cb_is_default);
			address = (TextView) convertView.findViewById(R.id.tv_detail_address);
			contacts = (TextView) convertView.findViewById(R.id.tv_contacts);
			contactNum = (TextView) convertView.findViewById(R.id.tv_contact_number);
		}


	}

}
