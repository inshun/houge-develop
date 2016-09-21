package com.ysp.houge.ui.photoview;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.widget.HackyViewPager;
import com.ysp.houge.widget.photoview.PhotoView;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 描述： 大图查看Activity
 *
 * @ClassName: PhotoViewActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月25日 上午10:37:31
 * 
 *        版本: 1.0
 */
public class PhotoViewActivity extends BaseFragmentActivity implements OnPageChangeListener {
	public static final String KEY = "photo_list";
    public static final String PAGE_INDEX = "index";

	private HackyViewPager vp;
	private TextView count;

    private int page = 0;
	private List<String> photoList = new ArrayList<String>();

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, PhotoViewActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void initActionbar() {
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_photo_view);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		vp = (HackyViewPager) findViewById(R.id.vp_photo);
		count = (TextView) findViewById(R.id.tv_count);
		findViewById(R.id.line_finish).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				close();
			}
		});
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		if (getIntent().getExtras().containsKey(KEY)) {
			photoList = new Gson().fromJson(getIntent().getExtras().getString(KEY), new TypeToken<ArrayList<String>>() {
			}.getType());
            if (getIntent().getExtras().containsKey(PAGE_INDEX))
                page = getIntent().getExtras().getInt(PAGE_INDEX);
		} else {
			close();
		}

		if (null == photoList || photoList.isEmpty()) {
			close();
		}
		count.setText((page + 1) +"/" + photoList.size());
		vp.setAdapter(new PhotoViewAdapter());
        vp.setCurrentItem(page);
		vp.addOnPageChangeListener(this);
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
	public void onPageSelected(int arg0) {
		count.setText(arg0 + 1 + "/" + photoList.size());
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	class PhotoViewAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return photoList.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			MyApplication.getInstance().getImageLoaderManager().loadNormalImage(photoView, photoList.get(position),
					LoadImageType.NormalImage);
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
}
