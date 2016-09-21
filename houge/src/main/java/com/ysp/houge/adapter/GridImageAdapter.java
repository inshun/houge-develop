package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.PhotoEntity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

public class GridImageAdapter extends BaseAdapter implements
		IDataAdapter<List<PhotoEntity>> {
	private Context mContext;
	private ArrayList<PhotoEntity> dataList = new ArrayList<PhotoEntity>();
	private DisplayMetrics dm;

	public GridImageAdapter(Context c, ArrayList<PhotoEntity> dataList) {
		mContext = c;
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		PhotoEntity photoEntity = dataList.get(position);
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(
					GridView.LayoutParams.MATCH_PARENT, dipToPx(65)));
			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		} else
			imageView = (ImageView) convertView;
		String path;
		if (dataList != null && position < dataList.size()) {
			path = photoEntity.getPhotoUrl();
		} else
			path = "camera_default";
		Log.i("path", "path:" + path + "::position" + position);
		if (path.contains("default"))
			imageView.setImageResource(R.drawable.camera_default);
		else {
			MyApplication
					.getInstance()
					.getImageLoaderManager()
					.loadNormalImage(imageView, path, LoadImageType.NormalImage);
		}
		return imageView;
	}

	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}

	@Override
	public void setData(List<PhotoEntity> data, boolean isRefresh) {

	}

	@Override
	public List<PhotoEntity> getData() {
		return dataList;
	}

}
