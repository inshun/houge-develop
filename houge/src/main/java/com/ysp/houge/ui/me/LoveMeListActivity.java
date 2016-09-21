package com.ysp.houge.ui.me;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.LoveMeImageAdapter;
import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 描述： 喜欢的人列表
 *
 * @ClassName: LoveMeListActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月21日 上午12:36:21
 * 
 *        版本: 1.0
 */
public class LoveMeListActivity extends BaseFragmentActivity implements OnItemClickListener {
	public final static String KEY = "order_id";

	private MyActionBar actionBar;

	private GridView gvLoveMeList;
	private TextView tvEmptyView;

    private int page = 1;
	private int imgSize;
	private int order_id;
	private LoveMeImageAdapter adapter;
	private List<UserInfoEntity> loveMeListInfo = new ArrayList<>();

	private IGoodsDetailsModel iSkillDetailsModel;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, LoveMeListActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_love_me);
	}

	@Override
	protected void initActionbar() {
		actionBar = new MyActionBar(this);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		gvLoveMeList = (GridView) findViewById(R.id.gv_love_me_list);
		tvEmptyView = (TextView) findViewById(R.id.tv_empty_view);

		gvLoveMeList.setOnItemClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		if (null == getIntent() || null == getIntent().getExtras() || !getIntent().getExtras().containsKey(KEY)) {
			order_id = -1;
			actionBar.setTitle(getString(R.string.love_me));
		} else {
			order_id = getIntent().getExtras().getInt(KEY);
			actionBar.setTitle(getString(R.string.more_love));
		}

		iSkillDetailsModel = new GoodsDetailsModelImpl();

		loadLoveData();
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

    private void loadLoveData() {
		iSkillDetailsModel.requestLoveList(order_id, page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof List<?>) {
					List<UserInfoEntity> list = (List<UserInfoEntity>) data;
                    loveMeListInfo.addAll(list);
                    if (null != list && list.size() == 10){
                        page++;
                        loadLoveData();
                    }
				}
				loadFinish();
			}

			@Override
			public void onError(int errorCode, String message) {
				loadFinish();
			}
		});
	}

	private void loadFinish() {
		if (null != loveMeListInfo && loveMeListInfo.size() > 0) {
			// 计算图片款高度
			imgSize = (SizeUtils.getScreenWidth(this) - SizeUtils.dip2px(this, 20) * 2 - SizeUtils.dip2px(this, 8) * 5)
					/ 6;
			adapter = new LoveMeImageAdapter(imgSize, this, loveMeListInfo);
			gvLoveMeList.setAdapter(adapter);
		} else {
			gvLoveMeList.setVisibility(View.GONE);
			tvEmptyView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, (int) id);
		UserDetailsActivity.jumpIn(this, bundle);
	}
}
