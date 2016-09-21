package com.ysp.houge.popwindow;

import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.FlowLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @描述: 展开的关注列表pop
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月31日下午8:52:39
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class NearbyListPopupWindow extends PopupWindow implements OnClickListener {
	private Context context;
	private LayoutInflater inflater;
	private List<WorkTypeEntity> entities;

	private View contentView;
	private FlowLayout flowLayout;
	private OnClick onClick;


	@SuppressWarnings("deprecation")
	public NearbyListPopupWindow(Context context, List<WorkTypeEntity> entities) {
		super(context);
		this.context = context;
		this.entities = entities;
		inflater = LayoutInflater.from(context);
		// 这里设置宽高度(之前宽高都为0就是应为没有设置导致的)
		setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		// 使其聚集
		setFocusable(true);
		// 设置允许在外点击消失
		setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		setBackgroundDrawable(new BitmapDrawable());

		if (context == null || entities == null || entities.size() < 1) {
			LogUtil.setLogWithE("RecommendListPopupWindow", "NULL");
		}

		initView();
		addView();
		setContentView(contentView);
	}

	private void initView() {
		// TODO 初始载入布局文件
		contentView = inflater.inflate(R.layout.popup_recommend_flow, null);
		flowLayout = (FlowLayout) contentView.findViewById(R.id.flowlayout);
	}

	@SuppressWarnings("deprecation")
	private void addView() {
		// TODO 往布局循环添加TextView
		for (int i = 0; i < entities.size(); i++) {
			TextView textView = new TextView(context);
			textView.setText(entities.get(i).getName());
			MarginLayoutParams params = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,
					MarginLayoutParams.WRAP_CONTENT);
			// 设置四个方向的边距
			params.bottomMargin = SizeUtils.dip2px(context, 5);
			params.topMargin = SizeUtils.dip2px(context, 5);
			params.leftMargin = SizeUtils.dip2px(context, 5);
			params.rightMargin = SizeUtils.dip2px(context, 5);
			textView.setLayoutParams(params);
			textView.setTextColor(context.getResources().getColor(R.color.color_e5e5e5));
			textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shapa_recommend_bg));

			// 设置TAG以及点击事件(点击事件通过tag来判断)
			textView.setTag(entities.get(i).getId());
			textView.setOnClickListener(this);

			flowLayout.addView(textView);
		}
	}

	public void setOnClick(OnClick onClick) {
		this.onClick = onClick;
	}

	@Override
	public void onClick(View v) {
		// TODO 每一个单项的点击事件
		onClick.click(((Integer) v.getTag()));
		this.dismiss();
	}

	public interface OnClick {
		void click(int id);
	}
}
