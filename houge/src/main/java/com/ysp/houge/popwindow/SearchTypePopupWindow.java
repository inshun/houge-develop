package com.ysp.houge.popwindow;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.ui.search.SearchActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 描述： 搜索类型的Pop
 *
 * @ClassName: SearchTypePopupWindow
 * 
 * @author: hx
 * 
 * @date: 2015年12月5日 下午3:32:14
 * 
 *        版本: 1.0
 */
public class SearchTypePopupWindow extends BasePopupWindow implements OnClickListener {
	private TextView user, skill;
	private onTypeChooseListener listener;
	public SearchTypePopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
	}

	@Override
	public void initViews() {
		user = (TextView) contentView.findViewById(R.id.tv_type_user);
		skill = (TextView) contentView.findViewById(R.id.tv_type_skill);

		switch (MyApplication.getInstance().getLoginStaus()) {
		case MyApplication.LOG_STATUS_BUYER:
			skill.setText("搜技能");
			break;
		case MyApplication.LOG_STATUS_SELLER:
			skill.setText("搜需求");
			break;
		}
	}

	@Override
	public void initEvents() {
		user.setOnClickListener(this);
		skill.setOnClickListener(this);
	}

	@Override
	public void initData() {
	}

	public void setListener(onTypeChooseListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_type_user:
			listener.onTypeClick(SearchActivity.SEARCH_TYPE_USER);
			break;
		case R.id.tv_type_skill:
			switch (MyApplication.getInstance().getLoginStaus()) {
			case MyApplication.LOG_STATUS_BUYER:
				listener.onTypeClick(SearchActivity.SEARCH_TYPE_SKILL);
				break;
			case MyApplication.LOG_STATUS_SELLER:
				listener.onTypeClick(SearchActivity.SEARCH_TYPE_NEED);
				break;
			}
			break;
		default:
			break;
		}
		dismiss();
	}

	public interface onTypeChooseListener {
		void onTypeClick(int type);
	}

}
