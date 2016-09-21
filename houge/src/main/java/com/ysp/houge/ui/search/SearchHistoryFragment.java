package com.ysp.houge.ui.search;

import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SearchHistoryAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.ISearchModel;
import com.ysp.houge.model.entity.db.SearchHistoryEntity;
import com.ysp.houge.model.impl.SearchModelImpl;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 描述： 搜索历史Fragment
 *
 * @ClassName: SearchHistoryFragment
 * 
 * @author: hx
 * 
 * @date: 2015年12月6日 上午10:55:49
 * 
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class SearchHistoryFragment extends BaseFragment implements OnItemClickListener, OnClickListener {
	private int type;

	/** listView */
	private View footerView;
	private Button clean;
	private ListView listView;
	private SearchHistoryAdapter adapter;

	private ISearchModel iSearchModel;
	private List<SearchHistoryEntity> historyEntities;

	@Override
	protected int getContentView() {
		return R.layout.fragment_search_history;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		// 脚部清除按钮
		footerView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_search_history, null);
		clean = (Button) footerView.findViewById(R.id.tv_clean);
		clean.setOnClickListener(this);

		listView = (ListView) view.findViewById(R.id.lv_search_history);
		listView.setOnItemClickListener(this);
		listView.addFooterView(footerView);

        //根据身份处理清除按钮背景以及颜色
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_SELLER:
                clean.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                clean.setBackgroundResource(R.drawable.shapa_sex_mal);
                break;
            case MyApplication.LOG_STATUS_BUYER:
                clean.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                clean.setBackgroundResource(R.drawable.shapa_sex_femal);
                break;
        }
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iSearchModel = new SearchModelImpl();
		adapter = new SearchHistoryAdapter(getActivity());
		listView.setAdapter(adapter);
		getHistoryDate(type);
	}

	private void getHistoryDate(int type) {
		showProgress();
		iSearchModel.getSearchHistory(type, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				hideProgress();
				if (data != null && data instanceof List<?>) {
					historyEntities = (List<SearchHistoryEntity>) data;
					if (historyEntities.size() > 0) {
						if (listView.getFooterViewsCount() == 0) {
							listView.addFooterView(footerView);
						}
					} else {
						listView.removeFooterView(footerView);
					}
					adapter.setData(historyEntities, true);
					adapter.notifyDataSetChanged();
				} else {
					historyEntities = null;
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				hideProgress();
				historyEntities = null;
			}
		});
	};

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

	public void setType(int type) {
		this.type = type;
		getHistoryDate(type);
	}

	@Override
	public void onClick(View v) {
		showProgress();
		iSearchModel.delSearchHistory(type, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				hideProgress();
				if (data instanceof Integer && ((Integer) data) > 0) {
					showToast("删除成功");
					getHistoryDate(type);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				hideProgress();
				historyEntities = null;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		SearchHistoryEntity historyEntity = adapter.getData().get(arg2);
		EventBus.getDefault().post(historyEntity);
	}
}
