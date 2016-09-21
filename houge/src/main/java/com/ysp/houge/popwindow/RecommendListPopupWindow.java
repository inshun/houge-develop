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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 描述： 瀑布流关注列表
 *
 * @ClassName: RecommendListPopupWindow
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 上午9:33:55
 * 
 *        版本: 1.0
 */
@SuppressLint({ "InflateParams", "ClickableViewAccessibility" })
public class RecommendListPopupWindow extends PopupWindow implements OnClickListener, OnDismissListener {
	private Context context;
	private LayoutInflater inflater;
	private List<WorkTypeEntity> entities;

	private View contentView;
	private FlowLayout flowLayout;
	private ScrollView layout;
	private OnClick onClick;

	@SuppressWarnings("deprecation")
	public RecommendListPopupWindow(Context context, List<WorkTypeEntity> entities) {
		super(context);
		this.context = context;
		this.entities = entities;
		inflater = LayoutInflater.from(context);
		// 这里设置宽高度(之前宽高都为0就是应为没有设置导致的)
		setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		// 使其聚集
		setFocusable(true);
		// 设置允许在外点击消失
		setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		setBackgroundDrawable(new BitmapDrawable());
		setOnDismissListener(this);

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
		layout = (ScrollView) contentView.findViewById(R.id.sc_layout);
		flowLayout = (FlowLayout) contentView.findViewById(R.id.flowlayout);

		layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					RecommendListPopupWindow.this.dismiss();
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	@SuppressLint("NewApi")
	private void addView() {
		// TODO 往布局循环添加TextView
		for (int i = 0; i < entities.size(); i++) {
			RelativeLayout layout = new RelativeLayout(context);
			MarginLayoutParams params = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,
					MarginLayoutParams.WRAP_CONTENT);
			params.bottomMargin = SizeUtils.dip2px(context, 2);
			params.topMargin = SizeUtils.dip2px(context, 2);
			params.leftMargin = SizeUtils.dip2px(context, 2);
			params.rightMargin = SizeUtils.dip2px(context, 2);
			layout.setLayoutParams(params);
			layout.setBackground(context.getResources().getDrawable(R.drawable.shapa_recommend_bg));

			TextView textView = new TextView(context);

			textView.setText(entities.get(i).getName());
			textView.setPaddingRelative(SizeUtils.dip2px(context, 8), 0,
					SizeUtils.dip2px(context, 8), SizeUtils.dip2px(context, 2));
			textView.setTextColor(context.getResources().getColor(R.color.color_666666));

			layout.addView(textView);
			layout.setTag(i);
			layout.setOnClickListener(this);

			flowLayout.addView(layout);
		}
	}

	public void setOnClick(OnClick onClick) {
		this.onClick = onClick;
	}

	@Override
	public void onClick(View v) {
		if (v.getTag() != null && v.getTag() instanceof Integer) {
			onClick.click(((Integer) v.getTag()));
		}
		this.dismiss();
	}

	@Override
	public void onDismiss() {
	}

	public interface OnClick {
		void click(int id);
	}
}
