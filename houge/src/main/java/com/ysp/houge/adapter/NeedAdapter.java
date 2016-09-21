package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 我的需求适配器
 *
 * @ClassName: NeedAdapter
 * 
 * @author: hx
 * 
 * @date: 2015年12月23日 上午9:54:32
 * 
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class NeedAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>> {
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
	private OnItemClickListener listener;
	private LayoutInflater mInflater;
	private Context context;

	public NeedAdapter(Context context) {
		super();
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	@Override
	public int getCount() {
        return list.size() == 0 ? 1 : list.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_need, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

        if (list.size() == 0) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 1);
            convertView.setLayoutParams(params);
        }

        if (position==0&&list.size()!=0) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(params);
        }

        if (holder != null && list.size() > position) {
			// ViewHolder操作

			final GoodsDetailEntity detailEntity = list.get(position);
            holder.auditMes.setVisibility(View.GONE);
			if (null != detailEntity) {
				// 状态(审核未通过的原因、重新编辑)
				switch (detailEntity.status) {
				case GoodsDetailEntity.STATUS_PASS:
					holder.status.setText("已通过");
					holder.auditMes.setVisibility(View.GONE);
					holder.reedit.setVisibility(View.VISIBLE);
					break;
				case GoodsDetailEntity.STATUS_UN_PASS:
					holder.status.setText("未通过");
                    if (!TextUtils.isEmpty(detailEntity.reason))
					    holder.auditMes.setText(detailEntity.reason);
					holder.auditMes.setVisibility(View.VISIBLE);
					holder.reedit.setVisibility(View.VISIBLE);
					break;
				default:
					holder.status.setText("审核中");
					holder.auditMes.setVisibility(View.GONE);
					holder.reedit.setVisibility(View.VISIBLE);
					break;
				}

				// 图片
				if (null != detailEntity.image && detailEntity.image.size() > 0) {
					MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.img,
							detailEntity.image.get(0), LoadImageType.NormalImage);
				}

				// 名称
				if (TextUtils.isEmpty(detailEntity.title)) {
					holder.name.setText("未知需求标题");
				} else {
					holder.name.setText(detailEntity.title);
				}

				// 价格
				holder.price.setText("￥" + String.valueOf(detailEntity.price));

				// 点击进入需求详情
				holder.need.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (detailEntity.status == GoodsDetailEntity.STATUS_PASS) {
							Bundle bundle = new Bundle();
							bundle.putInt(NeedDetailsActivity.KEY, detailEntity.id);
							NeedDetailsActivity.jumpIn(context, bundle);
						}
					}
				});

				// 重新编辑事件处理
				holder.reedit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 暂时只做删除
						if (null != listener) {
							listener.OnClick(detailEntity.id, OnItemClickListener.CLICK_OPERATION_DELETE);
						}

					}
				});

                //进入需求详情
                holder.need.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != listener) {
                            listener.OnClick(detailEntity.id, OnItemClickListener.CLICK_OPERATION_NEED_DETAIL);
                        }
                    }
                });
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<GoodsDetailEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);
	}

	@Override
	public List<GoodsDetailEntity> getData() {
		return list;
	}

	@SuppressLint("CutPasteId")
	class Holder {
		TextView status;
		TextView reedit;
		TextView auditMes;

		LinearLayout need;
		ImageView img;
		TextView name;
		TextView price;

		public Holder(View convertView) {
			status = (TextView) convertView.findViewById(R.id.tv_need_status);
			reedit = (TextView) convertView.findViewById(R.id.tv_need_reedit);
			auditMes = (TextView) convertView.findViewById(R.id.tv_audit_mes);

			need = (LinearLayout) convertView.findViewById(R.id.line_need_info);
			img = (ImageView) convertView.findViewById(R.id.iv_need_img);
			name = (TextView) convertView.findViewById(R.id.tv_need_name);
			price = (TextView) convertView.findViewById(R.id.tv_need_money);
		}
	}

}
