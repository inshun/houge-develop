package com.ysp.houge.widget.image.selector.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.widget.image.selector.bean.Folder;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 文件夹Adapter Created by Nereo on 2015/4/7.
 */
public class FolderAdapter extends BaseAdapter {

	int mImageSize;
	int lastSelected = 0;
	private Context mContext;
	private LayoutInflater mInflater;
	private List<Folder> mFolders = new ArrayList<Folder>();

	public FolderAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.folder_cover_size);
	}

	/**
	 * 设置数据集
	 * 
	 * @param folders
	 */
	public void setData(List<Folder> folders) {
		if (folders != null && folders.size() > 0) {
			mFolders = folders;
		} else {
			mFolders.clear();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mFolders.size() + 1;
	}

	@Override
	public Folder getItem(int i) {
		if (i == 0)
			return null;
		return mFolders.get(i - 1);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.item_list_folder, viewGroup, false);
			holder = new ViewHolder(view);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if (holder != null) {
			if (i == 0) {
				holder.name.setText("所有图片");
				holder.size.setText(getTotalImageSize() + "张");
				if (mFolders.size() > 0) {
					Folder f = mFolders.get(0);

					// 显示图片
					// ImageLoader方式
					loadImage(holder.cover, f.cover.path);
					// 毕加索方式
					// Picasso.with(mContext).load(new
					// File(f.cover.path)).error(R.drawable.default_error)
					// .resize(mImageSize,
					// mImageSize).centerCrop().into(holder.cover);
				}
			} else {
				holder.bindData(getItem(i));
			}
			if (lastSelected == i) {
				holder.indicator.setVisibility(View.VISIBLE);
			} else {
				holder.indicator.setVisibility(View.INVISIBLE);
			}
		}
		return view;
	}

	private int getTotalImageSize() {
		int result = 0;
		if (mFolders != null && mFolders.size() > 0) {
			for (Folder f : mFolders) {
				result += f.images.size();
			}
		}
		return result;
	}

	public int getSelectIndex() {
		return lastSelected;
	}

	public void setSelectIndex(int i) {
		if (lastSelected == i)
			return;

		lastSelected = i;
		notifyDataSetChanged();
	}

	private void loadImage(View view, String url) {
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, "file://" + url,
				LoadImageType.NormalImage);
	}

	class ViewHolder {
		ImageView cover;
		TextView name;
		TextView size;
		ImageView indicator;

		ViewHolder(View view) {
			cover = (ImageView) view.findViewById(R.id.cover);
			name = (TextView) view.findViewById(R.id.name);
			size = (TextView) view.findViewById(R.id.size);
			indicator = (ImageView) view.findViewById(R.id.indicator);
			view.setTag(this);
		}

		void bindData(Folder data) {
			name.setText(data.name);
			size.setText(data.images.size() + "张");
			// 显示图片
			// ImageLoader方式
			loadImage(cover, data.cover.path);
			// 毕加索方式
			// Picasso.with(mContext).load(new
			// File(data.cover.path)).placeholder(R.drawable.default_error)
			// .resize(mImageSize, mImageSize).centerCrop().into(cover);
		}
	}
}
