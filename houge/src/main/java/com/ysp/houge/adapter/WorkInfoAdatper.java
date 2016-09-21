package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.PhotoEntity;
import com.ysp.houge.model.entity.db.ItemWorkInfoEntity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.ImageViewSubClass;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

/**
 * @描述:工作经历列表适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月25日上午11:34:38
 * @version 2.2
 */
public class WorkInfoAdatper extends BaseAdapter implements
		IDataAdapter<List<ItemWorkInfoEntity>> {
	private List<ItemWorkInfoEntity> itemWorkInfoEntities = new ArrayList<ItemWorkInfoEntity>();
	private LayoutInflater mInflater;
	private int widthAndHeight;
	private WorkInfoItemClickListener workInfoItemClickListener;
	public WorkInfoAdatper(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
		widthAndHeight = (SizeUtils.getScreenWidth((Activity) context)) / 3;
	}

	/**
	 * @return the workInfoItemClickListener
	 */
	public WorkInfoItemClickListener getWorkInfoItemClickListener() {
		return workInfoItemClickListener;
	}

	/**
	 * @param workInfoItemClickListener
	 *            the workInfoItemClickListener to set
	 */
	public void setWorkInfoItemClickListener(
			WorkInfoItemClickListener workInfoItemClickListener) {
		this.workInfoItemClickListener = workInfoItemClickListener;
	}

	@Override
	public int getCount() {
		return itemWorkInfoEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return itemWorkInfoEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		final ItemWorkInfoEntity itemWorkInfoEntity = itemWorkInfoEntities
				.get(position);
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_work_info,
					null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			holder.tv_work_info_name.setText(itemWorkInfoEntity
					.getWorkInfoName());
			holder.tv_work_info_content.setText(itemWorkInfoEntity
					.getWorkInfoContent());
			holder.tv_work_info_time.setText(itemWorkInfoEntity
					.getWorkInfoTime());
			holder.tv_work_info_delete
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (workInfoItemClickListener != null) {
								workInfoItemClickListener.clickDeleteBtn(
										itemWorkInfoEntity, position);
							}
						}
					});

			for (int i = 0; i < holder.mImg.length; i++) {
				ViewGroup.LayoutParams params = holder.mImg[i]
						.getLayoutParams();
				params.width = widthAndHeight;
				params.height = widthAndHeight;
				holder.mImg[i].setLayoutParams(params);
			}
			List<PhotoEntity> photos = itemWorkInfoEntity.getPhotoEntities();
			if (photos != null && photos.size() != 0 && photos.size() < 10) {
				holder.mHorizontalScrollView.setVisibility(View.VISIBLE);
				PhotoEntity photo = null;
				for (int i = 0, count = photos.size(); i < count; i++) {
					final int positionInListSize = i;
					photo = photos.get(i);
					MyApplication
							.getInstance()
							.getImageLoaderManager()
							.loadNormalImage(holder.mImg[i],
									photo.getPhotoUrl(),
									LoadImageType.NormalImage);
					holder.mImg[i].setVisibility(View.VISIBLE);
					holder.mImg[i].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (workInfoItemClickListener != null) {
								workInfoItemClickListener.clickPicture(
										itemWorkInfoEntity.getPhotoEntities(),
										positionInListSize);
							}
						}
					});
				}
				if (photos.size() > 0 && photos.size() < 9) {
					for (int i = photos.size(); i < 9; i++) {
						holder.mImg[i].setVisibility(View.GONE);
					}
				}
			} else {
				holder.mHorizontalScrollView.setVisibility(View.GONE);
			}

		}
		return convertView;
	}

	@Override
	public void setData(List<ItemWorkInfoEntity> data, boolean isRefresh) {
		if (isRefresh) {
			itemWorkInfoEntities.clear();
		}
		itemWorkInfoEntities.addAll(data);
	}

	@Override
	public List<ItemWorkInfoEntity> getData() {
		return itemWorkInfoEntities;
	}

	public interface WorkInfoItemClickListener {
		void clickDeleteBtn(ItemWorkInfoEntity itemWorkInfoEntity, int position);

		void clickPicture(List<PhotoEntity> photoEntities,
				int positionInPictureList);
	}

	class Holder {
		TextView tv_work_info_name;
		TextView tv_work_info_delete;
		TextView tv_work_info_content;
		TextView tv_work_info_time;
		HorizontalScrollView mHorizontalScrollView;
		ImageViewSubClass[] mImg = new ImageViewSubClass[9];

		public Holder(View convertView) {
			tv_work_info_name = (TextView) convertView
					.findViewById(R.id.tv_work_info_name);
			tv_work_info_delete = (TextView) convertView
					.findViewById(R.id.tv_work_info_delete);
			tv_work_info_content = (TextView) convertView
					.findViewById(R.id.tv_work_info_content);
			tv_work_info_time = (TextView) convertView
					.findViewById(R.id.tv_system_message_time);
			mHorizontalScrollView = (HorizontalScrollView) convertView
					.findViewById(R.id.mHorizontalScrollView);
			mImg[0] = (ImageViewSubClass) convertView.findViewById(R.id.mImg1);
			mImg[1] = (ImageViewSubClass) convertView.findViewById(R.id.mImg2);
			mImg[2] = (ImageViewSubClass) convertView.findViewById(R.id.mImg3);
			mImg[3] = (ImageViewSubClass) convertView.findViewById(R.id.mImg4);
			mImg[4] = (ImageViewSubClass) convertView.findViewById(R.id.mImg5);
			mImg[5] = (ImageViewSubClass) convertView.findViewById(R.id.mImg6);
			mImg[6] = (ImageViewSubClass) convertView.findViewById(R.id.mImg7);
			mImg[7] = (ImageViewSubClass) convertView.findViewById(R.id.mImg8);
			mImg[8] = (ImageViewSubClass) convertView.findViewById(R.id.mImg9);
		}

	}

}
